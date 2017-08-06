package de.smava.recrt.persistence.repository;

import de.smava.recrt.persistence.model.AppUserEntity;
import de.smava.recrt.persistence.model.AppUserRoleEntity;
import de.smava.recrt.persistence.model.AppUserRoleKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface AppUserRoleRepository extends JpaRepository<AppUserRoleEntity, AppUserRoleKey> {

    List<AppUserRoleEntity> findByKeyAppUser(AppUserEntity appUser);

}
