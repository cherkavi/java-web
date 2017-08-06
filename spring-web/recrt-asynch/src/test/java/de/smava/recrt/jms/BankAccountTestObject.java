package de.smava.recrt.jms;

import de.smava.recrt.model.AppUser;
import de.smava.recrt.model.BankAccount;

public class BankAccountTestObject implements BankAccount {

    private String iban;
    private String bic;

    public BankAccountTestObject() {
        super();
    }

    public BankAccountTestObject(String iban, String bic) {
        this.iban = iban;
        this.bic = bic;
    }

    @Override
    public String getIban() {
        return this.iban;
    }

    @Override
    public void setIban(String iban) {
        this.iban = iban;
    }

    @Override
    public String getBic() {
        return this.bic;
    }

    @Override
    public void setBic(String bic) {
        this.bic = bic;
    }

    @Override
    public AppUser getAppUser() {
        return null;
    }

    @Override
    public void setAppUser(AppUser appUser) {

    }
}
