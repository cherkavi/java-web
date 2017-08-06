package de.smava.recrt.persistence.model;

import de.smava.recrt.model.UserRole;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
public class AppUserRoleKey implements Serializable {

    @ManyToOne
    @JoinColumn(name = "app_user_username")
    private AppUserEntity appUser;

    @Column(name = "role")
    private UserRole role;

    public AppUserRoleKey() {
        super();
    }

    public AppUserRoleKey(AppUserEntity appUser, UserRole role) {
        this.appUser = appUser;
        this.role = role;
    }

    public AppUserEntity getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUserEntity appUser) {
        this.appUser = appUser;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
}
