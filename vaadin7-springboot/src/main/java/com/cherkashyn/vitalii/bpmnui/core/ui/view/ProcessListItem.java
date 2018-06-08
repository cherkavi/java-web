package com.cherkashyn.vitalii.bpmnui.core.ui.view;


public class ProcessListItem {
    private String port;
    private String branch;

    public ProcessListItem(String port, String branch){
        this.port = port;
        this.branch = branch;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

}
