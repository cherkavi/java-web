package de.smava.recrt.service.impl;

import de.smava.recrt.exception.RecrtServiceException;
import de.smava.recrt.model.AppUser;
import de.smava.recrt.model.BankAccount;
import de.smava.recrt.persistence.model.AppUserEntity;
import de.smava.recrt.persistence.model.BankAccountEntity;
import de.smava.recrt.persistence.repository.AppUserRepository;
import de.smava.recrt.persistence.repository.BankAccountRepository;
import de.smava.recrt.service.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("bankAccountPersistenceService")
public class BankAccountServiceImpl implements BankAccountService {

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Override
    @Transactional
    public List<? extends BankAccount> getByAppUser(String appUserName) throws RecrtServiceException {
        AppUserEntity user = appUserRepository.findOne(appUserName);
        if (user != null) {
            return bankAccountRepository.findByAppUser(user);
        }
        return new ArrayList<>();
    }

    @Override
    @Transactional
    public BankAccount create(BankAccount account) throws RecrtServiceException {
        AppUser user = account.getAppUser();
        // TODO throw exception
        if (user != null) {
            AppUserEntity userEntity = appUserRepository.findOne(account.getAppUser().getUsername());

            if (userEntity != null) {
                BankAccountEntity target = new BankAccountEntity(account.getIban(), account.getBic(), userEntity);
                return bankAccountRepository.save(target);
            }
        }
        return null;
    }
}
