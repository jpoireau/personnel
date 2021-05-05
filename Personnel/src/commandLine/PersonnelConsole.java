package commandLine;

import static commandLineMenus.rendering.examples.util.InOut.getString;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.sun.org.apache.bcel.internal.generic.LineNumberGen;

import commandLineMenus.Action;
import commandLineMenus.Menu;
import commandLineMenus.Option;
import personnel.GestionPersonnel;
import personnel.Ligue;
import personnel.SauvegardeImpossible;

public class PersonnelConsole extends JFrame
{
	private GestionPersonnel gestionPersonnel;
	LigueConsole ligueConsole;
	EmployeConsole employeConsole;
	
	
	public PersonnelConsole(GestionPersonnel gestionPersonnel) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException, SauvegardeImpossible, SQLException
	{
		this.gestionPersonnel = gestionPersonnel;
		this.employeConsole = new EmployeConsole();
		
		ligueConsole = new LigueConsole(gestionPersonnel, employeConsole);
							
	}
	
	
	public void start()
	{
		menuPrincipal().start();
	}
	
	private Menu menuPrincipal()
	{
		Menu menu = new Menu("Gestion du personnel des ligues");
		menu.add(employeConsole.editerEmploye(gestionPersonnel.getRoot()));
		menu.add(ligueConsole.menuLigues());
		menu.add(quitterEtEnregistrer());
		// menu.add(menuQuitter());
		return menu;
	}

	private Menu menuQuitter()
	{
		Menu menu = new Menu("Quitter", "q");
		menu.add(quitterEtEnregistrer());
		menu.add(quitterSansEnregistrer());
		menu.addBack("r");
		return menu;
	}
	
	private Option quitterEtEnregistrer()
	{
		return new Option("Quitter", "q",
		// return new Option("Quitter et enregistrer", "q", 
				() -> 
				{
					try
					{
						gestionPersonnel.sauvegarder();
						Action.QUIT.optionSelected();
					} 
					catch (SauvegardeImpossible e)
					{
						System.out.println("Impossible d'effectuer la sauvegarde");
					}
				}
			);
	}
	
	private Option quitterSansEnregistrer()
	{
		return new Option("Quitter sans enregistrer", "a", Action.QUIT);
	}
	
	private boolean verifiePassword()
	{
		boolean ok = gestionPersonnel.getRoot().checkPassword(getString("password : "));
		if (!ok)
			System.out.println("Password incorrect.");
		return ok;
	}
	
	public static void main(String[] args) throws SauvegardeImpossible, SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException 
	{
	
		PersonnelConsole personnelConsole;
		personnelConsole = new PersonnelConsole(GestionPersonnel.getGestionPersonnel());
		if (personnelConsole.verifiePassword())
			personnelConsole.start();
	}
	
}