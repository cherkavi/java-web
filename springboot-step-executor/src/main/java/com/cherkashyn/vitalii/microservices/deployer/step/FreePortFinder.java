package com.cherkashyn.vitalii.microservices.deployer.step;

import org.springframework.stereotype.Component;

@Component
public class FreePortFinder extends PortFinder{
    public static String OUTPUT_FREE_PORT="free_http_port";

    @Override
    void execute(String branchName) {
        setContextParameter(OUTPUT_FREE_PORT, findFirstOpenPort(9000, 9999));
    }
}
