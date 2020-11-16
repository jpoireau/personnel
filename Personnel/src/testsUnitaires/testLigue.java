package testsUnitaires;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Before;
import org.junit.jupiter.api.Test;

import personnel.*;

class testLigue 
{
	GestionPersonnel gestionPersonnel = GestionPersonnel.getGestionPersonnel();
	
	@Test
	void createLigue() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue("Fléchettes");
		assertEquals("Fléchettes", ligue.getNom());
		
	}

	
	@Test
	void addLigue() throws SauvegardeImpossible 
	{
		Ligue ligue = gestionPersonnel.addLigue("Vicoudou");
		assertEquals("Vicoudou", ligue.getNom());
		assertTrue(gestionPersonnel.getLigues().contains(ligue));
		
	}
	
	@Test
	void getLigue() throws SauvegardeImpossible 
	{
		Ligue ligue = gestionPersonnel.addLigue("Football");
		Employe admin = ligue.addEmploye("Admin","Gertrude","A.Gertrude@gmail.com","pass",null, null);
		ligue.setAdministrateur(admin);
		
		Employe employe = ligue.addEmploye("Bouchard", "Gérard", "g.bouchard@gmail.com", "pass", "", "");
		
		Ligue ligueAdmin = gestionPersonnel.getLigue(admin);
		assertNotNull(ligueAdmin);
		assertEquals(ligue, ligueAdmin);
		
		ligueAdmin = gestionPersonnel.getLigue(employe);
		assertNull(ligueAdmin);
	}
/*	
	@Test
	void removeLigues() throws SauvegardeImpossible 
	{
		Ligue ligue = gestionPersonnel.addLigue("Football");
		assertEquals("Football", ligue.getNom());
		ligue.remove();
		assertTrue(gestionPersonnel.getLigues().isEmpty());
	}
*/	
	@Test
	void setAdmin() throws SauvegardeImpossible 
	{
		Ligue ligue = gestionPersonnel.addLigue("Football");
		Ligue ligue1 = gestionPersonnel.addLigue("Fléchettes");
		Employe employe = ligue.addEmploye("Bouchard", "Gérard", "g.bouchard@gmail.com", "pass", "", "");
		assertThrows(DroitsInsuffisants.class, () -> {
			ligue1.setAdministrateur(employe);
		});
	}
	
	
	
	@Test
	void addEmploye() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue("Fléchettes");
		Employe employe = ligue.addEmploye("Bouchard", "Gérard", "g.bouchard@gmail.com", "pass", "", ""); 
		assertEquals(employe, ligue.getEmployes().first()); 
		Ligue ligue1 = gestionPersonnel.addLigue("SERPENTAR");
		Employe employe1 = ligue1.addEmploye("arun", "gangsta", "arun.gangsta@gmail.com", "pass", "", "");
		assertEquals(employe1, ligue1.getEmployes().first());
	}
	
	@Test
	void removeEmployeRoot() throws SauvegardeImpossible 
	{
		Employe root = gestionPersonnel.getRoot();
		assertThrows(ImpossibleDeSupprimerRoot.class, () -> {
			root.remove();
		});
	}
	
	@Test
	void removeEmploye() throws SauvegardeImpossible 
	{
		Ligue ligue = gestionPersonnel.addLigue(1, "Football");
		Employe employe = ligue.addEmploye("Bouchard", "Gérard", "g.bouchard@gmail.com", "pass", "", "");
		assertTrue(ligue.getEmployes().contains(employe));
		ligue.setAdministrateur(employe);
		employe.remove();
		assertFalse(ligue.getEmployes().contains(employe));
		assertEquals(this.gestionPersonnel.getRoot(), ligue.getAdministrateur());
		
	}
	
	@Test
	void checkPassword() throws SauvegardeImpossible 
	{
		Ligue ligue = gestionPersonnel.addLigue(1, "Football");
		Employe employe = ligue.addEmploye("Bouchard", "Gérard", "g.bouchard@gmail.com", "pass", "", "");
		assertTrue(employe.checkPassword("pass"));
		assertFalse(employe.checkPassword("autrePass"));
	}
	
	@Test
	void estAdmin() throws SauvegardeImpossible 
	{
		Ligue ligue = gestionPersonnel.addLigue(1, "Football");
		Employe employe = ligue.addEmploye("Bouchard", "Gérard", "g.bouchard@gmail.com", "pass", "", "");
		assertFalse(employe.estAdmin(ligue));
		
		ligue.setAdministrateur(employe);
		assertTrue(employe.estAdmin(ligue));
		
		Ligue ligue1 = gestionPersonnel.addLigue(1, "Fléchette");
		assertFalse(employe.estAdmin(ligue1));
		
	}
	
	
}
