package com.lukas.minibank.data.entity;

import javax.persistence.*;

@Entity
@Table(name="BANK_ACCOUNT")
public class BankAccount {

    @Id
    @Column(name="BA_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long baId;
    @Column(name="USER_ID")
    private long userId;
    @Column(name="LAST_NAME")
    private String ACCOUNT_NUMBER;
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

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getACCOUNT_NUMBER() {
        return ACCOUNT_NUMBER;
    }

    public void setACCOUNT_NUMBER(String ACCOUNT_NUMBER) {
        this.ACCOUNT_NUMBER = ACCOUNT_NUMBER;
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
