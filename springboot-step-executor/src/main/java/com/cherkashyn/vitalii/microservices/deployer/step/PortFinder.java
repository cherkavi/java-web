package com.cherkashyn.vitalii.microservices.deployer.step;

import java.io.IOException;
import java.net.ServerSocket;


abstract class PortFinder extends StepBranchAware {

    int findFirstOpenPort(int lowRange, int highRange) throws IllegalStateException {
        for (int index=lowRange; index<highRange; index++) {
            try {
                ServerSocket socket = new ServerSocket(index);
                socket.close();
                return index;
            } catch (IOException ex) {
                continue; // try next port
            }
        }
        throw new IllegalStateException(String.format("no free port found into the range %d .. %d", lowRange, highRange));
    }
}
