package personnel;

import java.io.Serializable;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 * Employé d'une ligue hébergée par la M2L. Certains peuvent 
 * être administrateurs des employés de leur ligue.
 * Un seul employé, rattaché à aucune ligue, est le root.
 * Il est impossible d'instancier directement un employé, 
 * il faut passer la méthode 
 */

public class Employe implements Serializable, Comparable<Employe>
{
	private static final long serialVersionUID = 4795721718037994734L;
	private int id;
	private String nom, prenom, password, mail;
	private LocalDate dateDebut, dateFin;
	private Ligue ligue;
	private GestionPersonnel gestionPersonnel;
	
	public Employe(GestionPersonnel gestionPersonnel, Ligue ligue, String nom, String prenom, String mail, String password , LocalDate dateDebut, LocalDate dateFin ) throws SauvegardeImpossible, SQLException
	{
		this.gestionPersonnel = gestionPersonnel;
		this.nom = nom;
		this.prenom = prenom;
		this.password = password;
		this.mail = mail;
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
		this.ligue = ligue;
		if(dateDebut != null && dateFin !=null && dateDebut.isBefore(dateFin))
			this.id = gestionPersonnel.insertEmploye(this);//gestion erreur date
	}
	
	Employe(GestionPersonnel gestionPersonnel, Ligue ligue,int id, String nom, String prenom, String mail, String password, LocalDate dateDebut, LocalDate dateFin)
	{
		this.gestionPersonnel = gestionPersonnel;
		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
		this.password = password;
		this.mail = mail;
		this.ligue = ligue;
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
	}
	
	/**
	 * Retourne vrai ssi l'employé est administrateur de la ligue 
	 * passée en paramètre.
	 * @return vrai ssi l'employé est administrateur de la ligue 
	 * passée en paramètre.
	 * @param ligue la ligue pour laquelle on souhaite vérifier si this 
	 * est l'admininstrateur.
	 */
	
	public boolean estAdmin(Ligue ligue)
	{
		return ligue.getAdministrateur() == this;
	}
	
	/**
	 * Retourne vrai ssi l'employé est le root.
	 * @return vrai ssi l'employé est le root.
	 * @throws SauvegardeImpossible 
	 * @throws SQLException 
	 */
	
	public boolean estRoot() throws SauvegardeImpossible, SQLException
	{
		return GestionPersonnel.getGestionPersonnel().getRoot() == this;
	}
	
	/**
	 * Retourne le nom de l'employé.
	 * @return le nom de l'employé. 
	 */
	
	public String getNom()
	{
		return nom;
	}

	/**
	 * Change le nom de l'employé.
	 * @param nom le nouveau nom.
	 * @throws SauvegardeImpossible 
	 * @throws SQLException 
	 */
	
	public void setNom(String nom) throws SauvegardeImpossible, SQLException
	{
		this.nom = nom;
		gestionPersonnel.updateEmploye(this);
	}

	/**
	 * Retourne le prénom de l'employé.
	 * @return le prénom de l'employé.
	 */
	
	public String getPrenom()
	{
		return prenom;

	}
	
	/**
	 * Change le prénom de l'employé.
	 * @param prenom le nouveau prénom de l'employé. 
	 * @throws SauvegardeImpossible 
	 * @throws SQLException 
	 */

	public void setPrenom(String prenom) throws SauvegardeImpossible, SQLException
	{
		this.prenom = prenom;
		gestionPersonnel.updateEmploye(this);
	}

	/**
	 * Retourne le mail de l'employé.
	 * @return le mail de l'employé.
	 */
	
	public String getMail()
	{
		return mail;
	}
	
	/**
	 * Change le mail de l'employé.
	 * @param mail le nouveau mail de l'employé.
	 * @throws SauvegardeImpossible 
	 * @throws SQLException 
	 */

	public void setMail(String mail) throws SauvegardeImpossible, SQLException
	{
		this.mail = mail;
		gestionPersonnel.updateEmploye(this);
	}
	
	public String getPassword() {
		return password;
	}

	/**
	 * Retourne vrai ssi le password passé en paramètre est bien celui
	 * de l'employé.
	 * @return vrai ssi le password passé en paramètre est bien celui
	 * de l'employé.
	 * @param password le password auquel comparer celui de l'employé.
	 */
	
	public LocalDate getDateDebut() {
		return dateDebut;
	}

	public void setDateDebut(LocalDate datedebut) throws ErreurDateDepart, SauvegardeImpossible, SQLException {
		if(dateFin == null)
			this.dateDebut = datedebut;
		else if(datedebut.isAfter(dateFin))
			throw new ErreurDateDepart(datedebut, dateFin);
		else {
			this.dateDebut = datedebut;
			gestionPersonnel.updateEmploye(this);
		}
			
	}

	public LocalDate getDateFin() {
		return dateFin;
	}

	public void setDateFin(LocalDate datefin) throws ErreurDateFin, SauvegardeImpossible, SQLException {
		if(dateDebut ==null )
			this.dateFin = datefin;
		else if(datefin.isBefore(dateDebut))
			throw new ErreurDateFin(datefin, dateDebut);
		else {
			this.dateFin = datefin;
			gestionPersonnel.updateEmploye(this);
		}
	}
	
	public boolean checkPassword(String password)
	{
		return this.password.equals(password);
	}
	
	/**
	 * Change le password de l'employé.
	 * @param password le nouveau password de l'employé. 
	 * @throws SauvegardeImpossible 
	 * @throws SQLException 
	 */
	
	public void setPassword(String password) throws SauvegardeImpossible, SQLException
	{
		this.password= password;
		gestionPersonnel.updateEmploye(this);
	}

	/**
	 * Retourne la ligue à laquelle l'employé est affecté.
	 * @return la ligue à laquelle l'employé est affecté.
	 */
	
	public Ligue getLigue()
	{
		return ligue;
	}

	/**
	 * Supprime l'employé. Si celui-ci est un administrateur, le root
	 * récupère les droits d'administration sur sa ligue.
	 * @throws SauvegardeImpossible 
	 * @throws SQLException 
	 */
	
	public void remove() throws SauvegardeImpossible, SQLException
	{
		Employe root = GestionPersonnel.getGestionPersonnel().getRoot();
		if (this != root)
		{
			gestionPersonnel.deleteEmploye(this);
			if (estAdmin(getLigue()))
				getLigue().setAdministrateur(root);
			ligue.remove(this);
		}
		else
			throw new ImpossibleDeSupprimerRoot();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public int compareTo(Employe autre)
	{
		int cmp = getNom().compareTo(autre.getNom());
		if (cmp != 0)
			return cmp;
		return getPrenom().compareTo(autre.getPrenom());
	}
	
	@Override
	public String toString()
	{
		String res = nom + " " + prenom + " " + mail + " " + dateDebut + " " + dateFin + " " + password + " (";
		try {
			if (estRoot())
				res += "super-utilisateur";
			else
				res += ligue.toString();
		} catch (SauvegardeImpossible e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res + ")";
	}

}