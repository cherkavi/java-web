package com.cherkashyn.vitalii.microservices.deployer.step;

import com.cherkashyn.vitalii.microservices.statusholder.domain.Status;
import org.springframework.stereotype.Component;

@Component
public class StartingRecord extends UpdateRecord{
    @Override
    Status getStatus() {
        return Status.STARTING;
    }
}
