

DROP TABLE IF EXISTS LIGUE;
DROP TABLE IF EXISTS EMPLOYE;

CREATE TABLE LIGUE(
   idLigue INT NOT NULL AUTO_INCREMENT,
   nomLigue VARCHAR(50),
   CONSTRAINT pk_ligue PRIMARY KEY (idLigue)
)ENGINE = innoDB;

CREATE TABLE EMPLOYE(
   idEmploye INT NOT NULL AUTO_INCREMENT,
   nomE VARCHAR(50),
   prenomE VARCHAR(50),
   DateDebut DATE,
   DateFin DATE,
   mailE VARCHAR(50),
   passwordE VARCHAR(50),
   estAdmin BOOLEAN DEFAULT false,
   ligue INT,
   estRoot BOOLEAN DEFAULT false,
   CONSTRAINT pk_employe PRIMARY KEY (idEmploye)
)ENGINE = innoDB;

ALTER TABLE EMPLOYE
  ADD CONSTRAINT FK_EmpLigue FOREIGN KEY (Ligue) REFERENCES ligue(idLigue);