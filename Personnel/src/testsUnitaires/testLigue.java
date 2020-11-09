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
	void addEmploye() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue("Fléchettes");
		Employe employe = ligue.addEmploye("Bouchard", "Gérard", "g.bouchard@gmail.com", "azerty", "vd^fokvfod", "vkfdof"); 
		assertEquals(employe, ligue.getEmployes().first()); 
		Ligue ligue1 = gestionPersonnel.addLigue("SERPENTAR");
		Employe employe1 = ligue1.addEmploye("arun", "gangsta", "arun.gangsta@gmail.com", "lpb", "dvfvf", "vf;ldv");
		assertEquals(employe1, ligue1.getEmployes().first());
	}
	@Test
	void addLigue() throws SauvegardeImpossible 
	{
		Ligue ligue2 = gestionPersonnel.addLigue("vipcjoc");
		assertEquals("vipcjoc", ligue2.getNom());
	}
	
	
}
