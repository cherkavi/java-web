package de.smava.recrt.persistence.repository;

import de.smava.recrt.persistence.model.AppUserEntity;
import de.smava.recrt.persistence.model.BankAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface BankAccountRepository extends JpaRepository<BankAccountEntity, String> {

    List<BankAccountEntity> findByAppUser(AppUserEntity appUser);
}
