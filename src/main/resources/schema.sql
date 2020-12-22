DROP TABLE IF EXISTS USER;
DROP TABLE IF EXISTS BANK_ACCOUNT;
DROP TABLE IF EXISTS ACCOUNT_TRANSACTION;

CREATE TABLE APP_USER (
  USER_ID BIGINT AUTO_INCREMENT  PRIMARY KEY,
  FIRST_NAME VARCHAR(250) NOT NULL,
  LAST_NAME VARCHAR(250) NOT NULL,
  PASSWORD VARCHAR(250) DEFAULT NOT NULL
);

CREATE TABLE BANK_ACCOUNT (
  BA_ID BIGINT AUTO_INCREMENT  PRIMARY KEY,
  USER_ID BIGINT NOT NULL,
  ACCOUNT_NUMBER VARCHAR(250) NOT NULL,
  BALANCE BIGINT NOT NULL,
  CURRENCY VARCHAR(250) DEFAULT NOT NULL
);

CREATE TABLE ACCOUNT_TRANSACTION (
  AT_ID BIGINT AUTO_INCREMENT  PRIMARY KEY,
  FROM_BA BIGINT NOT NULL,
  TO_BA BIGINT NOT NULL,
  SOURCE_CURRENCY VARCHAR(250) DEFAULT NOT NULL,
  AMOUNT BIGINT NOT NULL
);

ALTER TABLE BANK_ACCOUNT ADD FOREIGN KEY (USER_ID) REFERENCES APP_USER(USER_ID);
ALTER TABLE ACCOUNT_TRANSACTION ADD FOREIGN KEY (FROM_BA) REFERENCES BANK_ACCOUNT(BA_ID);
ALTER TABLE ACCOUNT_TRANSACTION ADD FOREIGN KEY (TO_BA) REFERENCES BANK_ACCOUNT(BA_ID);
