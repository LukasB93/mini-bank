DROP TABLE IF EXISTS APP_USER;
DROP TABLE IF EXISTS APP_ROLE;
DROP TABLE IF EXISTS USER_ROLE;
DROP TABLE IF EXISTS CURRENCY;
DROP TABLE IF EXISTS BANK_ACCOUNT;
DROP TABLE IF EXISTS ACCOUNT_TRANSACTION;

CREATE TABLE APP_USER (
    USER_ID BIGINT AUTO_INCREMENT PRIMARY KEY,
    USER_NAME VARCHAR(250) NOT NULL,
    ENCRYPTED_PASSWORD VARCHAR(250) DEFAULT NOT NULL
);

CREATE TABLE APP_ROLE (
    ROLE_ID BIGINT AUTO_INCREMENT PRIMARY KEY,
    ROLE_NAME VARCHAR(30) NOT NULL
);

CREATE TABLE USER_ROLE (
    ID BIGINT AUTO_INCREMENT PRIMARY KEY,
    USER_ID BIGINT NOT NULL,
    ROLE_ID BIGINT NOT NULL
);

ALTER TABLE USER_ROLE ADD FOREIGN KEY (USER_ID) REFERENCES APP_USER(USER_ID);
ALTER TABLE USER_ROLE ADD FOREIGN KEY (ROLE_ID) REFERENCES APP_ROLE(ROLE_ID);

CREATE TABLE CURRENCY (
    C_ID BIGINT AUTO_INCREMENT PRIMARY KEY,
    CODE CHAR(3) NOT NULL,
    RATE DECIMAL NOT NULL,
    LAST_UPDATED TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE TABLE BANK_ACCOUNT (
    BA_ID BIGINT AUTO_INCREMENT  PRIMARY KEY,
    NAME VARCHAR(250) NOT NULL,
    USER_ID BIGINT NOT NULL,
    ACCOUNT_NUMBER VARCHAR(250) NOT NULL,
    BALANCE DECIMAL NOT NULL,
    CURRENCY_ID BIGINT NOT NULL
);

ALTER TABLE BANK_ACCOUNT ADD FOREIGN KEY (USER_ID) REFERENCES APP_USER(USER_ID);
ALTER TABLE BANK_ACCOUNT ADD FOREIGN KEY (CURRENCY_ID) REFERENCES CURRENCY(C_ID);


CREATE TABLE ACCOUNT_TRANSACTION (
    AT_ID BIGINT AUTO_INCREMENT PRIMARY KEY,
    FROM_BA BIGINT NOT NULL,
    TO_BA BIGINT NOT NULL,
    REASON VARCHAR(250) NOT NULL,
    SOURCE_CURRENCY_ID BIGINT DEFAULT NOT NULL,
    ENDPOINT_CURRENCY_ID BIGINT DEFAULT NOT NULL,
    SOURCE_AMOUNT DECIMAL NOT NULL,
    ENDPOINT_AMOUNT DECIMAL NOT NULL,
    TIME TIMESTAMP WITH TIME ZONE NOT NULL
);

ALTER TABLE ACCOUNT_TRANSACTION ADD FOREIGN KEY (FROM_BA) REFERENCES BANK_ACCOUNT(BA_ID);
ALTER TABLE ACCOUNT_TRANSACTION ADD FOREIGN KEY (TO_BA) REFERENCES BANK_ACCOUNT(BA_ID);
ALTER TABLE ACCOUNT_TRANSACTION ADD FOREIGN KEY (SOURCE_CURRENCY_ID) REFERENCES CURRENCY(C_ID);
ALTER TABLE ACCOUNT_TRANSACTION ADD FOREIGN KEY (ENDPOINT_CURRENCY_ID) REFERENCES CURRENCY(C_ID);