package com.cherkashyn.vitalii.bpmnui.service;

import com.cherkashyn.vitalii.bpmnui.core.ui.common.Exchange;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("notificationCreateOrder")
public class NotificationCreateOrder implements JavaDelegate {

    @Autowired
    ProcessEngine engine;

    @Override
    public void execute(DelegateExecution delegateExecution) {
        System.out.println("place for Notification about Order Creation: "+engine.getRuntimeService().getVariable(delegateExecution.getProcessInstanceId(), Exchange.ORDERID_KEY));

        System.out.println("id: "+delegateExecution.getId());
        System.out.println("DefinitionId: "+delegateExecution.getProcessDefinitionId());
        System.out.println("InstanceId: "+delegateExecution.getProcessInstanceId());
        System.out.println("ParentId: "+delegateExecution.getParentId());
        System.out.println("ProcessInstanceId: "+delegateExecution.getRootProcessInstanceId());
        System.out.println("CurrentFlowElement: "+delegateExecution.getCurrentFlowElement());
        System.out.println("CurrentActivitiId: "+delegateExecution.getCurrentActivityId());
        System.out.println("InstanceBusinessKey: "+delegateExecution.getProcessInstanceBusinessKey());
        System.out.println("TenantId: "+delegateExecution.getTenantId());
    }

}
