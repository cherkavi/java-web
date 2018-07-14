package com.cherkashyn.vitalii.bpmnui.core.management;

import com.cherkashyn.vitalii.bpmnui.core.bpm.BpmUtils;
import com.cherkashyn.vitalii.bpmnui.core.bpm.validator.XmlValidator;
import com.cherkashyn.vitalii.bpmnui.core.bpm.validator.XmlValidatorException;
import com.cherkashyn.vitalii.bpmnui.core.ui.common.Exchange;
import org.activiti.bpmn.model.*;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.impl.persistence.entity.TaskEntityImpl;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.*;
import java.util.stream.Collectors;

@ShellComponent
public class ActivitiShellCommands {

    @Autowired
    ProcessEngine processEngine;


    private final static String REMOVE_ME="myProcess";

    @ShellMethod("start Activiti process")
    public ProcessInstance processStart(@ShellOption(help="process definition key/id") String processName,
                                        @ShellOption(help="order_id value for current process") String orderNumber){
        Map<String, Object> variables = new HashMap<>();
        variables.put(Exchange.ORDERID_KEY, orderNumber);
        return processEngine.getRuntimeService()
                .startProcessInstanceByKey(processName, variables);
    }

    @ShellMethod("deploy new process into Activiti Engine")
    public Deployment processDeploy(@ShellOption(help="path to file with BPMN content") String path) throws FileNotFoundException {
        validateBpmFile(path);
        return processEngine.getRepositoryService().createDeployment()
                    .addInputStream(path, new FileInputStream(path))
                    .deploy();
    }

    private void validateBpmFile(String path) throws XmlValidatorException{
        File file = new File(path);
        if(!file.exists()){
            throw new InvalidParameterException("file not found: "+path);
        }
        Document document = null;
        try {
            document = XmlValidator.read(file);
        } catch (ParserConfigurationException | IOException | SAXException e) {
            throw new XmlValidatorException("error reading XML file : "+path, e);
        }

        XmlValidator.validateProcess((Element) XmlValidator.readProcess(document));
        NodeList userTasks = XmlValidator.readAllUserTasks(document);
        if(userTasks==null || userTasks.getLength()==0){
            throw new XmlValidatorException("no user tasks found !!! ");
        }
        for(int index = 0; index<userTasks.getLength();index++){
            XmlValidator.validateUserTask((Element) userTasks.item(index));
        }
    }

    @ShellMethod("print all definitions")
    public List<String> processDefinitions(){
        return BpmUtils.getProcessDefinitions(processEngine).stream()
                .map(processDefinition ->
                        String.format("id: %s, key: %s, name:%s ",
                                processDefinition.getId(), processDefinition.getKey(), processDefinition.getName()))
                .collect(Collectors.toList());
    }

    @ShellMethod("print all deployments")
    public List<String> processDeployments(){
        return BpmUtils.getProcessDeployments(processEngine).stream()
                .map(processDefinition -> String.format("id: %s, key: %s, name:%s ", processDefinition.getId(), processDefinition.getKey(), processDefinition.getName()))
                .collect(Collectors.toList());
    }

    @ShellMethod("print all executions by ProcessDefinitionId ")
    public List<String> processExecutions(@ShellOption(help="process definition id") String processDefinitionId){
        return BpmUtils.getProcessesInstanceByName(processEngine, processDefinitionId)
                .stream().map(processInstance->
                    String.format("\n >>> processId: %s \n" +
                                    "processInstanceId: %s \n" +
                                    "processDefinitionId: %s  \n" +
                                    "processDeploymentId: %s  \n" +
                                    "tenantId: %s  \n" +
                                    "activityId: %s  \n" +
                                    "startUserId: %s  \n" +
                                    "Variables: %s",
                            processInstance.getId(),
                            processInstance.getProcessInstanceId(),
                            processInstance.getProcessDefinitionId(),
                            processInstance.getDeploymentId(),
                            processInstance.getTenantId(),
                            processInstance.getActivityId(),
                            processInstance.getStartUserId(),
                            processInstance.getProcessVariables()
                            // processEngine.getRuntimeService().getVariableInstance(processInstance.getId(), Exchange.ORDERID_KEY)
                ))
                .collect(Collectors.toList());
    }

    @ShellMethod("delete process by ProcessInstanceId")
    public void processDelete(@ShellOption(help="process instance id") String processInstanceId, @ShellOption(help="delete reason", defaultValue = "unknown") String reason){
        this.processEngine.getRuntimeService().deleteProcessInstance(processInstanceId, reason);
    }

    @ShellMethod("print all active tasks by process instance ")
    public void tasksPrintByProcessInstance(@ShellOption(help="process InstanceId") String processInstanceId){
        BpmnModel model = BpmUtils.getBpmnModelByProcessExecution(processEngine, BpmUtils.getProcessInstanceById(processEngine, processInstanceId));
        for(Task activeTask : BpmUtils.getTasksByProcessInstance(processEngine, processInstanceId)){
            TaskEntityImpl task = (TaskEntityImpl)activeTask;
            System.out.println(">>> taskExecutionId: " + task.getExecutionId()
                    + "\n taskDefinitionKey: " + task.getTaskDefinitionKey()
                    + "\n Name: " + task.getName()
                    + "\n Assignee: " + task.getAssignee()
                    + "\n Category: " + task.getCategory()
                    + "\n Description: " + task.getDescription()
                    + "\n CandidateGroups: " + BpmUtils.getTaskCandidateGroups(model, task)
                    + "\n CandidateUsers: " + BpmUtils.getTaskCandidateUsers(model, task)
                    + "\n CustomProperties: " + BpmUtils.getTaskCustomProperties(model, task)
                    + "\n Candidates: " + task.getCandidates()
                    + "\n FormKey: " + task.getFormKey()
            );
        }
    }

    @ShellMethod("complete all UserTasks by process")
    public void tasksCompleteByProcessInstance(@ShellOption(help="id of instance of process") String processInstanceId){
        for(Task activeTask : BpmUtils.getTasksByProcessInstance(processEngine, processInstanceId)){
            processEngine.getTaskService().complete(activeTask.getId());
        }
    }

    @ShellMethod("complete single UserTask")
    public void taskComplete(@ShellOption(help="taskExecutionId") String processTaskId){
        processEngine.getTaskService().complete(processTaskId);
    }

    @ShellMethod("print image")
    public String processImage(@ShellOption(help="process instance") String processInstanceId) throws IOException {
        File outputFile = File.createTempFile("bpmn", "image");
        BpmUtils.writeImageOnDisk(processEngine, processInstanceId, outputFile.getPath());
        return outputFile.getPath();
    }


}

