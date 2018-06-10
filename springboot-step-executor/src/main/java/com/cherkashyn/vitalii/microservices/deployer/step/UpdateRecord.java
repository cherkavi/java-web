package com.cherkashyn.vitalii.microservices.deployer.step;

import com.cherkashyn.vitalii.microservices.statusholder.domain.DeployStatus;
import com.cherkashyn.vitalii.microservices.statusholder.domain.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;


abstract class UpdateRecord extends StepBranchAware {
    private final static Logger LOGGER = LoggerFactory.getLogger(UpdateRecord.class);
    @Value("${status-holder.url}")
    String url;

    @Override
    void execute(String branchName) {
        ResponseEntity<DeployStatus[]> result = new RestTemplate().getForEntity(url + "/search/findByBranch?branch="+ branchName, DeployStatus[].class);
        RestTemplate restTemplate = new RestTemplate();
        for(DeployStatus eachResult: result.getBody()){
            eachResult.setStatus(getStatus());
            try{
                eachResult.setPort(Integer.parseInt(this.getContextParameter(FreePortFinder.OUTPUT_FREE_PORT).toString()));
            }catch(NumberFormatException | NullPointerException re){}
            restTemplate.put(url + "/" + eachResult.getId(), eachResult);
            LOGGER.info("updated record: "+eachResult);
        }
    }

    abstract Status getStatus();
}
