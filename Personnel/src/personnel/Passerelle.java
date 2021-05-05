package personnel;

import java.sql.SQLException;

public interface Passerelle 
{
	public GestionPersonnel getGestionPersonnel() throws SauvegardeImpossible, SQLException;
	public void sauvegarderGestionPersonnel(GestionPersonnel gestionPersonnel)  throws SauvegardeImpossible;
	public int insert(Ligue ligue) throws SauvegardeImpossible, SQLException;
	public void updateLigue(Ligue ligue) throws SauvegardeImpossible, SQLException;
	public int insertEmploye(Employe employe) throws SauvegardeImpossible, SQLException;
	public void updateEmploye(Employe employe) throws SauvegardeImpossible, SQLException;
	public void changerAdmin(Employe employe) throws SauvegardeImpossible, SQLException;
	public void deleteLigue(Ligue ligue) throws SauvegardeImpossible, SQLException;
	public void deleteEmploye(Employe employe) throws SauvegardeImpossible, SQLException;
}