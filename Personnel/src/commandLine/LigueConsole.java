package commandLine;

import static commandLineMenus.rendering.examples.util.InOut.getString;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import commandLineMenus.List;
import commandLineMenus.Menu;
import commandLineMenus.Option;
import personnel.Employe;
import personnel.GestionPersonnel;
import personnel.Ligue;
import personnel.SauvegardeImpossible;


public class LigueConsole extends JFrame
{
	private GestionPersonnel gestionPersonnel;
	private EmployeConsole employeConsole;
	
	

	public LigueConsole(GestionPersonnel gestionPersonnel, EmployeConsole employeConsole) throws SauvegardeImpossible, SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException
	{
		this.gestionPersonnel = gestionPersonnel;
		this.employeConsole = employeConsole;
		
		
			}

	
	Menu menuLigues()
	{
		Menu menu = new Menu("Gérer les ligues", "l");
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
		return new Option("Afficher la ligue", "l", 
				() -> 
				{
					System.out.println(ligue);
					System.out.println("administrée par " + ligue.getAdministrateur());
				}
		);
	}
	private Option afficherEmployes(final Ligue ligue)
	{
		return new Option("Afficher les employes", "l", () -> {System.out.println(ligue.getEmployes());});
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
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}
	
	private Menu editerLigue(Ligue ligue)
	{
		Menu menu = new Menu("Editer " + ligue.getNom());
		menu.add(afficher(ligue));
		menu.add(changerNom(ligue));
		menu.add(supprimer(ligue));
		menu.add(gererEmployes(ligue));
		menu.addBack("q");
		return menu;
	}

	private Option changerNom(final Ligue ligue)
	{
		return new Option("Renommer", "r", 
				() -> {try {
					ligue.setNom(getString("Nouveau nom : "));
				} catch (SauvegardeImpossible e) {
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}});
	}

	private List<Ligue> selectionnerLigue()
	{
		return new List<Ligue>("Sélectionner une ligue", "e", 
				() -> new ArrayList<>(gestionPersonnel.getLigues()),
				(element) -> editerLigue(element)
				);
	}
	
	private Option ajouterEmploye(final Ligue ligue)
	{
		return new Option("ajouter un employé", "a",
				() -> 
				{
					try {
						ligue.addEmploye(getString("nom : "), 
								getString("prenom : "), getString("mail : "), 
								LocalDate.parse(getString("date d'arrivé  (AAAA-MM-JJ) : ")), 
								LocalDate.parse(getString("date de départ (AAAA-MM-JJ) : ")), getString("password : "));
					} 
					catch (Exception e) {
						System.out.println("erreur de format dans l'insertion de la date");
						e.printStackTrace();
					}
				}
		);
	}
	
	private Menu gererEmployes(Ligue ligue)
	{
		Menu menu = new Menu("Gérer les employés de " + ligue.getNom(), "e");
		menu.add(afficherEmployes(ligue));
		menu.add(ajouterEmploye(ligue));
		menu.add(selectionnerEmploye(ligue));
		menu.add(changerAdministrateur(ligue));
		menu.addBack("q");
		return menu;
	}
	
	private List<Employe> selectionnerEmploye(final Ligue ligue)
	{
		return new List<>("Selectionne un employe", "e", 
				() -> new ArrayList<>(ligue.getEmployes()),
				employeConsole.editerEmploye()
				);
	}

	private List<Employe> changerAdministrateur(final Ligue ligue)
	{
		return new List<>("Convetir employé en admin", "f", 
				() -> new ArrayList<>(ligue.getEmployes()),
				(index, element) -> {try {
					ligue.setAdministrateur(element);
				} catch (SauvegardeImpossible | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				};
				}
				);
	}		
	
	private Option supprimer(Ligue ligue)
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
	
		