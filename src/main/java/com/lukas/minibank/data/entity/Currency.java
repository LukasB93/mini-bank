package com.lukas.minibank.data.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Entity
@Table(name = "CURRENCY")
public class Currency {

    @Id
    @Column(name="C_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long cId;
    @Column(name="CODE", unique = true)
    private String code;
    @Column(name="RATE")
    private BigDecimal rate;
    @Column(name="LAST_UPDATED")
    private ZonedDateTime lastUpdated;

    public ZonedDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(ZonedDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }
}
