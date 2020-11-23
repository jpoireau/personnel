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
		return new Option("Afficher l'employ�", "l", () -> {System.out.println(employe);});
	}

	ListOption<Employe> editerEmploye()
	{
		return (employe) -> editerEmploye(employe);		
	}

	Option editerEmploye(Employe employe)
	{
			Menu menu = new Menu("G�rer le compte " + employe.getNom(), "c");
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
		return new Option("Changer le pr�nom", "p", () -> {employe.setPrenom(getString("Nouveau pr�nom : "));});
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
		return new Option("Modifier la date de d�part", "f", 
				() -> {
					try 
					{
						System.out.println("Ancienne date de d�part : " + employe.getDateFin());
						employe.setDateFin((LocalDate)LocalDate.parse(getString("Entrez une nouvelle date de d�part : ")));
						System.out.println("Nouvelle date enregistr�e avec succ�s ! ");
					} catch (Exception e) 
					{
						System.out.println("Vous avez fait une erreur dans la saisie de la date, v�rifiez le format (AAAA-MM-JJ) et r�essayez !");
					}
					
					}
					
			); 
	} 

	private Option changeDateDebut(final Employe employe) {
		return new Option("Modifier la date d'arriv�e", "d", 
				() -> {
					try 
					{
						System.out.println("Ancienne date d'arriv�e : " + employe.getDateDebut());
						employe.setDateDebut((LocalDate)LocalDate.parse(getString("Entrez une nouvelle date d'arriv�e :")));
						System.out.println("Nouvelle date enregistr�e avec succ�s ! ");
					} catch (Exception e) 
					{
						System.out.println("Vous avez fait une erreur dans la saisie de la date, v�rifiez le format (AAAA-MM-JJ) et r�essayez !");
					}
					}
			);
	}
}
