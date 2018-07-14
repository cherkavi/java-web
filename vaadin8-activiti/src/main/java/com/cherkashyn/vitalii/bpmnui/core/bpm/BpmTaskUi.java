package com.cherkashyn.vitalii.bpmnui.core.bpm;

import com.cherkashyn.vitalii.bpmnui.core.repository.UserRepository;
import com.cherkashyn.vitalii.bpmnui.core.ui.common.Caption;
import com.cherkashyn.vitalii.bpmnui.core.ui.common.Exchange;
import com.cherkashyn.vitalii.bpmnui.core.ui.view.common.NotAssignedTask;
import com.cherkashyn.vitalii.bpmnui.core.ui.view.common.ProcessNotFound;
import com.cherkashyn.vitalii.bpmnui.core.ui.view.common.TaskAlreadySubmitted;
import com.cherkashyn.vitalii.bpmnui.core.ui.view.common.TaskViewNotFound;
import com.cherkashyn.vitalii.bpmnui.core.ui.view.processlist.ProcessListView;
import com.vaadin.server.BrowserWindowOpener;
import com.vaadin.server.StreamResource;
import com.vaadin.ui.*;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.impl.persistence.entity.TaskEntityImpl;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.addons.md_stepper.HorizontalStepper;
import org.vaadin.addons.md_stepper.Step;
import org.vaadin.addons.md_stepper.StepBuilder;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.*;
import java.util.Arrays;


public abstract class BpmTaskUi extends VerticalLayout {

    private static final Logger LOG = LoggerFactory.getLogger(BpmTaskUi.class);

    @Autowired
    protected Caption caption;

    @Autowired
    protected ProcessEngine processEngine;

    @Autowired
    protected Exchange exchange;

    @Autowired
    private BpmUIController uiController;

    @Autowired
    protected UserRepository userRepository;

    private File imageFile;


    @PostConstruct
    public void init(){
        this.addComponent(buildButtonPrintAll());
        this.addComponentsAndExpand(getRootComponent());
        this.addComponent(buildStepper());
        this.addComponent(buildButtonProcessList());
        this.addComponent(buildStatusBar());
        this.setSizeFull();
    }

    private Component buildStatusBar() {
        Label statusLabel = new Label();
        statusLabel.setCaption("login: "+this.exchange.user.getLogin()+" processId: "+this.exchange.processInstanceId);
        return statusLabel;
    }

    private Component buildStepper() {
        Step step1 = new Step(true, "Caption", "Description", new Label("Content"));
        Step step2 = new StepBuilder()
                .withDefaultActions(true)
                .withCaption("Caption")
                .withDescription("Description")
                .withContent(new Label("Content"))
                .build();

        HorizontalStepper stepper = new HorizontalStepper(Arrays.asList(step1, step2), false);
        stepper.setSizeFull();
        stepper.start();
        return stepper;
    }

    protected abstract Component getRootComponent();

    protected abstract void complete();

    protected void completeUserTask(String formKey, Pair<String, Object> ... values){
        TaskEntityImpl userTaskToComplete;
        try{
            userTaskToComplete = BpmUtils.findActiveTaskByFormKey(this.processEngine, this.exchange.processInstanceId, formKey);
        }catch(ProcessNotFoundException pe){
            uiController.showScreen(ProcessNotFound.class);
            return;
        }
        if(userTaskToComplete==null){
            this.uiController.showScreen(TaskAlreadySubmitted.class);
            return;
        }
        try{
            setProcessVariables(values);
            processEngine.getTaskService().complete(userTaskToComplete.getId());
            // show next UI
            uiController.showScreen(this.exchange.processInstanceId);
        }catch(ActivitiException e){
            uiController.showScreen(ProcessNotFound.class);
            return;
        }
    }

    private void setProcessVariables(Pair<String, Object>[] values) {
        if(values!=null && values.length>0){
            for(Pair<String, Object> eachValue:values){
                processEngine.getRuntimeService().setVariable(this.exchange.processInstanceId, eachValue.getKey(), eachValue.getValue());
            }
        }
    }

    private Component buildButtonProcessList() {
        Button buttonProcessList = new Button(
                caption.get("bpmtaskui.button.processlist"),
                clickEvent -> this.uiController.showScreen(ProcessListView.class)
        );
        buttonProcessList.setWidth("100%");
        return buttonProcessList;
    }

    @PreDestroy
    public void destroy(){
        FileUtils.deleteQuietly(imageFile);
    }

    private Button buildButtonPrintAll() {
        final Button printAllButton = new Button(caption.get("bpmtaskui.button.image"));
        printAllButton.setWidth("100%");

        StreamResource resource = new StreamResource((StreamResource.StreamSource) () -> {
            try {
                File outputFile = createFile();
                LOG.debug(" prepare image into file: "+outputFile.getPath());
                BpmUtils.writeImageOnDisk(processEngine, this.exchange.processInstanceId, outputFile.getPath());
                return new FileInputStream(outputFile);
            } catch (IOException | RuntimeException e) {
                // Notification.show("Can't open file", "report file is not readable", Notification.Type.ERROR_MESSAGE);
                System.err.println("Can't open file, exception:"+e.getMessage());
                return new ByteArrayInputStream(new byte[]{});
            }
        }, "process.jpg");

        resource.setMIMEType("application/jpg");
        resource.getStream().setParameter("Content-Disposition", "attachment; filename=process.jpg");
        resource.getStream().setParameter("Cache-Control", "no-cache, no-store, must-revalidate");
        BrowserWindowOpener browser = new BrowserWindowOpener(resource);
        // FileDownloader downloader = new FileDownloader(resource);
        browser.extend(printAllButton);
        return printAllButton;
    }

    protected File createFile() throws IOException {
        return this.imageFile = File.createTempFile("image", "bpmn");
    }

}
