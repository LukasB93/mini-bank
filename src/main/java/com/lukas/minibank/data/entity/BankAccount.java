package com.lukas.minibank.data.entity;

import javax.persistence.*;

@Entity
@Table(name="BANK_ACCOUNT")
public class BankAccount {

    @Id
    @Column(name="BA_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long baId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="USER_ID", nullable = false)
    private AppUser appUser;

    @Column(name="accountNumber")
    private String accountNumber;
    @Column(name="BALANCE")
    private long balance;
    @Column(name="CURRENCY")
    private String currency;

    public long getBaId() {
        return baId;
    }

    public void setBaId(long baId) {
        this.baId = baId;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
