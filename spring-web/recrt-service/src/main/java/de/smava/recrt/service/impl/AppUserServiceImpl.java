package de.smava.recrt.service.impl;

import de.smava.recrt.exception.RecrtServiceException;
import de.smava.recrt.model.AppUser;
import de.smava.recrt.persistence.repository.AppUserRepository;
import de.smava.recrt.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("appUserService")
public class AppUserServiceImpl implements AppUserService {

    @Autowired
    private AppUserRepository appUserRepository;

    @Override
    @Transactional
    public List<? extends AppUser> getAll() throws RecrtServiceException {
        return appUserRepository.findAll();
    }

    @Override
    @Transactional
    public AppUser get(String username) {
        return appUserRepository.findOne(username);
    }
}
