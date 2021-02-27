package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import personnel.GestionPersonnel;
import personnel.Ligue;
import personnel.Passerelle;
import personnel.SauvegardeImpossible;

public class BDD implements Passerelle {

	@Override
	public GestionPersonnel getGestionPersonnel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void sauvegarderGestionPersonnel(GestionPersonnel gestionPersonnel) throws SauvegardeImpossible {
		// TODO Auto-generated method stub

	}

	@Override
	public int insert(Ligue ligue) throws SauvegardeImpossible {
		// TODO Auto-generated method stub
		return 0;
	}
	public static void main(String[] args)
	 {
	  Connection c = null;
	  try
	  {
	   Class.forName("com.mysql.cj.jdbc.Driver");
	   String url = "jdbc:mysql://localhost/personnel?zeroDateTimeBehavior=CONVERT_TO_NULL&serverTimezone=UTC", user = "root", password = "";
	   c = DriverManager.getConnection(url, user, password);
	   String req = "select* from ligue;";
	   
	   Statement s = c.createStatement();
	   
	   // s.execute(sql: " insert into classe(id_classe, nom_classe) values (7,'Licence')"); // exemple pour insérer des nouvelles valeurs dans une table 
	   ResultSet rs = s.executeQuery(req); 	// Pour lire/récupérer qqchose d'une base de donnée
	   
	   while (rs.next()) 					// la requete peut avoir plusieurs résultats donc pour chaque itération il va afficher le résultat
	   {
	    System.out.println(rs.getInt(1) + " : " + rs.getString(2)); // On récupère l'id de la clonne 1 et le nom de la colonne 2
	    
	    /* 
	    
	    System.out.println("id : "+ rs.getInt(req));
	      
	      
	    */
		
	  }
	   
	  }
	  catch (ClassNotFoundException e)
	  {
	   System.out.println("Pilote JDBC non installé.");
	  }
	  catch (SQLException e)
	  {
	   System.out.println(e);
	  }
	  finally
	  {
	   try
	   {
	    if (c != null)
	     c.close();
	   }
	   catch (SQLException e)
	   {
	    System.out.println("Impossible de fermer la connection.");
	   }
	  }
	 }

}
