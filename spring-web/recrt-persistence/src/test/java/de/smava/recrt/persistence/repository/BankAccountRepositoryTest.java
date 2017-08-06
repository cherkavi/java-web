package de.smava.recrt.persistence.repository;

import de.smava.recrt.persistence.config.PersistenceConfig;
import de.smava.recrt.persistence.model.AppUserEntity;
import de.smava.recrt.persistence.model.BankAccountEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = PersistenceConfig.class)
public class BankAccountRepositoryTest {

    @Autowired
    BankAccountRepository bankAccountRepository;

    @Test
    public void testFindByAppUser() throws Exception {
        AppUserEntity user = new AppUserEntity("user3");
        BankAccountEntity account = new BankAccountEntity("IBANTEST3", "BICTEST3", user);
        bankAccountRepository.save(account);

        List<BankAccountEntity> result = bankAccountRepository.findByAppUser(user);
        assertEquals(1, result.size());
        assertEquals("IBANTEST3", result.get(0).getIban());

        bankAccountRepository.delete(result);
        BankAccountEntity deleted = bankAccountRepository.findOne("IBANTEST3");
        assertNull(deleted);

    }
}