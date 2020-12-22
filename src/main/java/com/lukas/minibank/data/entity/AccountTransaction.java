package com.lukas.minibank.data.entity;

import javax.persistence.*;

@Entity
@Table(name = "ACCOUNT_TRANSACTION")
public class AccountTransaction {
    @Id
    @Column(name="AT_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long atId;
    @Column(name="FROM_ACCOUNT")
    private long fromAccount;
    @Column(name="TO_ACCOUNT")
    private long ACCOUNT_NUMBER;
    @Column(name="SOURCE_CURRENCY")
    private String sourceCurrency;
    @Column(name="AMOUNT")
    private long amount;

    public long getAtId() {
        return atId;
    }

    public void setAtId(long atId) {
        this.atId = atId;
    }

    public long getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(long fromAccount) {
        this.fromAccount = fromAccount;
    }

    public long getACCOUNT_NUMBER() {
        return ACCOUNT_NUMBER;
    }

    public void setACCOUNT_NUMBER(long ACCOUNT_NUMBER) {
        this.ACCOUNT_NUMBER = ACCOUNT_NUMBER;
    }

    public String getSourceCurrency() {
        return sourceCurrency;
    }

    public void setSourceCurrency(String sourceCurrency) {
        this.sourceCurrency = sourceCurrency;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }
}
