package com.lukas.minibank.data.entity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name="BANK_ACCOUNT")
public class BankAccount {

    @Id
    @Column(name="BA_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long baId;
    @Column(name="NAME")
    private String name;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="USER_ID", nullable = false)
    private AppUser appUser;
    @Column(name="accountNumber")
    private String accountNumber;
    @Column(name="BALANCE")
    private BigDecimal balance;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CURRENCY_ID", nullable = false)
    private Currency currency;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}
