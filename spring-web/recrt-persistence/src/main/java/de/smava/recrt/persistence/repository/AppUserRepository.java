package de.smava.recrt.persistence.repository;

import de.smava.recrt.persistence.model.AppUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface AppUserRepository extends JpaRepository<AppUserEntity, String> {

}
