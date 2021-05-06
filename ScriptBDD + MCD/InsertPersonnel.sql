INSERT INTO LIGUE VALUES(1,"Ligue de football");
INSERT INTO LIGUE VALUES(2, "Ligue de basketball");
INSERT INTO LIGUE VALUES(3, "Ligue de natation");
INSERT INTO LIGUE VALUES(4, "Ligue de badminton");
INSERT INTO LIGUE VALUES(5, "Ligue de tennis");
INSERT INTO LIGUE VALUES(6, "Ligue de bowling");

INSERT INTO EMPLOYE(idEmploye, nomE,prenomE,DateDebut,DateFin,mailE,passwordE,estAdmin,ligue, estRoot) VALUES(1,"root","",null,null,"root","toor",false,null, true);
INSERT INTO EMPLOYE(idEmploye, nomE,prenomE,DateDebut,DateFin,mailE,passwordE,estAdmin,ligue) VALUES(2,"Bois","Julien", '2020-01-01', '2021-05-03',"fanDeProcrastination@gmail.com", "codeDePorc", true, 4);
INSERT INTO EMPLOYE(idEmploye, nomE,prenomE,DateDebut,DateFin,mailE,passwordE,estAdmin,ligue) VALUES(3,"Araujo","Hugo", '2020-01-01', '2021-05-03',"fanDeJOJO@gmail.com", "beastStars", false, 4);
INSERT INTO EMPLOYE(idEmploye, nomE,prenomE,DateDebut,DateFin,mailE,passwordE,estAdmin,ligue) VALUES(4,"Thiriot","Romain", '2020-01-01', '2021-05-03',"fanDeKohLant.surivor@gmail.com", "beeeeeeee", false, 4);
INSERT INTO EMPLOYE(idEmploye, nomE,prenomE,DateDebut,DateFin,mailE,passwordE,estAdmin,ligue) VALUES(5,"Depardieu","Gerard", '2020-01-01', '2021-05-03',"fanDeeeeeeeeee@gmail.com", "coteDePorc", true, 6);
INSERT INTO EMPLOYE(idEmploye, nomE,prenomE,DateDebut,DateFin,mailE,passwordE,estAdmin,ligue) VALUES(6,"Hanouna","Cyril", '2020-01-01', '2021-05-03',"tuNePeuxPasEtreFierDeMeRencontrer@gmail.com", "meTapezPas", false, 6);
INSERT INTO EMPLOYE(idEmploye, nomE,prenomE,DateDebut,DateFin,mailE,passwordE,estAdmin,ligue) VALUES(7,"TheJava","ThePurificator", '2020-01-01', '2021-05-03',"fanDePurification@gmail.com", "codinGame", true, 5);
INSERT INTO EMPLOYE(idEmploye, nomE,prenomE,DateDebut,DateFin,mailE,passwordE,estAdmin,ligue) VALUES(8,"Vinciguerra","Michel(Version292484)", '2020-01-01', '2021-05-03',"actuellementLocaliseaAltair@gmail.com", "GREUT", true, 1);
INSERT INTO EMPLOYE(idEmploye, nomE,prenomE,DateDebut,DateFin,mailE,passwordE,estAdmin,ligue) VALUES(9,"Brunet","Didier", '2020-01-01', '2021-05-03',"faitesMoiDesPcQuiMarchent@gmail.com", "bloodyHell", true, 2);
INSERT INTO EMPLOYE(idEmploye, nomE,prenomE,DateDebut,DateFin,mailE,passwordE,estAdmin,ligue) VALUES(10,"Cheramnac","Alexis", '2020-01-01', '2021-05-03',"RocketLeague@gmail.com", "onTheFly", true, 3);