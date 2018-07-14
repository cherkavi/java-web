package com.cherkashyn.vitalii.export.documentscanner.ui.common.upload;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * generate OutputStream based on Temporary folder
 */
public class TempStorageOutputStreamGenerator {
    private final static String PREFIX = "vaadin";
    private final static String SUFFIX = "upload";
    private Path filePath;


    public OutputStream getOutputStream(){
        try {
            this.filePath = Files.createTempFile(PREFIX, SUFFIX);
        } catch (IOException e) {
            return null;
        }
        try {
            return new FileOutputStream(this.filePath.toFile());
        } catch (FileNotFoundException e) {
            return null;
        }
    }

    public Path getFilePath() {
        return filePath;
    }
}