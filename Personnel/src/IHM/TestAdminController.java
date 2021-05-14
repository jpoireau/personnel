package IHM;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import personnel.Employe;
import personnel.GestionPersonnel;
import personnel.Ligue;
import personnel.SauvegardeImpossible;

public class TestAdminController{
	
	private String mail;
	
	private String mdp;
	
	
	public String getMail() {
		return mail;
	}



	public void setMail(String mail) {
		this.mail = mail;
	}



	public String getMdp() {
		return mdp;
	}



	public void setMdp(String mdp) {
		this.mdp = mdp;
	}

	private GestionPersonnel gestionPersonnel;
    
    private final ObservableList<Ligue> donneesTableauLigue = FXCollections.observableArrayList();
    
    private final ObservableList<Employe> donneesTableauEmploye = FXCollections.observableArrayList();
    
    private Ligue ligueSelect;
    
    private Employe employeSelect;
    
    private Employe utilisateurConnecter;
    
    private int i = 0;
    
    @FXML
    private Label titre;
    
    @FXML
    private TableView<Ligue> tableauLigue;
    
    @FXML
    private TableColumn<Ligue, String> colonneNomLigue;
    
    @FXML
    private TableColumn<Ligue, Void> colonneVoirLigue;
    
    @FXML
    private TableColumn<Ligue, Void> colonneModifierLigue;
    
    @FXML
    private TableColumn<Ligue, Void> colonneSupprimerLigue;
    
    @FXML
    private TableView<Employe> tableauEmploye;
    
    @FXML
    private TableColumn<Employe, String> colonneNomEmploye;
    
    @FXML
    private TableColumn<Employe, String> colonnePrenomEmploye;
    
    @FXML
    private TableColumn<Employe, String> colonneMailEmploye;
    
    @FXML
    private TableColumn<Employe, LocalDate> colonneDateDebutEmploye;
    
    @FXML
    private TableColumn<Employe, LocalDate> colonneDateFinEmploye;
    
    @FXML
    private TableColumn<Employe, Void> colonneModifierEmploye;
    
    @FXML
    private TableColumn<Employe, Void> colonneSupprimerEmploye;
    
    @FXML
    private TableColumn<Employe, Void> colonneEstAdminEmploye;
    
    @FXML
    private Button ajouterLigue;
    
    @FXML
    private Button ajouterEmploye;
    
    @FXML
    private Button deconnecter;
    
    @FXML
    private void initializeTableauLigue() throws IOException {
    	if(tableauLigue.getItems().size() > 0)
    	{
    		tableauLigue.getItems().clear(); // refresh en enlevant les données du tableau (pour si on enlève une ligue pour rafraichir le tableau)
    	}
    	if(utilisateurConnecter.getNom().equals(gestionPersonnel.getGestionPersonnel().getRoot().getNom()))
    	{
    		for(Ligue ligue : gestionPersonnel.getGestionPersonnel().getLigues())
    		{
    			donneesTableauLigue.add(ligue); // Si connecter en root on prend toutes les ligues
    		}
    	}
    	else
    	{
    		donneesTableauLigue.add(utilisateurConnecter.getLigue());// On récupère la ligue de l'admin si connecté en admin
    	}
    	colonneNomLigue.setCellValueFactory(new PropertyValueFactory<>("nom"));
		tableauLigue.setItems(donneesTableauLigue); //On ajoute données dans tableau ligue
		ajouterColonneVoirLigue();
		ajouterColonneSupprimerLigue();
		ajouterColonneModifierLigue();
	}
	
    @FXML
    private void initializeTableauEmploye() throws IOException {
    	if(tableauEmploye.getItems().size() > 0)
    	{
    		tableauEmploye.getItems().clear();
    	}
    	colonneNomEmploye.setCellValueFactory(new PropertyValueFactory<>("nom"));//On définit le nom de la variable de la classe à prendre
    	colonnePrenomEmploye.setCellValueFactory(new PropertyValueFactory<>("prenom"));
    	colonneMailEmploye.setCellValueFactory(new PropertyValueFactory<>("mail"));
    	colonneDateDebutEmploye.setCellValueFactory(new PropertyValueFactory<>("dateDebut"));
    	colonneDateFinEmploye.setCellValueFactory(new PropertyValueFactory<>("dateFin"));
    	for(Ligue ligue : gestionPersonnel.getGestionPersonnel().getLigues())
    	{
    		if(ligueSelect.getNom().equals(ligue.getNom()))
    		{
    			for(Employe employesLigue : ligue.getEmployes())
    	    	{ 
    	    		donneesTableauEmploye.add(employesLigue);// On ajoute tout les employés de la ligue
    	    	}
    		} 
    	}
    	tableauEmploye.setItems(donneesTableauEmploye);
    	ajouterColonneModifierEmploye();//On définit le format de la colonne pour afficher des boutons
    	ajouterColonneSupprimerEmploye();
    	ajouterColonneEstAdminEmploye();
    	tableauEmploye.autosize();
	}
    
	private void ajouterColonneVoirLigue()
	{
		Callback<TableColumn<Ligue, Void>, TableCell<Ligue, Void>> cellFactory = new Callback<TableColumn<Ligue, Void>, TableCell<Ligue, Void>>() {
            @Override
            public TableCell<Ligue, Void> call(final TableColumn<Ligue, Void> param) {
                final TableCell<Ligue, Void> cell = new TableCell<Ligue, Void>() {

                    private final Button btn = new Button("Voir la ligue");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                        	ligueSelect = getTableView().getItems().get(getIndex()); //Onn récupère la ligue de la ligne sélectionnée
                        	try {
								initializeTableauEmploye();//
								ajouterEmploye.setVisible(true);// On fait apparaître le bouton pour ajouter employé
							} catch (IOException e) {
								e.printStackTrace();
							}
                        });
                    }
                    
                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };

        colonneVoirLigue.setCellFactory(cellFactory);
	}
	
	private void ajouterColonneModifierLigue()
	{
		Callback<TableColumn<Ligue, Void>, TableCell<Ligue, Void>> cellFactory = new Callback<TableColumn<Ligue, Void>, TableCell<Ligue, Void>>() {
            @Override
            public TableCell<Ligue, Void> call(final TableColumn<Ligue, Void> param) {
                final TableCell<Ligue, Void> cell = new TableCell<Ligue, Void>() {

                    private final Button btn = new Button("Modifier la ligue");

                    {
                    	btn.setStyle(".buttonModifier");
                        btn.setOnAction((ActionEvent event) -> {
                        	ligueSelect = getTableView().getItems().get(getIndex());
                        	Stage stage;
                            AnchorPane root;
                        	try {
                        		FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifierLigue.fxml"));
                        		stage = (Stage) btn.getScene().getWindow();
                    			root = loader.load();
								Scene fenetreModifierLigue = new Scene(root);
								Stage modifierLigue = new Stage();
								modifierLigue.setTitle(ligueSelect.getNom());
								modifierLigue.initModality(Modality.WINDOW_MODAL);
								ModifierLigueController ligueController = loader.<ModifierLigueController>getController();
								ligueController.getLigue(ligueSelect);
								modifierLigue.initOwner(stage);
								modifierLigue.setScene(fenetreModifierLigue);
								modifierLigue.setResizable(false);
								modifierLigue.show();
							} catch (IOException e) {
								e.printStackTrace();
							}
                        });
                    }
                    
                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };
        colonneModifierLigue.setCellFactory(cellFactory);
        colonneModifierLigue.setStyle(".buttonModifier");
	}
	
	private void ajouterColonneSupprimerLigue()
	{
		Callback<TableColumn<Ligue, Void>, TableCell<Ligue, Void>> cellFactory = new Callback<TableColumn<Ligue, Void>, TableCell<Ligue, Void>>() {
            @Override
            public TableCell<Ligue, Void> call(final TableColumn<Ligue, Void> param) {
                final TableCell<Ligue, Void> cell = new TableCell<Ligue, Void>() {

                    private Button btn = new Button("Supprimer la ligue");
                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Ligue ligueASupprimer = getTableView().getItems().get(getIndex());
                            for(Ligue ligue : gestionPersonnel.getGestionPersonnel().getLigues()) {
                            	if(ligue.getNom().equals(ligueASupprimer.getNom()))
									try {
										ligue.remove();
									} catch (SauvegardeImpossible | SQLException e) {
										e.printStackTrace();
									}
                            }
                            try {
								initializeTableauLigue();
								initializeTableauEmploye();
								ajouterEmploye.setVisible(false);
							} catch (IOException e) {
								e.printStackTrace();
							}
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };

        colonneSupprimerLigue.setCellFactory(cellFactory);
	}
	
	private void ajouterColonneEstAdminEmploye()
	{
		Callback<TableColumn<Employe, Void>, TableCell<Employe, Void>> cellFactory = new Callback<TableColumn<Employe, Void>, TableCell<Employe, Void>>() {
            @Override
            public TableCell<Employe, Void> call(final TableColumn<Employe, Void> param) {
                final TableCell<Employe, Void> cell = new TableCell<Employe, Void>() {
                    private CheckBox checkBox = new CheckBox();
                    {
                    	if(tableauEmploye.getItems().size() > i)
                    	{	
                    		if(tableauEmploye.getItems().get(i).estAdmin(ligueSelect))
                    			checkBox.setSelected(true);
                    		else
                    			checkBox.setSelected(false);
                    		checkBox.setDisable(true);
                    		i++;
                    	}
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(checkBox);
                        }
                    }
                };
                return cell;
            }
        };
        i = 0;	
        colonneEstAdminEmploye.setCellFactory(cellFactory);
	}

	private void ajouterColonneModifierEmploye()
	{
		Callback<TableColumn<Employe, Void>, TableCell<Employe, Void>> cellFactory = new Callback<TableColumn<Employe, Void>, TableCell<Employe, Void>>() {
            @Override
            public TableCell<Employe, Void> call(final TableColumn<Employe, Void> param) {
                final TableCell<Employe, Void> cell = new TableCell<Employe, Void>() {

                    private final Button btn = new Button("Modifier l'employe");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                        	employeSelect = getTableView().getItems().get(getIndex());//On récupère l'employé de la ligue sélécctionné
                        	Stage stage;
                            AnchorPane root;
                        	try {
                        		stage = (Stage) btn.getScene().getWindow();
                        		FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifierEmploye.fxml"));
                    			root = loader.load();
								Scene fenetreModifierEmploye= new Scene(root);
								Stage modifierEmploye = new Stage();//Instanciation de la fenêtre pour modifier un employé
								modifierEmploye.setTitle(employeSelect.getLigue() + " , " + employeSelect.getMail());
								modifierEmploye.initModality(Modality.WINDOW_MODAL);
								modifierEmploye.initOwner(stage);
								modifierEmploye.setScene(fenetreModifierEmploye);
								modifierEmploye.setResizable(false);
								ModifierEmployeController employeController = loader.<ModifierEmployeController>getController();
								employeController.donnerDonneesModificationEmploye(employeSelect);//On passe en paramètre l'employé à modifier
								modifierEmploye.show();
							} catch (IOException e) {
								e.printStackTrace();
							}
                        });
                    }
                    
                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };
        colonneModifierEmploye.setCellFactory(cellFactory);//On donne le format de cellule et l'action des boutons de la colonne à la colonne modifier employé
	}
	
	private void ajouterColonneSupprimerEmploye()
	{
		Callback<TableColumn<Employe, Void>, TableCell<Employe, Void>> cellFactory = new Callback<TableColumn<Employe, Void>, TableCell<Employe, Void>>() {
            @Override
            public TableCell<Employe, Void> call(final TableColumn<Employe, Void> param) {
                final TableCell<Employe, Void> cell = new TableCell<Employe, Void>() {

                    private final Button btn = new Button("Supprimer l'employe");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                        	try {
								getTableView().getItems().get(getIndex()).remove();
							} catch (SauvegardeImpossible | SQLException e) {
								e.printStackTrace();
							}
                        	try {
								initializeTableauEmploye();
							} catch (IOException e) {
								e.printStackTrace();
							}
                        });
                    }
                    
                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };
        colonneSupprimerEmploye.setCellFactory(cellFactory);
	}
	
	void verifierSiRoot(Employe admin)
	{
		utilisateurConnecter = admin;
		ajouterEmploye.setVisible(false);
		if(!admin.getNom().equals(gestionPersonnel.getGestionPersonnel().getRoot().getNom()))
			ajouterLigue.setVisible(false);
		else
			ajouterLigue.setVisible(true);
		try {
			initializeTableauLigue();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	 @FXML
	 private void addLigue(ActionEvent event) {
		 Stage stage;
         AnchorPane root;
     	try {
     		stage = (Stage) ajouterLigue.getScene().getWindow();
 			root = FXMLLoader.load(TestJavaFx.class.getResource("ModifierLigue.fxml"));
				Scene fenetreCreationLigue = new Scene(root);
				Stage creationLigue = new Stage();
				creationLigue.setTitle("Ajouter ligue");
				creationLigue.initModality(Modality.WINDOW_MODAL);
				creationLigue.initOwner(stage);
				creationLigue.setScene(fenetreCreationLigue);
				creationLigue.setResizable(false);
				creationLigue.show();
			} catch (IOException e) {
				e.printStackTrace();
			}
	 }
	  
	 @FXML
	 private void addEmploye(ActionEvent event) {
		 Stage stage;
         AnchorPane root;
     	try {
     		stage = (Stage) ajouterEmploye.getScene().getWindow();
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifierEmploye.fxml"));
			root = loader.load();
			Scene fenetreCreationEmploye= new Scene(root);
			Stage creationEmploye = new Stage();
			creationEmploye.setTitle("Ajouter employé");
			creationEmploye.initModality(Modality.WINDOW_MODAL);
			creationEmploye.initOwner(stage);
			creationEmploye.setScene(fenetreCreationEmploye);
			ModifierEmployeController employeController = loader.<ModifierEmployeController>getController();
			employeController.donnerLigueCreationEmploye(ligueSelect);
			creationEmploye.setResizable(false);
			creationEmploye.show();
			} catch (IOException e) {
				e.printStackTrace();
			}
	 }
	 
	 @FXML
	 private void disconnect(ActionEvent event) {
		 Stage stage;
         AnchorPane root;
     		stage = (Stage) deconnecter.getScene().getWindow();
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("Test.fxml"));
			try {
				root = loader.load();
	        	Scene scene = new Scene(root);
	        	stage.setScene(scene);
	        	stage.setTitle("Connexion");
			} catch (IOException e) {
				e.printStackTrace();
			}
	 }
}