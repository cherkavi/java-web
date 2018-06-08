package com.cherkashyn.vitalii.bpmnui.core.domain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Objects;

@Entity
public class DeployStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Status status;

    @NotBlank
    private String branch;

    private Date createDate;

    private Integer port;

    @PrePersist
    public void prePersist(){
        if(Objects.isNull(createDate)){
            createDate=new Date();
        }
    }

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
}

