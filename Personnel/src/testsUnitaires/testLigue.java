package testsUnitaires;

import static commandLineMenus.rendering.examples.util.InOut.getString;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

import jdbc.JDBC;
import personnel.*;

class testLigue 
{
	GestionPersonnel gestionPersonnel = GestionPersonnel.getGestionPersonnel();
	JDBC jdbc = new JDBC();
	
	@Test
	void createLigue() throws SauvegardeImpossible, SQLException
	{
		Statement instruction = jdbc.getConnection().createStatement();
		Ligue ligue;
		ligue = gestionPersonnel.addLigue("Fléchettes");
		String requete = "Select nomLigue from ligue where idLigue = "+ ligue.getId();
		ResultSet req = instruction.executeQuery(requete);
		while (req.next()) {
			assertEquals(req.getString(1), ligue.getNom());
		}
	}
	
	@Test
	void delLigue() throws SauvegardeImpossible, SQLException
	{
		Statement instruction = jdbc.getConnection().createStatement();
		Ligue ligue = gestionPersonnel.addLigue("testultime");
		ligue.remove();
		String requete = "Select nomLigue from ligue where idLigue ="+ ligue.getId();
		ResultSet req = instruction.executeQuery(requete);
		while (req.next()) {
		assertEquals(req.getString(1),null);
	} 
	}
	
	@Test
	void modifLigue() throws SauvegardeImpossible, SQLException 
	{
		Statement instruction = jdbc.getConnection().createStatement();
		Ligue ligue = gestionPersonnel.addLigue("Fléchettes");
		ligue.setNom("Flech");
		String requete = "Select nomLigue from ligue where idLigue = "+ ligue.getId();
		ResultSet req = instruction.executeQuery(requete);
		while (req.next()) {
		assertEquals("Flech", ligue.getNom()); }
	}

	@Test
	void addEmploye() throws SauvegardeImpossible, ErreurDateDepart, ErreurDateFin, SQLException
	{
		String result = null;
		Statement instruction = jdbc.getConnection().createStatement();
		Ligue ligue = gestionPersonnel.addLigue("Fléchettes");
		Employe employe = ligue.addEmploye("Bouchard", "Gérard", "g.bouchard@gmail.com", LocalDate.parse("2020-09-09"), LocalDate.parse("2030-06-06"), "azerty"); 
		String Employer = employe.toString();
		String requete = "Select nomE, prenomE, mailE, DateDebut, DateFin, passwordE, nomLigue from employe, ligue where ligue = idLigue and ligue = "+ ligue.getId();
		ResultSet req = instruction.executeQuery(requete);
		while (req.next()) {
			result = req.getString(1) + " " + req.getString(2) + " " + req.getString(3) + " " + req.getString(4) + " " + req.getString(5) + " " + req.getString(6) + " " + "(" + req.getString(7) + ")";
		}
		assertEquals(result, Employer);
	}
	
	@Test
	void delEmploye() throws SauvegardeImpossible, ErreurDateDepart, ErreurDateFin, SQLException 
	{
		Statement instruction = jdbc.getConnection().createStatement();
		Ligue ligue = gestionPersonnel.addLigue("Fléchettes");
		Employe employe = ligue.addEmploye("Bouchard", "Gérard", "g.bouchard@gmail.com", LocalDate.parse("2020-01-08"), LocalDate.parse("2021-06-06"), "azerty"); 
		employe.remove();
		String requete = "Select idEmploye from employe where ligue = "+ ligue.getId();
		ResultSet req = instruction.executeQuery(requete);
		while (req.next()) {
			assertEquals(req.getString(1),null);
		}
	}
	
	@Test
	void modifEmploye() throws SauvegardeImpossible, ErreurDateDepart, ErreurDateFin, SQLException 
	{
		String result = null;
		Statement instruction = jdbc.getConnection().createStatement();
		Ligue ligue = gestionPersonnel.addLigue("Fléchettes");
		Employe employe = ligue.addEmploye("Bouchard", "Gérard", "g.bouchard@gmail.com", LocalDate.parse("2010-09-09"), LocalDate.parse("2020-10-01"), "azerty"); 
		employe.setNom("Bouchard2");
		employe.setPrenom("Gérard2");
		employe.setMail("richard@35.com");
		employe.setDateDebut(LocalDate.parse("2002-02-02"));
		employe.setPassword("qwerty");
		String Employer = employe.toString();
		String requete = "Select nomE, prenomE, mailE, DateDebut, DateFin, passwordE, nomLigue from employe, ligue where employe.ligue = ligue.idLigue and ligue = "+ ligue.getId();
		ResultSet req = instruction.executeQuery(requete);
		while (req.next()) {
			result = req.getString(1) + " " + req.getString(2) + " " + req.getString(3) + " " + req.getString(4) + " " + req.getString(5) + " " + req.getString(6) + " " + "(" + req.getString(7) + ")";
		}
		assertEquals(result, Employer);
	}
	
	@Test
	void changeAdmin() throws SauvegardeImpossible, ErreurDateDepart, ErreurDateFin, SQLException
	{
		Ligue ligue = gestionPersonnel.addLigue("Flechettes");
		Employe employe = ligue.addEmploye("Bouchard", "Gérard", "g.bouchard@gmail.com",LocalDate.parse("2010-09-09"), LocalDate.parse("2020-10-01"), "azerty");
		ligue.setAdministrateur(employe);
		String result = null;
		Statement instruction = jdbc.getConnection().createStatement();
		String requete = "Select * from employe, ligue where estAdmin = 1 and ligue = idLigue and ligue = " + employe.getLigue().getId();
		ResultSet res = instruction.executeQuery(requete);
		while(res.next())
			result = res.getString(2) + " " + res.getString(3) + " " + res.getString(6) + " " + res.getString(4) + " " + res.getString(5) + " " + res.getString(7) + " " + "(" + res.getString(11) + ")";
		assertEquals(result,ligue.getAdministrateur().toString());
	}


}