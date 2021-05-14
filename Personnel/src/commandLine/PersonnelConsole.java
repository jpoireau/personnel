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
	private Box ligneTitre = Box.createHorizontalBox();
	private Box ligneUsername = Box.createHorizontalBox();
	private Box ligneMotDePasse = Box.createHorizontalBox();
	private Box ligneConfirmer = Box.createHorizontalBox();
	private Box ligneErreurConnexion = Box.createHorizontalBox();
	private Box colonne= Box.createVerticalBox();
	private JButton confirmer = new JButton("Confirmer");
	private JTextField username = new JTextField();
	private JPasswordField motDePasse = new JPasswordField();
	private JLabel erreurConnexion = new JLabel("Mot de passe saisie et/ou mail inconnu");
	
	public PersonnelConsole(GestionPersonnel gestionPersonnel) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException, SauvegardeImpossible, SQLException
	{
		this.gestionPersonnel = gestionPersonnel;
		this.employeConsole = new EmployeConsole();
		this.setTitle("Personnel accueil");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBackground(Color.WHITE);
		this.setLocationRelativeTo(null);
		setLookComponent(confirmer);
		setLookComponent(username);
		setLookComponent(motDePasse);
		setLookComponent(erreurConnexion);
		ligneTitre.add(new JLabel("Bienvenue sur personnel "));
		ligneUsername.add(new JLabel("Adresse mail :"));
		username.setMinimumSize(new Dimension(150,25));
		username.setMaximumSize(new Dimension(150,25));
		ligneUsername.add(username);
		ligneMotDePasse.add(new JLabel("Password :"));
		motDePasse.setMinimumSize(new Dimension(150,25));
		motDePasse.setMaximumSize(new Dimension(150,25));
		ligneMotDePasse.add(motDePasse);
		confirmer.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				for(Ligue ligue: GestionPersonnel.getGestionPersonnel().getLigues())	
				{
					
					if(ligue.getAdministrateur().getMail().equals(username.getText().trim()) && ligue.getAdministrateur().getPassword().equals(motDePasse.getText().trim()))
					{
						LigueConsole ligueConsole;
						try {
							ligueConsole = new LigueConsole(gestionPersonnel, employeConsole,ligue.getAdministrateur());
							colonne.removeAll();
							colonne.add(ligueConsole.getColonne());
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
				if(GestionPersonnel.getGestionPersonnel().getRoot().getNom().equals(username.getText().trim()) && GestionPersonnel.getGestionPersonnel().getRoot().getPassword().equals(motDePasse.getText().trim()))
				{
					LigueConsole ligueConsole;
					try {
						if(erreurConnexion.isVisible())
							erreurConnexion.setVisible(false);
						ligueConsole = new LigueConsole(gestionPersonnel, employeConsole,gestionPersonnel.getRoot());
						colonne.removeAll();
						colonne.add(ligueConsole.getColonne());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				else {
					erreurConnexion.setVisible(true);
				}
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		ligneConfirmer.add(confirmer);
		erreurConnexion.setVisible(false);
		ligneErreurConnexion.add(erreurConnexion);
		colonne.add(ligneTitre);
		colonne.add(ligneUsername);
		colonne.add(ligneMotDePasse);
		colonne.add(ligneConfirmer);
		colonne.add(erreurConnexion);
		this.getContentPane().add(colonne);
		this.setSize(700,400);
		this.setResizable(false);
		this.setVisible(true);
	}
	
	public void setLookComponent(Component component) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException
	{
		UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		SwingUtilities.updateComponentTreeUI(component);
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

//	private Menu menuQuitter()
//	{
//		Menu menu = new Menu("Quitter", "q");
//		menu.add(quitterEtEnregistrer());
//		menu.add(quitterSansEnregistrer());
//		menu.addBack("r");
//		return menu;
//	}
	
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
	
	public static void main(String[] args) throws SauvegardeImpossible, SQLException 
	{
		try {
			PersonnelConsole fenetre = new PersonnelConsole(GestionPersonnel.getGestionPersonnel());
		} catch (Exception e) {
			e.printStackTrace();
		}
		//JFrame fenetreLigue = new LigueConsole(GestionPersonnel.getGestionPersonnel());
		PersonnelConsole personnelConsole;
//		personnelConsole = new PersonnelConsole(GestionPersonnel.getGestionPersonnel());
//		if (personnelConsole.verifiePassword())
//			personnelConsole.start();
	}
	
}