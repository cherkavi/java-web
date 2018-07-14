package com.cherkashyn.vitalii.bpmnui.core.ui.common;


import com.cherkashyn.vitalii.bpmnui.core.domain.User;
import com.cherkashyn.vitalii.bpmnui.core.ui.view.processlist.ProcessListItem;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import org.activiti.engine.impl.persistence.entity.TaskEntityImpl;

import java.util.HashMap;
import java.util.Map;

public class Exchange {
    public static String ORDERID_KEY="order_id";
    public User user;
    public ProcessListItem currentProcess;
    public TaskEntityImpl currentTask;
}
