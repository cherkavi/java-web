package de.smava.recrt.service.impl;

import de.smava.recrt.exception.RecrtServiceException;
import de.smava.recrt.model.AppUser;
import de.smava.recrt.model.AppUserRole;
import de.smava.recrt.persistence.model.AppUserEntity;
import de.smava.recrt.persistence.repository.AppUserRoleRepository;
import de.smava.recrt.service.AppUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AppUserRoleServiceImpl implements AppUserRoleService {

    @Autowired
    private AppUserRoleRepository repository;

    @Override
    @Transactional
    public List<? extends AppUserRole> getByAppUser(AppUser appUser) throws RecrtServiceException {
        return repository.findByKeyAppUser((AppUserEntity) appUser);
    }
}
