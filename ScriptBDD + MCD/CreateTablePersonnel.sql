

CREATE TABLE LIGUE
(
   idLigue INT NOT NULL AUTO_INCREMENT,
   nomLigue VARCHAR(50) NOT NULL,
   constraint pk_Ligue PRIMARY KEY(idLigue)
)
ENGINE=INNODB;




CREATE TABLE EMPLOYE
(
   idEmploye INT NOT NULL AUTO_INCREMENT,
   prenom VARCHAR(25) NOT NULL,
   nom VARCHAR(25) NOT NULL,
   password VARCHAR(50) NOT NULL,
   mail VARCHAR(255) NOT NULL,
   dateDebut DATE NOT NULL,
   dateFin DATE default NULL,
   estAdmin boolean default false,
   estRoot boolean default false, 
   idLigue int,
   constraint pk_EMPLOYE PRIMARY KEY(idEmploye)
)
ENGINE=INNODB;

ALTER TABLE EMPLOYE
  ADD CONSTRAINT FK_EmpLigue FOREIGN KEY (idLigue) REFERENCES ligue(idLigue);