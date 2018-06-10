package com.cherkashyn.vitalii.microservices.deployer.controller;

import com.cherkashyn.vitalii.microservices.deployer.step.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

@Controller
public class StepExecutor {
    private final static Logger LOGGER = LoggerFactory.getLogger(StepExecutor.class);
    @Autowired
    ApplicationContext context;

    @Autowired
    FailRecord failRecord;

    // curl -X POST -F "branch=feature-OPM-nojira" http://localhost:8080/proceed
    @PostMapping(path="/proceed")
    @ResponseBody
    public String stepExecutor(@RequestParam("branch") String branch){
        StepContext stepContext = StepContext.create(new AbstractMap.SimpleImmutableEntry<>("branch", branch)).setContext(context);
        try{
            return stepContext
                    .step(ProcessKiller.class)
                    .step(RemovePreviousRecord.class)
                    .step(DeployingRecord.class)
                    .step(MoveFile.class)
                    .step(FreePortFinder.class)
                    .step(FreeSshdPortFinder.class)
                    .step(ExecuteProcess.class)
                    .step(StartingRecord.class)
                    .execute(()-> "OK");
        }catch(RuntimeException ex){
            LOGGER.warn("can't execute sequence of steps, issue with: "+stepContext.getCurrentStep().getSimpleName()+" message:"+ex.getMessage());
            setFailRecord(branch);
            // change status to failure
            throw new RuntimeException(String.format(
                    "file execution stopped on step: %s with exception: %s ",
                    stepContext.getCurrentStep().getSimpleName(), ex.getMessage()));
        }
    }

    private void setFailRecord(String branch) {
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("branch", branch);
        failRecord.execute(parameters);
    }


    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<String> exceptionHandling(Exception ex){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }

}
