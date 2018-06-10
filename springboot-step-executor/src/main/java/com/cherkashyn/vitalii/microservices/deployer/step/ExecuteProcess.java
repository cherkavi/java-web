package com.cherkashyn.vitalii.microservices.deployer.step;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.zeroturnaround.exec.ProcessExecutor;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
public class ExecuteProcess extends StepBranchAware{

    private final static Logger LOGGER = LoggerFactory.getLogger(ExecuteProcess.class);

    @Value("${exec-commandline}")
    String commandLine;

    @Value("${storage.location}")
    String folderDestination;

    List<String> dependsOn(){
        return Arrays.asList(
                MoveFile.OUTPUT_FILENAME,
                FreePortFinder.OUTPUT_FREE_PORT
        );
    }

    @Override
    public void execute(String branchName) {
        File executableFile = new File(folderDestination + this.getContextParameter(MoveFile.OUTPUT_FILENAME)).toPath().toFile();
        String portNumber = this.getContextParameter(FreePortFinder.OUTPUT_FREE_PORT).toString();
        String portNumberSshd = this.getContextParameter(FreeSshdPortFinder.OUTPUT_FREE_PORT_SSHD).toString();
        String executeLine = String.format(commandLine, portNumberSshd, portNumber, executableFile);
        LOGGER.info("Attempt to execute command line: "+executeLine);
        try {
            new ProcessExecutor().commandSplit(executeLine).directory(executableFile.getParentFile()).start();
        } catch (IOException e) {
            LOGGER.error("Can't execute command IOException: ", e);
            throw new IllegalStateException("can't access to file");
        }
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
