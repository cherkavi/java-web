package com.cherkashyn.vitalii.bpmnui.core.ui.view.processlist;

import com.cherkashyn.vitalii.bpmnui.core.bpm.BpmUIController;
import com.cherkashyn.vitalii.bpmnui.core.bpm.BpmUtils;
import com.cherkashyn.vitalii.bpmnui.core.ui.ContentController;
import com.cherkashyn.vitalii.bpmnui.core.ui.common.Caption;
import com.cherkashyn.vitalii.bpmnui.core.ui.common.Exchange;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.SelectionEvent;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.stream.Collectors;

@Component
@Scope("prototype")
public class ProcessListView extends VerticalLayout implements View{

    @Autowired
    ContentController contentController;

    @Autowired
    Caption caption;

    @Autowired
    BpmUIController bpmAware;

    @Autowired
    Exchange exchange;

    @Autowired
    ProcessEngine processEngine;

    private String processDefinitionKey;

    private ProcessListItem selection=null;
    private BeanItemContainer<ProcessListItem> gridDataProvider = new BeanItemContainer<>(ProcessListItem.class);
    private Grid grid;

    @PostConstruct
    void init() {
        this.processDefinitionKey = getUserProcess();

        this.addComponents(buildGridProcesses());
        this.addComponent(new HorizontalLayout(buildButtonCreateProcess(), buildButtonShowUserTask()));

        this.setSizeFull();
        this.setMargin(true);
        this.setSpacing(true);

        this.refreshGrid();
    }

    private final static SimpleDateFormat SDF = new SimpleDateFormat("dd.MM HH:mm");

    private Grid buildGridProcesses() {
        // this.items.addAll(order.files);
        grid = new Grid();
        grid.setSizeFull();
        grid.addSelectionListener(getGridSelectionListener());

        Grid.Column columnOrder = grid.addColumn("orderId");
        columnOrder.setHeaderCaption(caption.get("processview.grid.columnheader.order"));
        columnOrder.setWidth(100);
        columnOrder.setExpandRatio(1);

//        ButtonRenderer buttonRenderer = new ButtonRenderer();
//        buttonRenderer.addClickListener((ClickableRenderer.RendererClickListener) event ->
//                onClickGridItemRemove((OrderFileItem)event.getItem()) );
//        columnFileSize.setRenderer(buttonRenderer);

        grid.setContainerDataSource(this.gridDataProvider);
        return grid;
    }

    private SelectionEvent.SelectionListener getGridSelectionListener() {
        return selectionEvent -> selectionEvent.getSelected().iterator().next();
    }

    private void refreshGrid(){
        this.selection=null;
        this.gridDataProvider.removeAllItems();
        this.gridDataProvider.addAll(
                BpmUtils.getProcessesInstanceByName(processEngine, this.processDefinitionKey)
                        .stream()
                        .map(ProcessListItem::build)
                        .collect(Collectors.toList())
        );
        this.grid.refreshAllRows();
    }

    private Button buildButtonCreateProcess() {
        Button button =  new Button(caption.get("processlistview.button.create-new"));
        button.setHeight("100px");
        button.addClickListener(clickEvent -> startBpmProcess());
        return button;
    }

    private void startBpmProcess() {
        ProcessInstance processInstance = processEngine.getRuntimeService().startProcessInstanceByKey(this.processDefinitionKey);
        bpmAware.showScreen(processInstance.getProcessInstanceId());
    }

    private Button buildButtonShowUserTask(){
        Button button =  new Button(caption.get("processlistview.button.show-user-task"));
        button.setHeight("100px");
        button.addClickListener(clickEvent -> showUserTaskBySelectedProcess());
        return button;
    }

    private void showUserTaskBySelectedProcess() {
        if(this.selection==null){
            Notification.show(caption.get("processlistview.message.no-selection"), Notification.Type.WARNING_MESSAGE);
            return;
        }
        bpmAware.showScreen(this.selection.getProcessInstanceId());
    }

    /**
     * retrieve first process from the list
     * @return
     */
    private String getUserProcess() {
        return this.exchange.user.getProcesses().iterator().next();
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
    }
}
