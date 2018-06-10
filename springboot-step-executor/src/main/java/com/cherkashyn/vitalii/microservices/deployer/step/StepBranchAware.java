package com.cherkashyn.vitalii.microservices.deployer.step;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

abstract class StepBranchAware implements StepContext.Step {

    private Map<String, Object> parameter;

    String getBranch(){
        return (String)this.parameter.get("branch");
    }

    @Override
    public void execute(Map<String, Object> parameters) {
        this.parameter = parameters;
        checkParameters();
        execute(getBranch());
    }

    abstract void execute(String branchName);

    <T> T setContextParameter(String parameterName, T value) {
        this.parameter.put(parameterName, value);
        return value;
    }

    <T> T getContextParameter(String parameterName) {
        return (T)this.parameter.get(parameterName);
    }

    List<String> dependsOn(){
        return new ArrayList<String>(0);
    }

    private void checkParameters() {
        for(String mandatoryParameterName : this.dependsOn()){
            if(!this.parameter.containsKey(mandatoryParameterName)){
                throw new IllegalArgumentException(String.format("can't execute next step %s because mandatory parameter is not present: %s", this.getClass().getSimpleName(), mandatoryParameterName));
            }
        }
    }

}
