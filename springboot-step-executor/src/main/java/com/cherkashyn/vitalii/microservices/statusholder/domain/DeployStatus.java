package com.cherkashyn.vitalii.microservices.statusholder.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class DeployStatus {

    private long id;

    private Status status;

    @NotBlank
    private String branch;

    private Date createDate;

    private Integer port;

    public DeployStatus(){
    }

    public DeployStatus(@NotNull Status status, @NotBlank String branch) {
        this.status = status;
        this.branch = branch;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    @Override
    public String toString() {
        return "DeployStatus{" +
                ", status=" + status +
                ", branch='" + branch + '\'' +
                ", port=" + port +
                '}';
    }
}

