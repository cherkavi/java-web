package com.cherkashyn.vitalii.bpmnui.core.bpm.listener;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

public class TaskEndListener implements TaskListener {
    private static final long	serialVersionUID	= 1L;

    @Override
    public void notify(DelegateTask task) {
        System.out.println("   EndTaskEvent:" + task.getEventName());
    }

}
