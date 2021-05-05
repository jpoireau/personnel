package jdbc;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import personnel.*;

public class JDBC implements Passerelle 
{
	Connection connection;

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	public JDBC()
	{
		try
		{
			Class.forName(ConnexionMySQL.getDriverClassName());
			connection = DriverManager.getConnection(ConnexionMySQL.getUrl(), ConnexionMySQL.getUser(), ConnexionMySQL.getPassword());
		}
		catch (ClassNotFoundException e)
		{
			System.out.println("Pilote JDBC non installé.");
		}
		catch (SQLException e)
		{
			System.out.println("Impossible de se connecter à la base de données");
			// System.out.println(e);
		}
	}
	
	@Override
	public GestionPersonnel getGestionPersonnel() throws SauvegardeImpossible, SQLException 
	{
		GestionPersonnel gestionPersonnel = new GestionPersonnel();
		try 
		{
			String requete = "select * from ligue";
			Statement instruction = connection.createStatement();
			ResultSet ligues = instruction.executeQuery(requete);
			while (ligues.next())
			{
				Ligue ligue = gestionPersonnel.addLigue(ligues.getInt(1), ligues.getString(2));
				requete = "Select * from employe where ligue = "+ ligue.getId();
				instruction = connection.createStatement();
				ResultSet employes = instruction.executeQuery(requete);
				while(employes.next()) {
					if(employes.getBoolean(8) == true){
						try {
							ligue.setAdministrateur(ligue.addEmploye(employes.getInt(1), employes.getString(2), employes.getString(3), employes.getString(6), LocalDate.parse( employes.getDate(4).toString()), LocalDate.parse(employes.getDate(5).toString()), employes.getString(7)));
						} catch (SauvegardeImpossible e) {
							e.printStackTrace();
						}}
						else {
						try {
							ligue.addEmploye(employes.getInt(1), employes.getString(2), employes.getString(3), employes.getString(6), LocalDate.parse( employes.getDate(4).toString()), LocalDate.parse(employes.getDate(5).toString()), employes.getString(7));
						} catch (SauvegardeImpossible e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		catch (SQLException e)
		{
			System.out.println(e);
		} catch (ErreurDateDepart e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ErreurDateFin e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return gestionPersonnel;
	}

	@Override
	public void sauvegarderGestionPersonnel(GestionPersonnel gestionPersonnel) throws SauvegardeImpossible 
	{
		close();
	}
	
	public void close() throws SauvegardeImpossible
	{
		try
		{
			if (connection != null)
				connection.close();
		}
		catch (SQLException e)
		{
			throw new SauvegardeImpossible(e);
		}
	}
	
	@Override
	public int insert(Ligue ligue) throws SauvegardeImpossible, SQLException 
	{
		PreparedStatement instruction;
		instruction = connection.prepareStatement("insert into ligue (nomLigue) values(?)", Statement.RETURN_GENERATED_KEYS);
		instruction.setString(1, ligue.getNom());		
		instruction.executeUpdate();
		ResultSet id = instruction.getGeneratedKeys();
		id.next();
		return id.getInt(1);
	}
	
	@Override
	public int insertEmploye(Employe employe) throws SauvegardeImpossible, SQLException 
	{
		PreparedStatement instruction;
		instruction = connection.prepareStatement("insert into employe(nomE,prenomE,DateDebut,DateFin,mailE,passwordE,ligue) values(?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
		instruction.setString(1, employe.getNom());
		instruction.setString(2, employe.getPrenom());
		instruction.setString(3, employe.getDateDebut().toString());
		instruction.setString(4, employe.getDateFin().toString());
		instruction.setString(5, employe.getMail());
		instruction.setString(6, employe.getPassword());
		instruction.setInt(7, employe.getLigue().getId());
		instruction.executeUpdate();
		ResultSet id = instruction.getGeneratedKeys();
		id.next();
		return id.getInt(1);
	}
	
	@Override
	public void updateEmploye(Employe employe) throws SauvegardeImpossible, SQLException 
	{
		PreparedStatement instruction;
		instruction = connection.prepareStatement("update employe set nomE = ? , prenomE = ? , DateDebut = ? , DateFin = ? , mailE= ? , passwordE= ? where idEmploye = ? ", Statement.RETURN_GENERATED_KEYS);
		instruction.setString(1, employe.getNom());
		instruction.setString(2, employe.getPrenom());
		instruction.setString(3, employe.getDateDebut().toString());
		instruction.setString(4, employe.getDateFin().toString());
		instruction.setString(5, employe.getMail());
		instruction.setString(6, employe.getPassword());
		instruction.setInt(7, employe.getId());
		instruction.executeUpdate();
	}
	
	@Override
	public void updateLigue(Ligue ligue) throws SauvegardeImpossible, SQLException 
	{
		PreparedStatement instruction;
		instruction = connection.prepareStatement("update ligue set nomLigue = ? where idLigue = ?", Statement.RETURN_GENERATED_KEYS);
		instruction.setString(1, ligue.getNom());
		instruction.setInt(2, ligue.getId());
		instruction.executeUpdate();
	}
	
	@Override
	public void deleteEmploye(Employe employe) throws SauvegardeImpossible, SQLException
	{
		PreparedStatement instruction;
		instruction = connection.prepareStatement("delete from employe where idEmploye = ?", Statement.RETURN_GENERATED_KEYS);
		instruction.setInt(1, employe.getId());
		instruction.executeUpdate();
	}
	
	@Override
	public void deleteLigue(Ligue ligue) throws SauvegardeImpossible, SQLException
	{
		if(ligue.getEmployes() != null && ligue.getEmployes().size() > 0)
		{
			PreparedStatement instruction;
			instruction = connection.prepareStatement("delete from employe where ligue = ?", Statement.RETURN_GENERATED_KEYS);
			instruction.setInt(1, ligue.getId());
			instruction.executeUpdate();
		}
		PreparedStatement instruction;
		instruction = connection.prepareStatement("delete from ligue where idLigue = ?", Statement.RETURN_GENERATED_KEYS);
		instruction.setInt(1, ligue.getId());
		instruction.executeUpdate();
	}
	
	@Override
	public void changerAdmin(Employe employe) throws SauvegardeImpossible, SQLException
	{
		String requete = "select idEmploye from employe where estAdmin = 1 and ligue = " + employe.getLigue().getId();
		Statement instruction = connection.createStatement();
		ResultSet admin = instruction.executeQuery(requete);
		if(admin.next())
		{
			PreparedStatement statement;
			statement = connection.prepareStatement("update employe set estAdmin = 0 where idEmploye = ? ", Statement.RETURN_GENERATED_KEYS);
			statement.setInt(1, admin.getInt(1));
			statement.executeUpdate();
		}
		PreparedStatement statement;
		statement = connection.prepareStatement("update employe set estAdmin = 1 where idEmploye = ? ", Statement.RETURN_GENERATED_KEYS);
		statement.setInt(1, employe.getId());
		statement.executeUpdate();
	}
}