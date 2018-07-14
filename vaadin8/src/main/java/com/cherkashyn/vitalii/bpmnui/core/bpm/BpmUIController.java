package com.cherkashyn.vitalii.bpmnui.core.bpm;

import com.cherkashyn.vitalii.bpmnui.core.ui.ContentController;
import com.cherkashyn.vitalii.bpmnui.core.ui.common.Exchange;
import com.cherkashyn.vitalii.bpmnui.core.ui.view.bpmframe.NotAssignedTask;
import com.cherkashyn.vitalii.bpmnui.core.ui.view.processlist.ProcessListItem;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Component;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.impl.persistence.entity.TaskEntityImpl;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.util.CollectionUtils;

import java.util.Set;

@UIScope
@SpringComponent
public class BpmUIController {

    @Autowired
    private ContentController contentController;

    @Autowired
    private ProcessEngine processEngine;

    @Autowired
    private Exchange exchange;

    @Autowired
    private ApplicationContext context;

    public void showScreen(ProcessListItem item){
        TaskEntityImpl assignedTask = findActiveTask(item);
        exchange.currentProcess=item;
        if(assignedTask==null){
            exchange.currentTask=null;
            contentController.setRoot(NotAssignedTask.class);
        }else{
            exchange.currentTask=assignedTask;
            contentController.setRoot((Component) context.getBean(assignedTask.getFormKey()));
        }
    }

    private Class<? extends Component> getTaskClass(String formKey) {
        return null;
    }

    private TaskEntityImpl findActiveTask(ProcessListItem item) {
        BpmnModel model = BpmUtils.getBpmnModelByProcessDefinition(processEngine, item.getProcessDefinitionId());
        // TODO consider using parameter below
        // .taskCandidateOrAssigned()
        for(Task eachTask : BpmUtils.getTasksByProcessInstance(processEngine, item.getProcessInstanceId())){
            if(isTaskBelongsToUserGroup(model, eachTask, exchange.user.getGroups())){
                return (TaskEntityImpl)eachTask;
            }
        }
        return null;
    }

    private boolean isTaskBelongsToUserGroup(BpmnModel model, Task task, Set<String> userGroups) {
        return CollectionUtils.containsAny(userGroups, BpmUtils.getTaskCandidateGroups(model, task));
    }

}
