package com.lukas.minibank.data.entity;

import javax.persistence.*;
import java.time.ZonedDateTime;


@Entity
@Table(name = "ACCOUNT_TRANSACTION")
public class AccountTransaction {
    @Id
    @Column(name="AT_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long atId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="FROM_BA", nullable = false)
    private BankAccount fromAccount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="TO_BA", nullable = false)
    private BankAccount toAccount;

    @Column(name = "REASON")
    private String reason;
    @Column(name="SOURCE_CURRENCY")
    private String sourceCurrency;
    @Column(name="AMOUNT")
    private long amount;
    @Column(name="TIME")
    private ZonedDateTime time;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public ZonedDateTime getTime() {
        return time;
    }

    public void setTime(ZonedDateTime time) {
        this.time = time;
    }

    public long getAtId() {
        return atId;
    }

    public void setAtId(long atId) {
        this.atId = atId;
    }

    public BankAccount getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(BankAccount fromAccount) {
        this.fromAccount = fromAccount;
    }

    public BankAccount getToAccount() {
        return toAccount;
    }

    public void setToAccount(BankAccount toAccount) {
        this.toAccount = toAccount;
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
