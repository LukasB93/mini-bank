package com.lukas.minibank.data.entity;

import javax.persistence.*;
import java.math.BigDecimal;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="SOURCE_CURRENCY_ID", nullable = false)
    private Currency sourceCurrency;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="ENDPOINT_CURRENCY_ID", nullable = false)
    private Currency endpointCurrency;

    @Column(name="SOURCE_AMOUNT")
    private BigDecimal sourceAmount;
    @Column(name="ENDPOINT_AMOUNT")
    private BigDecimal endpointAmount;
    @Column(name="TIME")
    private ZonedDateTime time;

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

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Currency getSourceCurrency() {
        return sourceCurrency;
    }

    public void setSourceCurrency(Currency sourceCurrency) {
        this.sourceCurrency = sourceCurrency;
    }

    public Currency getEndpointCurrency() {
        return endpointCurrency;
    }

    public void setEndpointCurrency(Currency endpointCurrency) {
        this.endpointCurrency = endpointCurrency;
    }

    public BigDecimal getSourceAmount() {
        return sourceAmount;
    }

    public void setSourceAmount(BigDecimal sourceAmount) {
        this.sourceAmount = sourceAmount;
    }

    public BigDecimal getEndpointAmount() {
        return endpointAmount;
    }

    public void setEndpointAmount(BigDecimal endpointAmount) {
        this.endpointAmount = endpointAmount;
    }

    public ZonedDateTime getTime() {
        return time;
    }

    public void setTime(ZonedDateTime time) {
        this.time = time;
    }
}
