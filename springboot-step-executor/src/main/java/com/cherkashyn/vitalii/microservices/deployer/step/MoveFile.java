package com.cherkashyn.vitalii.microservices.deployer.step;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Component
public class MoveFile extends StepBranchAware{

    public static String OUTPUT_FILENAME = "executable jar";

    private final static Logger LOGGER = LoggerFactory.getLogger(MoveFile.class);

    @Value("${uploader.folder}")
    String folderSource;

    @Value("${storage.location}")
    String folderDestination;

    @Override
    public void execute(String branchName) {
        File destinationFile = getFileWithFolder(folderDestination, branchName, ".jar");
        File sourceFile = getFile(folderSource, branchName);
        LOGGER.info(String.format("moving file from upload storage %s to deploy place %s", folderSource, destinationFile.toString()));
        try {
            if(!destinationFile.getParentFile().exists()){
                destinationFile.getParentFile().mkdirs();
            }
            FileUtils.deleteQuietly(destinationFile);
            FileUtils.moveFile(sourceFile, destinationFile);
        } catch (IOException e) {
            throw new IllegalStateException(String.format("can't move file from source %s to destination %s : %s", folderSource, folderDestination, e.getMessage()));
        }
        setContextParameter(OUTPUT_FILENAME, removePrefix(folderDestination, destinationFile));
    }

    private String removePrefix(String folderDestination, File destinationFile) {
        return destinationFile.toString().substring(folderDestination.length());
    }

    private File getFile(String folder, String branchName) {
        File directory = new File(folder);
        return directory.toPath().resolve( branchName).toFile();
    }

    private File getFileWithFolder(String folder, String branchName, String prefix) {
        File directory = new File(folder);
        return directory.toPath().resolve( branchName).resolve(branchName+prefix).toFile();
    }

}
