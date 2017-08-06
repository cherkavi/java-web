package de.smava.recrt.rest.model;

import de.smava.recrt.model.AppUser;
import de.smava.recrt.model.UserRole;

import java.util.List;

public class AppUserResource extends DefaultResource implements AppUser {

    private String username;

    private String email;

    private List<UserRole> roles;

    public AppUserResource() {
        super();
    }

    public AppUserResource(AppUser user) {
        this.username = user.getUsername();
        this.email = user.getEmail();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public void setPassword(String password) {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
