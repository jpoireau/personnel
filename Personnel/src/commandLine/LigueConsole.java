package commandLine;

import static commandLineMenus.rendering.examples.util.InOut.getString;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import commandLineMenus.List;
import commandLineMenus.Menu;
import commandLineMenus.Option;
import personnel.*;

public class LigueConsole 
{
	private GestionPersonnel gestionPersonnel;
	private EmployeConsole employeConsole;

	public LigueConsole(GestionPersonnel gestionPersonnel, EmployeConsole employeConsole)
	{
		this.gestionPersonnel = gestionPersonnel;
		this.employeConsole = employeConsole;
	}

	Menu menuLigues()
	{
		Menu menu = new Menu("G�rer les ligues", "l");
		menu.add(afficherLigues());
		menu.add(ajouterLigue());
		menu.add(selectionnerLigue());
		menu.addBack("q");
		return menu;
	}

	private Option afficherLigues()
	{
		return new Option("Afficher les ligues", "l", () -> {System.out.println(gestionPersonnel.getLigues());});
	}

	private Option afficher(final Ligue ligue)
	{
		return new Option("Afficher la ligue", "w", 
				() -> 
				{
					System.out.println(ligue);
					System.out.println("administr�e par " + ligue.getAdministrateur());
				}
		);
	}
	private Option afficherEmployes(final Ligue ligue)
	{
		return new Option("Afficher les employes", "m", () -> {System.out.println(ligue.getEmployes());});
	}

	private Option ajouterLigue()
	{
		return new Option("Ajouter une ligue", "a", () -> 
		{
			try
			{
				gestionPersonnel.addLigue(getString("nom : "));
			}
			catch(SauvegardeImpossible exception)
			{
				System.err.println("Impossible de sauvegarder cette ligue");
			}
		});
	}
	
	private Menu editerLigue(Ligue ligue) throws SQLException
	{
		Menu menu = new Menu("Editer " + ligue.getNom());
		menu.add(afficher(ligue));
		menu.add(gererEmployes(ligue));
		menu.add(changerAdministrateur(ligue));
		menu.add(changerNom(ligue));
		try {
			menu.add(supprimer(ligue));
		} catch (SauvegardeImpossible e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		menu.addBack("q");
		return menu;
	}

	private Option changerNom(final Ligue ligue) throws SQLException
	{
		return new Option("Renommer", "r", 
				() -> {try 
				{
					ligue.setNom(getString("Nouveau nom ligue : "));
				} 
				catch (SauvegardeImpossible e) 
				{
					e.printStackTrace();
				}});
	}
	
	private List<Ligue> selectionnerLigue()
	{
		return new List<Ligue>("S�lectionner une ligue", "e", 
				() -> new ArrayList<>(gestionPersonnel.getLigues()),
				(element) -> {
					try {
						return editerLigue(element);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return null;
				}
				);
	}
	
	private Option ajouterEmploye(final Ligue ligue)
	{
		return new Option("ajouter un employ�", "a",
				() -> 
				{
					try 
					{
						ligue.addEmploye(getString("nom : "), 
								getString("prenom : "), getString("mail : "), 
								getString("password : "),
								LocalDate.parse(getString("datedebut (format (AAAA-MM-JJ) : ")),
								LocalDate.parse(getString("datefin (format (AAAA-MM-JJ): ")));
					}
					catch(Exception e) 
					{
						System.out.println("Il y a une erreur dans la saisie de la date");
					}
					finally {
						System.out.println("OK");
					}
				}
		);
	}
	
	private Menu gererEmployes(Ligue ligue)
	{
		Menu menu = new Menu("G�rer les employ�s de " + ligue.getNom(), "e");
		menu.add(afficherEmployes(ligue));
		menu.add(changerAdministrateur(ligue));
		menu.add(ajouterEmploye(ligue));
		menu.add(selectionnerEmploye(ligue));
		//menu.add(modifierEmploye(ligue));
		//menu.add(supprimerEmploye(ligue));
		menu.addBack("q");
		return menu;
	}

	private List<Employe> selectionnerEmploye(Ligue ligue)
	{
		return new List<>("S�lectionner un employe", "s", 
				() -> new ArrayList<>(ligue.getEmployes()),
				(element) -> modifierEmploye(element)
				);
	}
	
	private Menu modifierEmploye(Employe employe){
		Menu menu = new Menu ("�diter l'employ� : " + employe.getNom() + " " + employe.getPrenom());
		menu.add(employeConsole.editerEmploye(employe));
		menu.add(supprimerEmploye(employe));
		menu.addBack("q");
		return menu;
	}
	
	private Option supprimerEmploye(final Employe employe)
	{
		return new Option("Supprimer l'empoy�" + " " + employe.getNom() + " " + employe.getPrenom(), "s",
				() -> {employe.remove()
					;});
		
	}
	
	private List<Employe> changerAdministrateur(final Ligue ligue)
	{
		return new List<>("Changer d'administrateur", "h", 
				() -> new ArrayList<>(ligue.getEmployes()), 
				(index, element) -> {ligue.setAdministrateur(element);}
				);
	}		

	private Option supprimer(Ligue ligue) throws SauvegardeImpossible, SQLException
	{
		return new Option("Supprimer", "d", () -> {try {
			ligue.remove();
		} catch (SauvegardeImpossible e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}});
	}
	
}
