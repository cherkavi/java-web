package com.cherkashyn.vitalii.export.documentscanner.utility;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zeroturnaround.exec.ProcessExecutor;
import org.zeroturnaround.exec.ProcessResult;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

public class BarCodeRecognizer {
    private static final Logger LOG = LoggerFactory.getLogger(BarCodeRecognizer.class);
    private final static String COMMAND ="zbarimg -q %s";

    public List<String> recognize(File file){
        String commandToExecute = String.format(COMMAND,file.getPath());
        ProcessResult result;
        try {
            LOG.info("execute external program: " + commandToExecute);
            result = new ProcessExecutor()
                    .commandSplit(commandToExecute)
                    .readOutput(true)
                    .execute();
        } catch (IOException | InterruptedException | TimeoutException | RuntimeException e) {
            String message = String.format("can't execute command line : %s ", commandToExecute);
            LOG.error(message);
            throw new RuntimeException(message, e);
        }

        return result.getOutput().getLines().stream()
                .map(line->StringUtils.substringAfter(line,":"))
                .collect(Collectors.toList());
    }

}
