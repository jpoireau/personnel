package IHM;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javax.swing.UnsupportedLookAndFeelException;

import commandLine.PersonnelConsole;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import personnel.GestionPersonnel;
import personnel.Ligue;
import personnel.SauvegardeImpossible;

public class FXMLDocumentController {

    private GestionPersonnel gestionPersonnel;

    /** Annotation FXML définie que l'on cherche l'id */
    @FXML
    private Label connexion;
    
	@FXML
    private Button confirmer;
   
    @FXML
    private TextField mail;
    
    @FXML
    private PasswordField motDePasse;
    
  
    @FXML
    private void handleButtonAction(ActionEvent event) throws IOException {
    	Stage stage;
        Parent root;
    	if(gestionPersonnel.getGestionPersonnel().getRoot().getNom().equals(mail.getText()) && gestionPersonnel.getGestionPersonnel().getRoot().getPassword().equals(motDePasse.getText()))
    	{
    		stage = (Stage) confirmer.getScene().getWindow();
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("TestAdmin.fxml"));
			root = loader.load();
        	Scene scene = new Scene(root);
        	TestAdminController gestionLigue = loader.<TestAdminController>getController();
			gestionLigue.verifierSiRoot(gestionPersonnel.getGestionPersonnel().getRoot());
        	stage.setTitle(gestionPersonnel.getGestionPersonnel().getRoot().getNom());
        	stage.setScene(scene);
    	}
    	{
    		for(Ligue ligue : gestionPersonnel.getGestionPersonnel().getLigues())
    		{
    			if(ligue.getAdministrateur().getMail().toLowerCase().equals(mail.getText().toLowerCase()) && ligue.getAdministrateur().getPassword().equals(motDePasse.getText()))
    			{
            		stage = (Stage) confirmer.getScene().getWindow();
            		FXMLLoader loader = new FXMLLoader(getClass().getResource("TestAdmin.fxml"));
    				root = loader.load();
            		Scene scene = new Scene(root);
            		TestAdminController gestionLigue = loader.<TestAdminController>getController();
    				gestionLigue.verifierSiRoot(ligue.getAdministrateur());
            		stage.setTitle(ligue.getNom());
            		stage.setScene(scene);
    			}
    			else
    	    	{
    	    		connexion.setText("Mail et/ou mot de passe saisi inconnu.");
    	    	}
    		}
    	}
    }

	public TextField getMail() {
		return mail;
	}

	public void setMail(TextField mail) {
		this.mail = mail;
	}

	public PasswordField getMotDePasse() {
		return motDePasse;
	}

	public void setMotDePasse(PasswordField motDePasse) {
		this.motDePasse = motDePasse;
	}	
}