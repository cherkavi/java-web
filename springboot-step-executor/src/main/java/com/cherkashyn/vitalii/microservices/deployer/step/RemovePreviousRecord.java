package com.cherkashyn.vitalii.microservices.deployer.step;

import com.cherkashyn.vitalii.microservices.statusholder.domain.DeployStatus;
import com.cherkashyn.vitalii.microservices.statusholder.domain.Status;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

@Component
public class RemovePreviousRecord extends StepBranchAware {
    @Value("${status-holder.url}")
    String url;


    @Override
    void execute(String branchName) {
        ResponseEntity<DeployStatus[]> result = new RestTemplate().getForEntity(url + "/search/findByBranch?branch="+ branchName, DeployStatus[].class);
        for(DeployStatus eachStatus: result.getBody()){
            if(eachStatus.getStatus().equals(Status.NEW)){
                continue;
            }
            try {
                new RestTemplate().delete(new URI(url+"/"+eachStatus.getId()));
            } catch (URISyntaxException e) {
            }
        }
    }

}
