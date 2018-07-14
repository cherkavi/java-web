package com.cherkashyn.vitalii.export.documentscanner.persistent.fs;

import com.cherkashyn.vitalii.export.documentscanner.persistent.nosql.Order;
import com.cherkashyn.vitalii.export.documentscanner.persistent.nosql.OrderFileItem;
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
import java.util.stream.Stream;

public class SetPostProcessor extends OrderFS{
    private static final Logger LOG = LoggerFactory.getLogger(OrderFSStorage.class);
    private final String commandLine;
    private final List<String> allowedExtensions;

    /**
     * @param rootFolder
     * @param commandLine - command line to execute external process in {@see String.format} with two parameters:
     *                    <ul>
     *                      <li>%1 - source file </li>
     *                      <li>%2 - destination file </li>
     *                    </ul>
     */
    public SetPostProcessor(String rootFolder, String commandLine, String ... fileextensions){
        super(rootFolder);
        this.commandLine = commandLine;
        if(fileextensions!=null && fileextensions.length>0){
            this.allowedExtensions = Stream.of(fileextensions)
                    .map(StringUtils::trimToEmpty)
                    .map(StringUtils::lowerCase)
                    .collect(Collectors.toList());
        }else{
            allowedExtensions = null;
        }
    }
    public void process(Order order, OrderFileItem fileItem, File source) {
        if(this.allowedExtensions != null){
            if (!this.allowedExtensions.contains(StringUtils.substringAfterLast(fileItem.name, ".").trim().toLowerCase())){
                return;
            }
        }
        File destination = getRealFile(order, fileItem);
        destination.getParentFile().mkdirs();
        executeExternalProgram(source, destination);
    }

    private void executeExternalProgram(File source, File destination) {
        ProcessResult result=null;
        String commandToExecute = String.format(this.commandLine, source.getPath(), destination.getPath());
        try {
            LOG.info("execute external program: " + commandToExecute);
            result = new ProcessExecutor()
                    .commandSplit(commandToExecute)
                    .execute();
        } catch (IOException | InterruptedException | TimeoutException | RuntimeException e) {
            String message = String.format("can't execute command line : %s ", commandToExecute);
            LOG.error(message);
            throw new RuntimeException(message, e);
        }
        if(result.getExitValue() != 0){
            String message = String.format("command [%s]return result is not 0 : %d ", commandToExecute, result.getExitValue());
            LOG.error(message);
            throw new RuntimeException(message);
        }
    }
}

