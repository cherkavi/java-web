package com.cherkashyn.vitalii.bpmnui.core.domain;

import java.util.Set;

public class User {
    private int id;
    private String login;
    private String password;
    private String name;
    private String surname;
    private Set<String> groups;
    private Set<String> processes;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Set<String> getGroups() {
        return groups;
    }

    public void setGroups(Set<String> groups) {
        this.groups = groups;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public Set<String> getProcesses() {
        return processes;
    }

    public void setProcesses(Set<String> processes) {
        this.processes = processes;
    }

    @Override
    public String toString() {
        return "UserReadOnly{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", groups=" + groups +
                ", processes=" + processes +
                '}';
    }
}
