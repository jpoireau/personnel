package IHM;

import java.io.IOException;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import personnel.GestionPersonnel;
import personnel.Ligue;
import personnel.SauvegardeImpossible;

public class ModifierLigueController {
	
	private GestionPersonnel gestionPersonnel;
	
	private Ligue ligueActuelle;
	
	@FXML
	private TextField nomLigue;
	
	@FXML
	private Button confirmer;
	
	@FXML
    private void confirmerNomLigue(ActionEvent event) throws IOException {
		Stage stage = (Stage) confirmer.getScene().getWindow();
		if(ligueActuelle != null)
		{
			try {
				ligueActuelle.setNom(nomLigue.getText());
			} catch (SauvegardeImpossible | SQLException e) {
				e.printStackTrace();
			}
		}
		else
		{
			try {
				gestionPersonnel.getGestionPersonnel().addLigue(nomLigue.getText());
			} catch (SauvegardeImpossible | SQLException e) {
				e.printStackTrace();
			}
		}
		stage.close();
	}
	
	void getLigue(Ligue ligue)
	{
		ligueActuelle = ligue;
	}
}