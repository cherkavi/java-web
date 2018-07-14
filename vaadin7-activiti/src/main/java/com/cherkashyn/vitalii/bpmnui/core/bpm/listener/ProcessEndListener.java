package com.cherkashyn.vitalii.bpmnui.core.bpm.listener;


import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

public class ProcessEndListener implements ExecutionListener {
    private static final long	serialVersionUID	= 1L;

    @Override
    public void notify(DelegateExecution execution) {
        System.out.println(">>> Start ProcessEvent:" + execution.getEventName()
                + "   instance: " + execution.getId());
    }

}
