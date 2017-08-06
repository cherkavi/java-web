package de.smava.recrt.rest.model;

import de.smava.recrt.model.BankAccount;

public class BankAccountResource extends DefaultResource implements BankAccount<AppUserResource> {

    private String iban;

    private String bic;

    private AppUserResource appUser;

    public BankAccountResource() {
        super();
    }

    public BankAccountResource(BankAccount bankAccount) {
        this.iban = bankAccount.getIban();
        this.bic = bankAccount.getBic();
        if (bankAccount.getAppUser() != null) {
            this.appUser = new AppUserResource(bankAccount.getAppUser());
        }
    }

    @Override
    public String getIban() {
        return iban;
    }

    @Override
    public void setIban(String iban) {
        this.iban = iban;
    }

    @Override
    public String getBic() {
        return bic;
    }

    @Override
    public void setBic(String bic) {
        this.bic = bic;
    }

    @Override
    public AppUserResource getAppUser() {
        return appUser;
    }

    @Override
    public void setAppUser(AppUserResource appUser) {
        this.appUser = appUser;
    }

}
