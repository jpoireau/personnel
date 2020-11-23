DROP TABLE IF EXISTS LIGUE;
DROP TABLE IF EXISTS NIVEAU;
DROP TABLE IF EXISTS DateDepart;
DROP TABLE IF EXISTS EMPLOYE;

CREATE TABLE LIGUE(
   IdLigue INT,
   NomLigue VARCHAR(50),
   constraint pk_Ligue primary key(IdLigue)
   )ENGINE=INNODB;




CREATE TABLE NIVEAU(
   NumNiveau INT,
   LibNiveau VARCHAR(50),
   constraint pk_NIVEAU PRIMARY KEY(NumNiveau)
)ENGINE=INNODB;


CREATE TABLE DATEDEPART(
   DateDepart DATE,
   NumE INT,
   constraint pk_DATEDEPART PRIMARY KEY(DateDepart)
   /*constraint fk_DATEDEPART FOREIGN KEY(NumE) references EMPLOYE(NumEmp)*/
)ENGINE=INNODB;



CREATE TABLE EMPLOYE(
   NumEmp INT,
   PrenomEmp VARCHAR(25),
   NomEmp VARCHAR(25),
   MailEmp VARCHAR(50),
   PasswordEmp VARCHAR(25),
   DateArrive DATE ,
   constraint pk_EMPLOYE PRIMARY KEY(NumEmp),
   /*constraint fk_EMPLOYE FOREIGN KEY(NumEmp) references DATEDEPART(NumE)*/
)ENGINE=INNODB;
