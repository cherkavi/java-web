package com.cherkashyn.vitalii.bpmnui.core.bpm;

import com.cherkashyn.vitalii.bpmnui.core.ui.ContentController;
import com.cherkashyn.vitalii.bpmnui.core.ui.common.Exchange;
import com.cherkashyn.vitalii.bpmnui.core.ui.view.common.NotAssignedTask;
import com.cherkashyn.vitalii.bpmnui.core.ui.view.common.ProcessNotFound;
import com.cherkashyn.vitalii.bpmnui.core.ui.view.common.TaskViewNotFound;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Component;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.impl.persistence.entity.TaskEntityImpl;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.BeansException;
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

    /**
     * show screen of the process
     * @param processInstanceId - activiti ProcessInstanceId of executed process
     */
    public void showScreen(String processInstanceId){
        exchange.processInstanceId=processInstanceId;

        try{
            TaskEntityImpl assignedTask = findActiveTask(processInstanceId);
            Component component = (Component)context.getBean(assignedTask.getFormKey());
            contentController.setRoot( component);
        }catch(NotAssignedTaskException e){
            contentController.setRoot(NotAssignedTask.class);
        }catch(BeansException e){
            contentController.setRoot(TaskViewNotFound.class);
        }catch(ProcessNotFoundException e){
            contentController.setRoot(ProcessNotFound.class);
        }
    }

    public void showScreen(Class<? extends Component> clazz){
        contentController.setRoot(context.getBean(clazz));
    }

    private TaskEntityImpl findActiveTask(String processInstanceId) throws NotAssignedTaskException, ProcessNotFoundException {
        BpmnModel model;
        try{
            ProcessInstance processInstance = BpmUtils.getProcessInstanceById(processEngine, processInstanceId);
            if(processInstance==null){
                throw new ProcessNotFoundException();
            }
            model = BpmUtils.getBpmnModelByProcessExecution(processEngine, processInstance);
            if(model==null){
                throw new ProcessNotFoundException();
            }
        }catch(ActivitiException e){
            throw new ProcessNotFoundException();
        }
        // TODO consider using parameter below
        // .taskCandidateOrAssigned()
        for(Task eachTask : BpmUtils.getTasksByProcessInstance(processEngine, processInstanceId)){
            if(isTaskBelongsToUserGroup(model, eachTask, exchange.user.getGroups())){
                return (TaskEntityImpl)eachTask;
            }
        }
        throw new NotAssignedTaskException();
    }

    private boolean isTaskBelongsToUserGroup(BpmnModel model, Task task, Set<String> userGroups) {
        return CollectionUtils.containsAny(userGroups, BpmUtils.getTaskCandidateGroups(model, task));
    }

}

class NotAssignedTaskException extends RuntimeException{
}

class ProcessNotFoundException extends RuntimeException{
}
