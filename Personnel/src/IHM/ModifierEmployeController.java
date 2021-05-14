package IHM;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import personnel.Employe;
import personnel.ErreurDateDepart;
import personnel.ErreurDateFin;
import personnel.GestionPersonnel;
import personnel.Ligue;
import personnel.SauvegardeImpossible;

public class ModifierEmployeController{

	private GestionPersonnel gestionPersonnel;
	
	private Ligue ligueActuelle;
	
	private Employe employeActuel;
	
	@FXML
	private Label titre;
	
	@FXML
	private TextField nomEmploye;
	
	@FXML
	private TextField prenomEmploye;
	
	@FXML
	private TextField mailEmploye;
	
	@FXML
	private PasswordField motDePasseEmploye;
	
	@FXML 
	private DatePicker dateDebut;
	
	@FXML
	private DatePicker dateFin;
	
	@FXML
	private CheckBox mettreAdministrateur;
	
	@FXML
	private Button confirmer;
	
	/**
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML
	private void confirmerChangementEmploye(ActionEvent event) throws IOException {
		Stage stage = (Stage) confirmer.getScene().getWindow();
		if(employeActuel != null)
		{
			try {
				employeActuel.setNom(nomEmploye.getText());
				employeActuel.setPrenom(prenomEmploye.getText());
				employeActuel.setMail(mailEmploye.getText());
				employeActuel.setPassword(motDePasseEmploye.getText());
				try {
					employeActuel.setDateDebut(dateDebut.getValue());
				} catch (ErreurDateDepart e) {
					e.printStackTrace();
				}
				try {
					employeActuel.setDateDebut(dateDebut.getValue());
				} catch (ErreurDateDepart e) {
					e.printStackTrace();
				}
				if(mettreAdministrateur.isSelected())
					employeActuel.getLigue().setAdministrateur(employeActuel);
			} catch (SauvegardeImpossible e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		else
		{
			try {
				ligueActuelle.addEmploye(nomEmploye.getText(), prenomEmploye.getText(), mailEmploye.getText(), dateDebut.getValue(), dateFin.getValue(), motDePasseEmploye.getText());
			} catch (ErreurDateDepart | ErreurDateFin | SauvegardeImpossible | SQLException e) {
				e.printStackTrace();
			}
			if(mettreAdministrateur.isSelected())
			{
				for(Employe employes : ligueActuelle.getEmployes())
				{
					if(mailEmploye.getText().equals(employes.getMail()))
						try {
							ligueActuelle.setAdministrateur(employes);
						} catch (SauvegardeImpossible | SQLException e) {
							e.printStackTrace();
						}
				}
			}
		}
		stage.close();
	}

	void donnerDonneesModificationEmploye(Employe employeSelect) {
		titre.setText("Modification employe :" + employeSelect.getNom() + employeSelect.getPrenom());
		nomEmploye.setText(employeSelect.getNom());
		prenomEmploye.setText(employeSelect.getPrenom());
		mailEmploye.setText(employeSelect.getMail());
		motDePasseEmploye.setText(employeSelect.getPassword());
		dateDebut.setValue(employeSelect.getDateDebut());
		dateFin.setValue(employeSelect.getDateFin());
		if(employeSelect.getLigue().getAdministrateur().getMail().equals(employeSelect.getMail()))
		{
			mettreAdministrateur.setSelected(true);
			mettreAdministrateur.setDisable(true);
		}
		employeActuel = employeSelect;
	}

	void donnerLigueCreationEmploye(Ligue ligueSelect) {
		ligueActuelle = ligueSelect;
		titre.setText("Création employé dans " + ligueSelect.getNom());
	}
}