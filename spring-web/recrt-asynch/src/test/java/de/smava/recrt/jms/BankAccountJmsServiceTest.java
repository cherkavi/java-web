package de.smava.recrt.jms;

import de.smava.recrt.jms.config.JmsConfig;
import de.smava.recrt.model.BankAccount;
import de.smava.recrt.service.AppUserRoleService;
import de.smava.recrt.service.AppUserService;
import de.smava.recrt.service.BankAccountService;
import org.easymock.EasyMock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = {JmsConfig.class})
public class BankAccountJmsServiceTest {

    @Configuration
    static class TestConfig {

        @Bean(name = "appUserService")
        public AppUserService getAppUserService() {
            return EasyMock.mock(AppUserService.class);
        }

        @Bean
        public AppUserRoleService getAppUserRoleService() {
            return EasyMock.mock(AppUserRoleService.class);
        }

        @Bean(name = "bankAccountPersistenceService")
        public BankAccountService getBankAccountService() {
            return EasyMock.mock(BankAccountService.class);
        }
    }

    @Autowired
    @Qualifier("bankAccountJmsProducer")
    BankAccountService bankAccountProducer;

    @Autowired
    @Qualifier("bankAccountJmsConsumer")
    BankAccountService bankAccountConsumer;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    @Qualifier("bankAccountPersistenceService")
    BankAccountService bankAccountService;

    @Test
    public void testCreate() throws Exception {

        expect(bankAccountService.create(anyObject(BankAccount.class))).andReturn(null).anyTimes();
        replay(bankAccountService);

        BankAccount bankAccount = new BankAccountTestObject("IBAN1", "BIC1");
        BankAccount bankAccount2 = new BankAccountTestObject("IBAN2", "BIC2");

        bankAccountProducer.create(bankAccount);
        Thread.sleep(512);
        bankAccountProducer.create(bankAccount2);
        Thread.sleep(512);

        assertEquals(bankAccount2.getIban(),
                ((BankAccountJmsConsumer) bankAccountConsumer).getLastReceived().getIban());
        assertEquals(bankAccount2.getBic(),
                ((BankAccountJmsConsumer) bankAccountConsumer).getLastReceived().getBic());
    }
}