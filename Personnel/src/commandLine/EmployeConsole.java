package commandLine;

import static commandLineMenus.rendering.examples.util.InOut.getString;

import java.time.LocalDate;

import commandLineMenus.ListOption;
import commandLineMenus.Menu;
import commandLineMenus.Option;
import personnel.Employe;

public class EmployeConsole 
{
	private Option afficher(final Employe employe)
	{
		return new Option("Afficher l'employé", "l", () -> {System.out.println(employe);});
	}

	ListOption<Employe> editerEmploye()
	{
		return (employe) -> editerEmploye(employe);		
	}

	Option editerEmploye(Employe employe)
	{
			Menu menu = new Menu("Gérer le compte " + employe.getNom(), "c");
			menu.add(afficher(employe));
			menu.add(changerNom(employe));
			menu.add(changerPrenom(employe));
			menu.add(changerMail(employe));
			menu.add(changerPassword(employe));
			menu.add(changeDateDebut(employe));
			menu.add(changeDateFin(employe));
			menu.addBack("q");
			return menu;
	}

	private Option changerNom(final Employe employe)
	{
		return new Option("Changer le nom", "n", 
				() -> {employe.setNom(getString("Nouveau nom : "));}
			);
	}
	
	private Option changerPrenom(final Employe employe)
	{
		return new Option("Changer le prénom", "p", () -> {employe.setPrenom(getString("Nouveau prénom : "));});
	}
	
	private Option changerMail(final Employe employe)
	{
		return new Option("Changer le mail", "e", () -> {employe.setMail(getString("Nouveau mail : "));});
	}
	
	private Option changerPassword(final Employe employe)
	{
		return new Option("Changer le password", "x", () -> {employe.setPassword(getString("Nouveau password : "));});
	}
	
	private Option changeDateFin(final Employe employe) {
		return new Option("Modifier la date de départ", "f", 
				() -> {
					try 
					{
						System.out.println("Ancienne date de départ : " + employe.getDateFin());
						employe.setDateFin((LocalDate)LocalDate.parse(getString("Entrez une nouvelle date de départ : ")));
						System.out.println("Nouvelle date enregistrée avec succès ! ");
					} catch (Exception e) 
					{
						System.out.println("Vous avez fait une erreur dans la saisie de la date, vérifiez le format (AAAA-MM-JJ) et réessayez !");
					}
					
					}
					
			); 
	} 

	private Option changeDateDebut(final Employe employe) {
		return new Option("Modifier la date d'arrivée", "d", 
				() -> {
					try 
					{
						System.out.println("Ancienne date d'arrivée : " + employe.getDateDebut());
						employe.setDateDebut((LocalDate)LocalDate.parse(getString("Entrez une nouvelle date d'arrivée :")));
						System.out.println("Nouvelle date enregistrée avec succès ! ");
					} catch (Exception e) 
					{
						System.out.println("Vous avez fait une erreur dans la saisie de la date, vérifiez le format (AAAA-MM-JJ) et réessayez !");
					}
					}
			);
	}
}
