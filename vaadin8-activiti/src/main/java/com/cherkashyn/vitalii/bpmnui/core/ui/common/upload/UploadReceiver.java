package com.cherkashyn.vitalii.bpmnui.core.ui.common.upload;

import com.vaadin.ui.Upload;

import java.io.OutputStream;
import java.nio.file.Path;

/**
 * implementation of {@see Upload.Receiver} with custom saving files
 */
public class UploadReceiver implements Upload.Receiver{

    private final TempStorageOutputStreamGenerator generator;
    private String fileName;
    private String type;

    public UploadReceiver(TempStorageOutputStreamGenerator generator){
        this.generator = generator;
    }

    @Override
    public OutputStream receiveUpload(String fileName, String type) {
        this.fileName = fileName;
        this.type = type;
        return this.generator.getOutputStream();
    }

    public String getFileName() {
        return fileName;
    }

    public String getType() {
        return type;
    }

    public Path getSavedFile() {
        return this.generator.getFilePath();
    }

}
