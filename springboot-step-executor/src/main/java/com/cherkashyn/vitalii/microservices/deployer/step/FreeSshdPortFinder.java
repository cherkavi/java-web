package com.cherkashyn.vitalii.microservices.deployer.step;

import org.springframework.stereotype.Component;

@Component
public class FreeSshdPortFinder extends PortFinder {
    public static String OUTPUT_FREE_PORT_SSHD="free_sshd_port";

    @Override
    void execute(String branchName) {
        setContextParameter(OUTPUT_FREE_PORT_SSHD, findFirstOpenPort(10000, 19999));
    }

}
