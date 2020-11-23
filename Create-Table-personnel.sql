DROP TABLE IF EXISTS LIGUE;
DROP TABLE IF EXISTS NIVEAU;
DROP TABLE IF EXISTS DateDepart;
DROP TABLE IF EXISTS EMPLOYE;

CREATE TABLE LIGUE(
   IdLigue INT,
   NomLigue VARCHAR(50),
   constraint pk_Ligue PRIMARY KEY(IdLigue)
)ENGINE=INNODB;




CREATE TABLE EMPLOYE(
   NumEmp INT,
   PrenomEmp VARCHAR(25),
   NomEmp VARCHAR(25),
   MailEmp VARCHAR(50),
   PasswordEmp VARCHAR(25),
   Simple_Employe TINYINT(1),
   Admin TINYINT(1),
   Super_Admin TINYINT(1), 
   DateArrive DATE ,
   DateDepart DATE,
   constraint pk_EMPLOYE PRIMARY KEY(NumEmp)
   )ENGINE=INNODB;
