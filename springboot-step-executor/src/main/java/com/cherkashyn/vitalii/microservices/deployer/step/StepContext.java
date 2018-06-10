package com.cherkashyn.vitalii.microservices.deployer.step;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class StepContext {
    private final static Logger LOGGER = LoggerFactory.getLogger(StepContext.class);
    @FunctionalInterface
    public interface Step {
        void execute(Map<String, Object> parameters);
    }

    private final Map<String, Object> parameters;
    private List<Class<? extends Step>> steps;
    private ApplicationContext context;
    private Class<? extends Step> currentStep;

    public StepContext(Map<String, Object> parameters){
        this.parameters = new HashMap<>(parameters);
        this.steps = new LinkedList<>();
    }

    public StepContext setContext(ApplicationContext context){
        this.context = context;
        return this;
    }

    public StepContext step(Class<? extends Step> nextStep){
        this.steps.add(nextStep);
        return this;
    }

    public static StepContext create(Map.Entry<String, Object> ... entries){
        Map<String, Object> parameters = new HashMap<>();
        if(entries!=null && entries.length>0){
            for(Map.Entry<String, Object> entry : entries){
                parameters.put(entry.getKey(), entry.getValue());
            }
        }
        return new StepContext(parameters);
    }

    @FunctionalInterface
    public interface Result<T>{
        T obtain();
    }

    public <T> T execute(Result<T> result) {
        this.steps.forEach(step->{
            this.currentStep=step;
            LOGGER.info("next step: "+this.currentStep.getSimpleName());
            this.context.getBean(step).execute(this.parameters);
        });
        return result.obtain();
    }

    public Class<? extends Step> getCurrentStep() {
        return this.currentStep;
    }

}
