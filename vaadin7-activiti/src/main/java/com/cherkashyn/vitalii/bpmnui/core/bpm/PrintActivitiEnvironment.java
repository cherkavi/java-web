package com.cherkashyn.vitalii.bpmnui.core.bpm;

import com.cherkashyn.vitalii.bpmnui.core.ui.common.Exchange;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

public class PrintActivitiEnvironment implements JavaDelegate {

    @Autowired
    ApplicationContext context;

    @Override
    public void execute(DelegateExecution delegateExecution) {
        System.out.println("spring-context: " + context);
        System.out.println("ProcessDefinitionId: " + delegateExecution.getProcessDefinitionId());
        System.out.println("ActivityId: " + delegateExecution.getCurrentActivityId());
        System.out.println("OrderId: " + delegateExecution.getVariable(Exchange.ORDERID_KEY));
    }

}
