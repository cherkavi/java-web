package de.smava.recrt.service;

import de.smava.recrt.exception.RecrtServiceException;
import de.smava.recrt.model.AppUser;
import de.smava.recrt.model.AppUserRole;

import java.util.List;

public interface AppUserRoleService {

    List<? extends AppUserRole> getByAppUser(AppUser appUser) throws RecrtServiceException;

}
