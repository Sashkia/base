DROP TABLE IF EXISTS AUTHORITIES;
DROP TABLE IF EXISTS USER;
DROP TABLE IF EXISTS LOG;

--USER TABLE
CREATE TABLE IF NOT EXISTS USER (
    IDUSER INT NOT NULL UNIQUE AUTO_INCREMENT,
    USERNAME VARCHAR(255) NOT NULL PRIMARY KEY ,
    PASSWORD  VARCHAR(255) NOT NULL,
    ENABLED BOOLEAN NOT NULL
   
);

--AUTHORITIES TABLE
CREATE TABLE IF NOT EXISTS AUTHORITIES(
      USERNAME VARCHAR(255) NOT NULL,
      AUTHORITY VARCHAR(255) NOT NULL,
      CONSTRAINT FK_AUTHORITIES_USER FOREIGN KEY(USERNAME) REFERENCES USER(USERNAME));
;



