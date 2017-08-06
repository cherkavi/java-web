package de.smava.recrt.service;

import de.smava.recrt.exception.RecrtServiceException;
import de.smava.recrt.model.AppUser;

import java.util.List;

public interface AppUserService {

    List<? extends AppUser> getAll() throws RecrtServiceException;

    AppUser get(String username) throws RecrtServiceException;

}
