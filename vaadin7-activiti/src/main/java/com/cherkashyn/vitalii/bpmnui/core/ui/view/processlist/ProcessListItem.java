package com.cherkashyn.vitalii.bpmnui.core.ui.view.processlist;

import com.cherkashyn.vitalii.bpmnui.core.ui.common.Exchange;
import org.activiti.engine.runtime.ProcessInstance;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ProcessListItem {
    private String orderId;
    private final String processDefinitionId;
    private final String processInstanceId;
//    private final String description;
//    private final Date creationDate;

    public ProcessListItem(String processDefinitionId,
                           String processInstanceId){
        this(null, processDefinitionId, processInstanceId);
    }

    public ProcessListItem(String orderId,
//                           String description,
//                           Date creationDate,
                           String processDefinitionId,
                           String processInstanceId){
        this.orderId = orderId;
//        this.description = description;
//        this.creationDate = creationDate;
        this.processDefinitionId = processDefinitionId;
        this.processInstanceId = processInstanceId;
    }


    public String getProcessDefinitionId() {
        return processDefinitionId;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public String getOrderId() {
        return orderId;
    }

//    public String getDescription() {
//        return description;
//    }
//
//    public Date getCreationDate() {
//        return creationDate;
//    }

    private final static String NO_ORDER_MESSAGE ="no Order assigned yet";
    public static ProcessListItem build(ProcessInstance processInstance){
        String orderFromProcess = (String)processInstance.getProcessVariables().get(Exchange.ORDERID_KEY);
        return new ProcessListItem(
                orderFromProcess==null?NO_ORDER_MESSAGE:orderFromProcess,
                processInstance.getProcessDefinitionId(),
                processInstance.getProcessInstanceId());
    }

}
