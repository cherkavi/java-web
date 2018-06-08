package com.cherkashyn.vitalii.bpmnui.core.ui.view;

import com.cherkashyn.vitalii.bpmnui.core.domain.DeployStatus;
import com.cherkashyn.vitalii.bpmnui.core.repository.DeployStatusRepository;
import com.cherkashyn.vitalii.bpmnui.core.ui.common.Caption;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.Page;
import com.vaadin.ui.Grid;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.renderers.ButtonRenderer;
import com.vaadin.ui.renderers.ClickableRenderer;
import org.apache.commons.collections.IteratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;

@Component
@Scope("prototype")
public class ProcessListView extends VerticalLayout{

    @Autowired
    Caption caption;

    @Autowired
    DeployStatusRepository repository;

    private ProcessListItem selection=null;
    private BeanItemContainer<DeployStatus> gridDataProvider = new BeanItemContainer<>(DeployStatus.class);
    private Grid grid;

    @PostConstruct
    void init() {
        this.addComponents(buildGridProcesses());

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
        // grid.addSelectionListener(getGridSelectionListener());

        Grid.Column columnBranch = grid.addColumn("branch");
        columnBranch.setHeaderCaption(caption.get("grid.column.branch"));
        columnBranch.setWidth(300);
        columnBranch.setExpandRatio(1);

        Grid.Column columnPort = grid.addColumn("port");
        columnPort.setHeaderCaption(caption.get("grid.column.port"));
        columnPort.setWidth(100);
        columnPort.setExpandRatio(1);
        columnPort.setRenderer(new ButtonRenderer((ClickableRenderer.RendererClickListener) rendererClickEvent -> {
            gotoLink((DeployStatus)rendererClickEvent.getItemId());
        } ));

        Grid.Column columnStatus = grid.addColumn("status");
        columnStatus.setHeaderCaption(caption.get("grid.column.status"));
        columnStatus.setWidth(150);
        columnStatus.setExpandRatio(1);

//        ButtonRenderer buttonRenderer = new ButtonRenderer();
//        buttonRenderer.addClickListener((ClickableRenderer.RendererClickListener) event ->
//                onClickGridItemRemove((OrderFileItem)event.getItem()) );
//        columnFileSize.setRenderer(buttonRenderer);

        grid.setContainerDataSource(this.gridDataProvider);
        return grid;
    }

    private void gotoLink(DeployStatus source) {
        String link = "http://vldn337:"+source.getPort();
        Page.getCurrent().open(link, "_blank");
    }

    private void refreshGrid(){
        this.selection=null;
        this.gridDataProvider.removeAllItems();
        this.gridDataProvider.addAll(IteratorUtils.toList(repository.findAll().iterator()));
        this.grid.refreshAllRows();
    }

}
