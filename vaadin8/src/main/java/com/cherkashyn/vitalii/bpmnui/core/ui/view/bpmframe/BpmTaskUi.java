package com.cherkashyn.vitalii.bpmnui.core.ui.view.bpmframe;

import com.cherkashyn.vitalii.bpmnui.core.bpm.BpmUtils;
import com.cherkashyn.vitalii.bpmnui.core.repository.UserRepository;
import com.cherkashyn.vitalii.bpmnui.core.ui.ContentController;
import com.cherkashyn.vitalii.bpmnui.core.ui.common.Caption;
import com.cherkashyn.vitalii.bpmnui.core.ui.common.Exchange;
import com.cherkashyn.vitalii.bpmnui.core.ui.view.processlist.ProcessListView;
import com.vaadin.server.BrowserWindowOpener;
import com.vaadin.server.StreamResource;
import com.vaadin.ui.*;
import org.activiti.engine.ProcessEngine;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.*;


public abstract class BpmTaskUi extends VerticalLayout {

    private static final Logger LOG = LoggerFactory.getLogger(BpmTaskUi.class);

    @Autowired
    protected Caption caption;

    @Autowired
    protected ProcessEngine processEngine;

    @Autowired
    protected Exchange exchange;

    @Autowired
    protected ContentController contentController;

    @Autowired
    protected UserRepository userRepository;

    private File imageFile;

    @PostConstruct
    public void init(){
        this.addComponent(buildButtonPrintAll());
        this.addComponentsAndExpand(getRootComponent());
        this.addComponent(buildButtonSubmit());
        this.addComponent(buildButtonProcessList());
        this.setSizeFull();
    }

    private Component buildButtonSubmit() {
        Button buttonSubmit = new Button(caption.get("bpmtaskui.button.submit"), clickEvent -> {
            LOG.info(">>> SUBMIT task ");
            // TODO check if task still accessible
            // TODO submit task
        });
        buttonSubmit.setWidth("50%");

        HorizontalLayout layout = new HorizontalLayout();
        layout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        layout.addComponent(buttonSubmit);
        layout.setWidth("100%");
        return layout;
    }

    private Component buildButtonProcessList() {
        Button buttonProcessList = new Button(
                caption.get("bpmtaskui.button.processlist"),
                clickEvent -> this.contentController.setRoot(ProcessListView.class)
        );
        buttonProcessList.setWidth("100%");
        return buttonProcessList;
    }

    @PreDestroy
    public void destroy(){
        FileUtils.deleteQuietly(imageFile);
    }

    protected abstract Component getRootComponent();

    private Button buildButtonPrintAll() {
        final Button printAllButton = new Button(caption.get("bpmtaskui.button.image"));
        printAllButton.setWidth("100%");
        // printAllButton.setSizeFull();

        StreamResource resource = new StreamResource((StreamResource.StreamSource) () -> {
            try {
                File outputFile = createFile();
                LOG.debug(" prepare image into file: "+outputFile.getPath());
                BpmUtils.writeImageOnDisk(processEngine, this.exchange.currentProcess.getProcessInstanceId(), outputFile.getPath());
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
