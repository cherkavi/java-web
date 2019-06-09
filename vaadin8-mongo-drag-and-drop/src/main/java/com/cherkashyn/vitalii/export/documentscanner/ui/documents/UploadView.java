package com.cherkashyn.vitalii.export.documentscanner.ui.documents;

import com.cherkashyn.vitalii.export.documentscanner.persistent.fs.OrderFSProcessorStorage;
import com.cherkashyn.vitalii.export.documentscanner.persistent.fs.OrderFSStorage;
import com.cherkashyn.vitalii.export.documentscanner.persistent.nosql.OrderFileItem;
import com.cherkashyn.vitalii.export.documentscanner.persistent.nosql.Order;
import com.cherkashyn.vitalii.export.documentscanner.persistent.nosql.OrderRepository;
import com.cherkashyn.vitalii.export.documentscanner.ui.common.Caption;
import com.cherkashyn.vitalii.export.documentscanner.ui.common.Exchange;
import com.cherkashyn.vitalii.export.documentscanner.ui.common.upload.TempStorageOutputStreamGenerator;
import com.cherkashyn.vitalii.export.documentscanner.ui.common.upload.UploadReceiver;
import com.cherkashyn.vitalii.export.documentscanner.utility.PdfMerger;
import com.vaadin.data.ValueProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.event.dd.acceptcriteria.Not;
import com.vaadin.navigator.View;
import com.vaadin.server.*;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.navigator.SpringNavigator;
import com.vaadin.ui.*;
import com.vaadin.ui.dnd.FileDropTarget;
import com.vaadin.ui.renderers.ButtonRenderer;
import com.vaadin.ui.renderers.ClickableRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.vaadin.dialogs.ConfirmDialog;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@SpringView(name="upload")
public class UploadView extends GridLayout implements View, FileItemUpdater {
    private static final Logger LOG = LoggerFactory.getLogger(UploadView.class);

    @Autowired
    Exchange exchange;

    @Autowired
    SpringNavigator navigator;

    @Autowired
    OrderRepository repository;

    @Qualifier("fsOriginalFilesTemp")
    @Autowired
    OrderFSProcessorStorage fileSystemStorageTemp;

    @Qualifier("fsOriginalFilesPermanent")
    @Autowired
    OrderFSStorage fileSystemStoragePermanent;

    @Qualifier("fsIcons")
    @Autowired
    OrderFSStorage fileSystemIcons;

    @Autowired
    PdfMerger pdfMerger;

    @Autowired
    Caption caption;

    private LinkedList<OrderFileItem> uiFileItemList = new LinkedList<>();

    private ListDataProvider<OrderFileItem> fileListDataProvider = new ListDataProvider<>(uiFileItemList);
    private Image image = new Image();


    public UploadView(){
        super(5,10);
        this.setSizeFull();
        this.setMargin(true);
        this.setSpacing(true);
    }

    @PostConstruct
    void afterPropertiesSet() {
        this.addComponent(buildFileListGrid(), 0,0, 1,6);

        this.addComponent(buildPanelSelectedImage(), 2,0, 4, 6);

        this.addComponent(buildButtonDone(), 0,7, 1,7);

        this.addComponent(buildButtonPrintAll(), 0,8, 1,8);

        this.addComponent(buildPanelDropUploadArea(), 2,7, 4,8);

        if(exchange.order.wasSaved() && !exchange.order.files.isEmpty()){
            initUIData(exchange.order);
        }
    }

    private void initUIData(Order order) {
        LOG.debug("data exists into DB, upload to ui ");
        this.uiFileItemList.addAll(order.files);
        this.uiFileItemList.stream().forEach(fileItem->
                this.fileSystemStorageTemp.copyFileItem(this.fileSystemStoragePermanent, exchange.order, fileItem)
        );
        fileListDataProvider.refreshAll();
        showSelectedItem(this.uiFileItemList.getFirst());
    }

    private Panel buildPanelDropUploadArea() {
        Label dropTargetLabel = new Label("drop image(s) here");
        ProgressBar progress = new ProgressBar();
        progress.setIndeterminate(false);
        progress.setVisible(false);
        progress.setWidth("100%");

        final VerticalLayout dragAndDropLayout = new VerticalLayout(dropTargetLabel, progress);
        dragAndDropLayout.setSizeFull();
        dragAndDropLayout.setComponentAlignment(dropTargetLabel , Alignment.MIDDLE_CENTER);
        dragAndDropLayout.addStyleName("drop-area");
        dragAndDropLayout.setSizeUndefined();
        dragAndDropLayout.setWidth("100%");

        Panel panelDragAndDrop = new Panel(dragAndDropLayout);
        panelDragAndDrop.setSizeFull();


        new FileDropTarget<>(panelDragAndDrop, fileDropEvent -> fileDropEvent.getFiles().stream()
                .filter(html5file->html5file.getFileSize() < UploadManager.SIZE_LIMIT)
                .forEach(html5file->html5file.setStreamVariable(
                        new UploadManager(this.exchange.order,
                                this.fileSystemStorageTemp,
                                progress,
                                new OrderFileItem(html5file.getFileName(), html5file.getFileSize()),
                                UploadView.this))));
        return panelDragAndDrop;
    }

    // need to consider: http://vaadintutorial.blogspot.de/2014/03/how-to-download-file-from-server-in.html
    private Button buildButtonPrintAll() {
        final Button printAllButton = new Button("printAll");
        printAllButton.setSizeFull();

        StreamResource resource = new StreamResource((StreamResource.StreamSource) () -> {
            try {
                File outputFile = pdfMerger.mergePdfFiles(
                        this.exchange.order.files.stream()
                                .map(item-> this.fileSystemStoragePermanent.getRealFile(this.exchange.order, item))
                                .collect(Collectors.toList())
                );
                LOG.debug("printAll file: "+outputFile);
                return new FileInputStream(outputFile);
            } catch (FileNotFoundException | RuntimeException e) {
                Notification.show("Can't open file", "report file is not readable", Notification.Type.ERROR_MESSAGE);
                return new ByteArrayInputStream(new byte[]{});
            }
        }, "report.pdf");

        resource.setMIMEType("application/pdf");
        resource.getStream().setParameter("Content-Disposition", "attachment; filename=report.pdf");
        resource.getStream().setParameter("Cache-Control", "no-cache, no-store, must-revalidate");
        BrowserWindowOpener browser = new BrowserWindowOpener(resource);
        // FileDownloader downloader = new FileDownloader(resource);
        browser.extend(printAllButton);
        return printAllButton;
    }

    private Button buildButtonDone(){
        Button printAllButton = new Button("done");
        printAllButton.setSizeFull();
        printAllButton.addClickListener(clickEvent -> onClickDone());
        return printAllButton;
    }

    private Upload buildButtonUpload() {
        final Upload upload = new Upload();
        upload.setSizeFull();
        upload.setButtonCaption("upload file");
        upload.setDescription("description");

        final UploadReceiver receiver = new UploadReceiver(new TempStorageOutputStreamGenerator());
        upload.setReceiver(receiver);
        upload.addSucceededListener(succeededEvent ->
                    addElementWithMovingFile(
                            new OrderFileItem(succeededEvent.getFilename(), succeededEvent.getLength()),
                            receiver.getSavedFile().toFile())
                );
        upload.addFailedListener(failedEvent -> image.setVisible(false));
        return upload;
    }

    private Panel buildPanelSelectedImage() {
        image.setVisible(false);
        Panel panelImage = new Panel("selected image", image);
        panelImage.setSizeFull();
        return panelImage;
    }

    private Panel buildFileListGrid() {
        final Grid<OrderFileItem> grid = new Grid<>();
        grid.setSizeFull();
        grid.addSelectionListener(selectionEvent -> selectionEvent.getFirstSelectedItem().ifPresent(this::showSelectedItem));

        Grid.Column columnFileName = grid.addColumn((ValueProvider<OrderFileItem, String>) uiFileItem -> uiFileItem.name);
        columnFileName.setCaption("name");

        Grid.Column columnFileSize = grid.addColumn((ValueProvider<OrderFileItem, Long>) uiFileItem -> uiFileItem.size);
        columnFileSize.setCaption("size");
        columnFileSize.setWidth(150);

        ButtonRenderer buttonRenderer = new ButtonRenderer();
        buttonRenderer.addClickListener((ClickableRenderer.RendererClickListener) event ->
                onClickGridItemRemove((OrderFileItem)event.getItem()) );
        columnFileSize.setRenderer(buttonRenderer);

        grid.setDataProvider(fileListDataProvider);

        Panel panelUploadList = new Panel("List of files to be saved:"+this.exchange.order.orderId, grid);
        panelUploadList.setSizeFull();
        return panelUploadList;
    }

    private void onClickGridItemRemove(OrderFileItem item) {
        LOG.info(item.toString());
        ConfirmDialog.show(UI.getCurrent(),
                "Please Confirm:",
                "Are you really sure to remove file ?",
                "Yes", "No",
                (ConfirmDialog.Listener) dialog -> {
                    if (dialog.isConfirmed()) {
                        removeGridItemAndUpdate(item);
                    }
                });
    }

    public void removeGridItemAndUpdate(OrderFileItem item){
        if(!uiFileItemList.contains(item)){
            return;
        }
        uiFileItemList.remove(item);
        fileListDataProvider.refreshAll();
        image.setVisible(false);
    }


    void onClickDone(){
        this.fileSystemIcons.cleanUp(this.exchange.order);
        this.navigator.navigateTo("");
    }

    @Override
    public void addElementWithMovingFile(OrderFileItem item, File sourceToBeMoved){
        LOG.info("file uploaded: "+item.name);
        if(uiFileItemList.contains(item)){
            LOG.warn("file already exists : "+item);
            Notification.show("not added", "Already exists",Notification.Type.WARNING_MESSAGE);
            return;
        }
        this.fileSystemStorageTemp.setFileItem(this.exchange.order, item, sourceToBeMoved);
        uiFileItemList.addFirst(item);
        fileListDataProvider.refreshAll();
        showSelectedItem(item);
    }

    void showSelectedItem(OrderFileItem item){
        image.setVisible(true);
        image.setSource(new FileResource(this.fileSystemIcons.getRealFile(this.exchange.order, item)));
    }

}

interface FileItemUpdater {
    void addElementWithMovingFile(OrderFileItem item, File source);
}


class UploadManager implements StreamVariable {
    static final long SIZE_LIMIT = 20 * 1024 * 1024; // 20MB
    private static final Logger LOG = LoggerFactory.getLogger(UploadManager.class);

    private final ProgressBar progress;
    private final TempStorageOutputStreamGenerator generator;
    private final OrderFileItem fileItem;
    private final FileItemUpdater updater;
    private final OrderFSStorage storage;
    private final Order order;

    UploadManager(Order order, OrderFSStorage tempStorage, ProgressBar progressBar, OrderFileItem fileItem, FileItemUpdater updater){
        this.progress = progressBar;
        this.generator = new TempStorageOutputStreamGenerator();
        this.fileItem = fileItem;
        this.updater = updater;
        this.storage = tempStorage;
        this.order = order;
    }

    @Override
    public OutputStream getOutputStream() {
        return this.generator.getOutputStream();
    }

    @Override
    public void streamingStarted(final StreamingStartEvent event) {
        progress.setVisible(true);
        progress.setCaption(event.getFileName());
    }

    @Override
    public void onProgress(final StreamingProgressEvent event) {
        progress.setValue(event.getBytesReceived() / event.getContentLength());
    }

    @Override
    public void streamingFinished(
            final StreamingEndEvent event) {
        progress.setVisible(false);
        LOG.info("new file uploaded: "+this.fileItem.name+"  into: "+this.generator.getFilePath() + " ( "+this.fileItem.size+" ) ");
        this.updater.addElementWithMovingFile(this.fileItem, this.generator.getFilePath().toFile());
    }

    @Override
    public void streamingFailed(final StreamingErrorEvent event) {
        progress.setVisible(false);
    }

    @Override
    public boolean listenProgress() { return false; }

    @Override
    public boolean isInterrupted() { return false; }
}




