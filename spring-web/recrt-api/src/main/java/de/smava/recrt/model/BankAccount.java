package de.smava.recrt.model;

public interface BankAccount<T extends AppUser> {

    String getIban();

    void setIban(String iban);

    String getBic();

    void setBic(String bic);

    T getAppUser();

    void setAppUser(T appUser);
}
