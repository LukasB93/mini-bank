package com.lukas.minibank.data.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="APP_USER")
public class AppUser {

    @Id
    @Column(name="USER_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long userId;
    @Column(name="USER_NAME")
    private String userName;
    @Column(name="ENCRYPTED_PASSWORD")
    private String encryptedPassword;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }
}
