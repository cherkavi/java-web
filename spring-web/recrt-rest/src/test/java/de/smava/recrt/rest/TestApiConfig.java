package de.smava.recrt.rest;

import de.smava.recrt.service.AppUserRoleService;
import de.smava.recrt.service.AppUserService;
import de.smava.recrt.service.BankAccountService;
import org.easymock.EasyMock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestApiConfig {

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
