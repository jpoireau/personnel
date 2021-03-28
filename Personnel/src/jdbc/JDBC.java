package jdbc;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import personnel.*;

public class JDBC implements Passerelle 
{
	Connection connection;

	public JDBC()
	{
		try
		{
			Class.forName(CredentialsExample.getDriverClassName());
			connection = DriverManager.getConnection(CredentialsExample.getUrl(), CredentialsExample.getUser(), CredentialsExample.getPassword());
		}
		catch (ClassNotFoundException e)
		{
			System.out.println("Pilote JDBC non installé.");
		}
		catch (SQLException e)
		{
			System.out.println(e);
		}
	}
	
	@Override
	public GestionPersonnel getGestionPersonnel() 
	{
		GestionPersonnel gestionPersonnel = new GestionPersonnel();
		try 
		{
			String requete = "select * from ligue";
			Statement instruction = connection.createStatement();
			ResultSet ligues = instruction.executeQuery(requete);
			while (ligues.next())
				gestionPersonnel.addLigue(ligues.getInt(1), ligues.getString(2));
		}
		catch (SQLException e)
		{
			System.out.println(e);
		}
		
		return gestionPersonnel;
	}
	
	@Override
	public int insertEmploye(Employe employe) throws SauvegardeImpossible, SQLException 
	{
		PreparedStatement instruction;
		instruction = connection.prepareStatement("insert into employe(nom,prenom,mail,password,dateDebut,dateFin,ligueID) values(?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
		instruction.setString(1, employe.getNom());
		instruction.setString(2, employe.getPrenom());
		instruction.setString(5, employe.getMail());
		instruction.setString(6, employe.getPassword());
		instruction.setString(3, employe.getDateDebut().toString());
		instruction.setString(4, employe.getDateFin().toString());
		instruction.setInt(7, employe.getLigue().getId());
		instruction.executeUpdate();
		ResultSet id = instruction.getGeneratedKeys();
		id.next();
		return id.getInt(1);
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
	public int insert(Ligue ligue) throws SauvegardeImpossible 
	{
		try 
		{
			PreparedStatement instruction;
			instruction = connection.prepareStatement("insert into ligue (nomLigue) values(?)", Statement.RETURN_GENERATED_KEYS);
			instruction.setString(1, ligue.getNom());		
			instruction.executeUpdate();
			ResultSet id = instruction.getGeneratedKeys();
			id.next();
			return id.getInt(1);
		} 
		catch (SQLException exception) 
		{
			exception.printStackTrace();
			throw new SauvegardeImpossible(exception);
		}		
	}

	@Override
	public int deleteLigue(Ligue ligue) throws SauvegardeImpossible, SQLException 
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
		return -1;
	}
	
	
	
	@Override
	public int modifLigue(Ligue ligue) throws SauvegardeImpossible, SQLException 
	{
		PreparedStatement instruction;
		instruction = connection.prepareStatement("update ligue set nomLigue = ? where idLigue = ?", Statement.RETURN_GENERATED_KEYS);
		instruction.setString(1, ligue.getNom());
		instruction.setInt(2, ligue.getId());
		instruction.executeUpdate();
		return -1;	
	}
}
