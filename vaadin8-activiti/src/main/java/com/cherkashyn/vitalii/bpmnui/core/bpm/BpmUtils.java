package com.cherkashyn.vitalii.bpmnui.core.bpm;

import com.cherkashyn.vitalii.bpmnui.core.ui.view.common.ProcessNotFound;
import org.activiti.bpmn.model.*;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.persistence.deploy.DeploymentManager;
import org.activiti.engine.impl.persistence.entity.TaskEntityImpl;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.image.impl.DefaultProcessDiagramGenerator;
import org.springframework.util.CollectionUtils;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BpmUtils {
    private BpmUtils(){
    }

    /**
     * search in Runtime for active processes with specific variable
     * @param processEngine
     * @param key
     * @param value
     * @return
     */
    public static List<ProcessInstance> getRuntimeProcessByVariable(ProcessEngine processEngine, String key, String value){
        return processEngine.getRuntimeService().createProcessInstanceQuery().variableValueEquals(key, value).list();
    }

    /**
     * get all process definitions that were registered previously
     * @param processEngine
     * @return
     */
    public static List<ProcessDefinition> getProcessDefinitions(ProcessEngine processEngine) {
        return processEngine.getRepositoryService().createProcessDefinitionQuery()
                // .processDefinitionKey("")
                .orderByProcessDefinitionId()
                .orderByProcessDefinitionVersion()
                .desc()
                .list();
    }

    /**
     * get all process deployments from engine
     * @param processEngine
     * @return
     */
    public static List<Deployment> getProcessDeployments(ProcessEngine processEngine) {
        return processEngine.getRepositoryService().createDeploymentQuery().list();
    }

    /**
     * get Runtime process by id, get ProcessInstance by ID
     * @param processEngine
     * @param processInstanceId
     * @return
     */
    public static ProcessInstance getProcessInstanceById(ProcessEngine processEngine, String processInstanceId) {
        return processEngine.getRuntimeService()
                .createProcessInstanceQuery()
                .processInstanceId(processInstanceId)
                .singleResult();
    }

    /**
     * get all ProcessInstances from engine by Definition Key/ID
     * @param processEngine
     * @param processDefinitionId
     * @return
     */
    public static List<ProcessInstance> getProcessesInstanceByName(ProcessEngine processEngine, String processDefinitionId) {
        return processEngine.getRuntimeService()
                .createProcessInstanceQuery()
                .processDefinitionKey(processDefinitionId)
                .includeProcessVariables()
                .list();
    }

    /**
     * all active tasks ( Token ) from active process by ProcessInstanceId
     * @param processEngine
     * @param processInstanceId
     * @return
     */
    public static List<Task> getTasksByProcessInstance(ProcessEngine processEngine, String processInstanceId) {
        return processEngine.getTaskService().createTaskQuery()
                .processInstanceId(processInstanceId)
                .active()
                .list();
    }

    public static TaskEntityImpl findActiveTaskByFormKey(ProcessEngine processEngine,
                                                         String processInstanceId,
                                                         String formKey)
            throws ProcessNotFoundException{
        List<Task> tasksByProcessInstance;
        try{
            tasksByProcessInstance = BpmUtils.getTasksByProcessInstance(processEngine, processInstanceId);
            if(CollectionUtils.isEmpty(tasksByProcessInstance)){
                throw new ProcessNotFoundException();
            }
        }catch(ActivitiException e){
            throw new ProcessNotFoundException();
        }
        for(Task eachTask : tasksByProcessInstance){
            if(formKey.equals(eachTask.getFormKey())){
                return (TaskEntityImpl)eachTask;
            }
        }
        return null;
    }



    public static List<String> getTaskCandidateGroups(BpmnModel model, Task activeTask){
        return getTaskCandidateGroups(model, activeTask.getTaskDefinitionKey());
    }

    public static List<String> getTaskCandidateGroups(BpmnModel model, String taskDefinitionKey){
        FlowElement flowElement = model.getMainProcess().getFlowElement(taskDefinitionKey);
        if(flowElement instanceof UserTask){
            return ((UserTask)flowElement).getCandidateGroups();
        }else{
            return new ArrayList<>(0);
        }
    }

    public static List<String> getTaskCandidateUsers(BpmnModel model, Task activeTask){
        return getTaskCandidateUsers(model, activeTask.getTaskDefinitionKey());
    }

    public static List<String> getTaskCandidateUsers(BpmnModel model, String taskDefinitionKey){
        FlowElement flowElement = model.getMainProcess().getFlowElement(taskDefinitionKey);
        if(flowElement instanceof UserTask){
            return ((UserTask)flowElement).getCandidateUsers();
        }else{
            return new ArrayList<>(0);
        }
    }

    public static List<String> getTaskCustomProperties(BpmnModel model, Task activeTask){
        return getTaskCandidateUsers(model, activeTask.getTaskDefinitionKey());
    }

    public static List<CustomProperty> getTaskCustomProperties(BpmnModel model, String taskDefinitionKey){
        FlowElement flowElement = model.getMainProcess().getFlowElement(taskDefinitionKey);
        if(flowElement instanceof UserTask){
            return ((UserTask)flowElement).getCustomProperties();
        }else{
            return new ArrayList<>(0);
        }
    }

    /**
     * Java representation of BPM diagram by ProcessInstance
     * @param processEngine
     * @param processExecution
     * @return
     */
    public static BpmnModel getBpmnModelByProcessExecution(ProcessEngine processEngine, ProcessInstance processExecution) {
        return getBpmnModelByProcessDefinition(processEngine, processExecution.getProcessDefinitionId());
    }

    /**
     * Java representation of BPM diagram by ProcessDefinition
     * @param processEngine
     * @param processDefinitionId
     * @return
     */
    public static BpmnModel getBpmnModelByProcessDefinition(ProcessEngine processEngine, String processDefinitionId) {
        BpmnModel model = processEngine.getRepositoryService().getBpmnModel(processDefinitionId);
        return isModelExists(model)?model:null;
    }

    /**
     * check Model for existence
     * @param model
     * @return
     */
    public static boolean isModelExists(BpmnModel model) {
        return (model != null && model.getLocationMap().size() > 0);
    }


    /**
     * write current representation of the process instance on Disk
     * @param processEngine
     * @param processInstanceId
     * @param path
     */
    public static void writeImageOnDisk(ProcessEngine processEngine, String processInstanceId, String path) {
        BpmnModel model = BpmUtils.getBpmnModelByProcessDefinition(processEngine,
                BpmUtils.getProcessInstanceById(processEngine, processInstanceId).getProcessDefinitionId());
        writeImageOnDisk(processEngine, model, processInstanceId, path);
    }

    /**
     * write current representation of the process instance on Disk
     * @param processEngine
     * @param model
     * @param processInstanceId
     * @param path
     */
    public static void writeImageOnDisk(ProcessEngine processEngine, BpmnModel model, String processInstanceId, String path) {
        // ProcessDiagramGenerator diagramGenerator = processEngine.getProcessEngineConfiguration().getProcessDiagramGenerator();
        DefaultProcessDiagramGenerator diagramGenerator = new DefaultProcessDiagramGenerator();
        List<String> activeActivities = processEngine.getRuntimeService().getActiveActivityIds(processInstanceId);
        try (InputStream diagram = diagramGenerator.generateDiagram(
                model, "jpg", activeActivities, getHighLightFlows(model, activeActivities)
        )){
            FileCopyUtils.copy(diagram, new FileOutputStream(new File(path)));
        } catch (IOException e) {
            throw new RuntimeException("can't save into output JPG file: "+path, e);
        }
    }

    private static List<String> getHighLightFlows(BpmnModel model, List<String> activeActivityIds) {
        List<String> returnValue = new ArrayList<>();
        // executionId -> task
        for(String eachTaskModelId: activeActivityIds){
            FlowElement element = model.getFlowElement(eachTaskModelId);
            if(element instanceof FlowNode){
                returnValue.addAll(((FlowNode)element).getOutgoingFlows().stream().map(f->f.getId()).collect(Collectors.toList()));
            }
        }
        // task ->
        return returnValue;
    }

    private static List<SequenceFlow> getAllSequenceFlow(BpmnModel model) {
        return model.getMainProcess().getFlowElements().stream()
                .filter(e->e instanceof SequenceFlow)
                .map(sf->(SequenceFlow)sf)
                .collect(Collectors.toList());
    }

    public static Map<String, List<ExtensionElement>> getExtensionElementsByTask(BpmnModel model, Task activeTask) {
        return getExtensionElementsByTaskDefinition(model, activeTask.getTaskDefinitionKey());
    }

    public static Map<String, List<ExtensionElement>> getExtensionElementsByTaskDefinition(BpmnModel model, String taskDefinitionKey) {
        return model.getMainProcess().getFlowElement(taskDefinitionKey).getExtensionElements();
    }


//    private void readExtensionElementFromBpmnModel(BpmnModel model, Task activeTask) {
//        Map<String, List<ExtensionElement>> extensionElements = BpmUtils.getExtensionElementsByTask(model, activeTask);
//        for(Map.Entry<String, List<ExtensionElement>> each: extensionElements.entrySet()){
//            System.out.println(String.format(">>> key: %s ", each.getKey()));
//            for(ExtensionElement element: each.getValue()){
//                System.out.println(String.format("> > > name: %s    text: %s   child.string: %s ", element.getName(), element.getElementText(), element.getChildElements().get("string").get(0).getElementText()));
//            }
//        }
//    }


}
