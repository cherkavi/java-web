package com.cherkashyn.vitalii.microservices.deployer.step;

import org.apache.commons.lang.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.zeroturnaround.exec.ProcessExecutor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.concurrent.TimeoutException;

@Component
public class ProcessKiller extends StepBranchAware{

    private final static Logger LOGGER = LoggerFactory.getLogger(ProcessKiller.class);

    @Value("${storage.location}")
    String folder;

    @Value("${pid-file-name}")
    String pidFileName;

    @Override
    public void execute(String branchName) {
        File pidFile = new File(folder).toPath().resolve(getBranch()).resolve(pidFileName).toFile();
        if(!pidFile.exists()){
            return;
        }
        int processId;
        try {
            processId = Integer.parseInt(Files.readAllLines(pidFile.toPath()).get(0).trim());
        } catch (IOException e) {
            throw new IllegalStateException("can't read file: "+pidFile.toPath());
        }

        LOGGER.info("attempt to kill process: "+processId);
        try {
            killProcess(processId);
        } catch (InterruptedException e) {
            LOGGER.warn("can't stop process - interrupted exception:"+e.getMessage());
            throw new IllegalStateException("can't stop process - interrupted exception", e);
        } catch (IOException e) {
            LOGGER.warn("can't stop process - can't reach process out: "+e.getMessage());
            throw new IllegalStateException("can't stop process - can't reach process out", e);
        } catch (TimeoutException e) {
            LOGGER.warn("can't stop process - timeout exception: "+e.getMessage());
            throw new IllegalStateException("can't stop process - timeout exception", e);
        }
    }


    public static void killProcess(int pid) throws InterruptedException, IOException, TimeoutException {
        if(SystemUtils.IS_OS_LINUX){
            killProcessLinux(pid);
        }
        if(SystemUtils.IS_OS_WINDOWS){
            killProcessWindows(pid);
        }
    }

    private static void killProcessWindows(int pid) throws InterruptedException, TimeoutException, IOException {
        new ProcessExecutor().commandSplit( String.format("Taskkill /pid %d /F", pid)).execute();
    }

    private static void killProcessLinux(int pid) throws InterruptedException, TimeoutException, IOException {
        new ProcessExecutor().commandSplit( String.format("kill -9 %d ", pid)).execute();
    }


}
