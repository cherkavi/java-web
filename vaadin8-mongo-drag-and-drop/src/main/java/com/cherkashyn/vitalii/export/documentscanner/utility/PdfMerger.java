package com.cherkashyn.vitalii.export.documentscanner.utility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zeroturnaround.exec.ProcessExecutor;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

public class PdfMerger {
    private static final Logger LOG = LoggerFactory.getLogger(BarCodeRecognizer.class);
    private final static String COMMAND ="gs -dBATCH -dNOPAUSE -q -sDEVICE=pdfwrite -sOutputFile=%s %s";

    public File mergePdfFiles(List<File> files){
        File outputFilePdf = null;
        try {
            outputFilePdf = new File(File.createTempFile("report", "output").getPath()+".pdf");
        } catch (IOException e) {
            throw new RuntimeException("can't create Temp file", e);
        }

        String commandToExecute = String.format(COMMAND,
                outputFilePdf.getPath(),
                files.stream().map(f->f.getPath()).collect(Collectors.joining(" "))
        );

        int exitValue;
        try {
            LOG.info("execute external program: " + commandToExecute);
            exitValue = new ProcessExecutor()
                    .commandSplit(commandToExecute)
                    .execute().getExitValue();
        } catch (IOException | InterruptedException | TimeoutException | RuntimeException e) {
            String message = String.format("can't execute command line : %s ", commandToExecute);
            LOG.error(message);
            throw new RuntimeException(message, e);
        }
        if(exitValue==0){
            return outputFilePdf;
        }
        throw new RuntimeException("can't generate output report");
    }

}
