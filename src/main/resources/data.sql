INSERT INTO APP_USER (USER_NAME, ENCRYPTED_PASSWORD) VALUES ('Admin', '$2y$12$pkNTvpnfUmJ3IYixBcTuNeDZuc1uz.DMZ6j6/i0QY.9TkgANfjS0a');
INSERT INTO APP_USER (USER_NAME, ENCRYPTED_PASSWORD) VALUES ('Alice', '$2y$12$lRGeYYZQ8.fWjLLcQ8MyUOkmZ408ZEEO8xbkMVjllShGZLsEnuo2e');
INSERT INTO APP_USER (USER_NAME, ENCRYPTED_PASSWORD) VALUES ('Bob', '$2y$12$xEzt/urzbPLCkhE.LMANgeVWXeNmggFHaKo6XJFjxXHfeI2IWOm9i');
INSERT INTO APP_USER (USER_NAME, ENCRYPTED_PASSWORD) VALUES ('dbuser1', '$2a$10$PrI5Gk9L.tSZiW9FXhTS8O8Mz9E97k2FZbFvGFFaSsiTUIl.TCrFu');

INSERT INTO APP_ROLE (ROLE_NAME) VALUES ('ROLE_ADMIN');
INSERT INTO APP_ROLE (ROLE_NAME) VALUES ('ROLE_USER');

INSERT INTO USER_ROLE (USER_ID, ROLE_ID) VALUES (1, 1); -- User Admin ROLE_ADMIN
INSERT INTO USER_ROLE (USER_ID, ROLE_ID) VALUES (1, 2); -- User Admin ROLE_USER
INSERT INTO USER_ROLE (USER_ID, ROLE_ID) VALUES (2, 2); -- User Alice ROLE_USER
INSERT INTO USER_ROLE (USER_ID, ROLE_ID) VALUES (3, 2); -- USER Bob ROLE_USER