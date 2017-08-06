DROP TABLE IF EXISTS app_user;
DROP TABLE IF EXISTS app_user_role;
DROP TABLE IF EXISTS bank_account;

CREATE TABLE app_user (
	username VARCHAR(32) PRIMARY KEY,
	password VARCHAR(32) NOT NULL,
  email VARCHAR(64) NOT NULL
);

CREATE TABLE app_user_role (
	app_user_username VARCHAR(32) NOT NULL REFERENCES app_user,
	role INT,
	PRIMARY KEY (app_user_username, role)
);

CREATE TABLE bank_account (
  	iban VARCHAR(34) PRIMARY KEY,
  	bic VARCHAR(11) NOT NULL,
    app_user_username VARCHAR(32) NOT NULL REFERENCES app_user
);


INSERT INTO app_user VALUES ('user1', '1111', 'user1@smava.de');
INSERT INTO app_user VALUES ('user2', '2222', 'user2@smava.de');
INSERT INTO app_user VALUES ('user3', '3333', 'user3@smava.de');

INSERT INTO app_user_role VALUES ('user1', 1);
INSERT INTO app_user_role VALUES ('user2', 0);
INSERT INTO app_user_role VALUES ('user3', 0);

INSERT INTO bank_account VALUES ('TESTIBAN0', 'TESTBIC0', 'user2');