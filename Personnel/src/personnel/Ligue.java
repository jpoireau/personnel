package personnel;

import java.io.Serializable;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Représente une ligue. Chaque ligue est reliée à une liste
 * d'employés dont un administrateur. Comme il n'est pas possible
 * de créer un employé sans l'affecter à une ligue, le root est 
 * l'administrateur de la ligue jusqu'à ce qu'un administrateur 
 * lui ait été affecté avec la fonction 
 */

public class Ligue implements Serializable, Comparable<Ligue>
{
	private static final long serialVersionUID = 1L;
	private int id = -1;
	private String nom;
	private SortedSet<Employe> employes;
	private Employe administrateur;
	private GestionPersonnel gestionPersonnel;
	
	/**
	 * Crée une ligue.
	 * @param nom le nom de la ligue.
	 * @throws SQLException 
	 */
	
	Ligue(GestionPersonnel gestionPersonnel, String nom) throws SauvegardeImpossible, SQLException
	{
		this(gestionPersonnel, -1, nom);
		this.id = gestionPersonnel.insert(this); 
	}

	Ligue(GestionPersonnel gestionPersonnel, int id, String nom)
	{
		this.nom = nom;
		employes = new TreeSet<>();
		this.gestionPersonnel = gestionPersonnel;
		administrateur = gestionPersonnel.getRoot();
		this.id = id;
	}

	/**
	 * Retourne le nom de la ligue.
	 * @return le nom de la ligue.
	 */

	public String getNom()
	{
		return nom;
	}

	/**
	 * Change le nom.
	 * @param nom le nouveau nom de la ligue.
	 * @throws SQLException 
	 */

	public void setNom(String nom) throws SauvegardeImpossible, SQLException
	{
		this.nom = nom;
		gestionPersonnel.updateLigue(this);
	}

	/**
	 * Retourne l'administrateur de la ligue.
	 * @return l'administrateur de la ligue.
	 */
	
	public Employe getAdministrateur()
	{
		return administrateur;
	}

	/**
	 * Fait de administrateur l'administrateur de la ligue.
	 * Lève DroitsInsuffisants si l'administrateur n'est pas 
	 * un employé de la ligue ou le root. Révoque les droits de l'ancien 
	 * administrateur.
	 * @param administrateur le nouvel administrateur de la ligue.
	 * @throws SQLException 
	 * @throws SauvegardeImpossible 
	 */
	
	public void setAdministrateur(Employe administrateur) throws SauvegardeImpossible, SQLException
	{
		Employe root = GestionPersonnel.getGestionPersonnel().getRoot();
		gestionPersonnel.changerAdmin(administrateur);
		if (administrateur != root && administrateur.getLigue() != this)
			throw new DroitsInsuffisants();
		this.administrateur = administrateur;
	}

	public void setRoot(Employe administrateur) throws SauvegardeImpossible, SQLException
	{
		Employe root = GestionPersonnel.getGestionPersonnel().getRoot();
		gestionPersonnel.updateEmploye(root);
	}
	
	/**
	 * Retourne les employés de la ligue.
	 * @return les employés de la ligue dans l'ordre alphabétique.
	 */
	
	public SortedSet<Employe> getEmployes()
	{
		return Collections.unmodifiableSortedSet(employes);
	}

	/**
	 * Ajoute un employé dans la ligue. Cette méthode 
	 * est le seul moyen de créer un employé.
	 * @param nom le nom de l'employé.
	 * @param prenom le prénom de l'employé.
	 * @param mail l'adresse mail de l'employé.
	 * @param password le password de l'employé.
	 * @param dateFin 
	 * @param dateDebut 
	 * @return l'employé créé. 
	 * @throws ErreurDateDepart 
	 * @throws ErreurDateFin 
	 * @throws SauvegardeImpossible 
	 * @throws SQLException 
	 */

	public Employe addEmploye(String nom, String prenom, String mail, LocalDate dateDebut, LocalDate dateFin, String password) throws ErreurDateDepart, ErreurDateFin, SauvegardeImpossible, SQLException
	{
		Employe employe = new Employe(this.gestionPersonnel, this, nom, prenom, mail, password, dateDebut,dateFin);
			employe.setDateDebut(dateDebut);
			employe.setDateFin(dateFin);
		employes.add(employe);
		return employe;
	}
	
	void remove(Employe employe)
	{
		employes.remove(employe);
	}
	
	/**
	 * Supprime la ligue, entraîne la suppression de tous les employés
	 * de la ligue.
	 * @throws SauvegardeImpossible 
	 * @throws SQLException 
	 */
	
	public void remove() throws SauvegardeImpossible, SQLException
	{
		GestionPersonnel.getGestionPersonnel().remove(this);
		gestionPersonnel.deleteLigue(this);
	}
	

	@Override
	public int compareTo(Ligue autre)
	{
		return getNom().compareTo(autre.getNom());
	}
	
	@Override
	public String toString()
	{
		return nom;
	}

	public Employe addEmploye(int id, String nom, String prenom, String mail, LocalDate dateDebut, LocalDate dateFin,String password) throws ErreurDateDepart, ErreurDateFin, SauvegardeImpossible, SQLException
	{
		Employe employe = new Employe(this.gestionPersonnel, this, id, nom, prenom, mail,password, dateDebut,dateFin);
		employe.setDateDebut(dateDebut);
		employe.setDateFin(dateFin);
		employe.setId(id);
		employes.add(employe);
		return employe;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
}