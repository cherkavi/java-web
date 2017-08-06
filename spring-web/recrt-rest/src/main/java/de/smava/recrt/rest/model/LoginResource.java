package de.smava.recrt.rest.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginResource {

    private String username;

    private String password;

    private Boolean loggedIn;

    public LoginResource() {
    }

    public LoginResource(Boolean loggedIn, String username) {
        this.loggedIn = loggedIn;
        this.username = username;
    }

    public LoginResource(String username, String password) {
        this.username = username;
        this.password = password;
        this.loggedIn = null;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(Boolean loggedIn) {
        this.loggedIn = loggedIn;
    }
}
