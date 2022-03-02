
package javafxapplication2;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.JOptionPane;



public class MenuController implements Initializable {

    @FXML
    private Label welcome;
    
    public String login;
    @FXML
    private Button markf;
   

    //Recevoir le login d'une autre scène fille
    public void transferMessage(String message){login=message;
    //un message de bienvenue est affiché avec le login de l'utilisateur
    welcome.setText("Welcome "+message);}
    
@FXML//créer une méthode pour gérer la fermeture de programme 
    private void quitter(ActionEvent event) {
    //Afficher un message de confirmation
    Alert confirmer = new Alert(Alert.AlertType.CONFIRMATION);
    confirmer.setTitle("Confirmation");//donner un nom au box du message
    confirmer.setHeaderText(null);//supprimer l'en tête
    confirmer.setContentText("Do You Really Want To Exit The Application?");//le message d'affirmation
    confirmer.getButtonTypes().removeAll(ButtonType.CANCEL,ButtonType.OK);//supprimer les bouton d'alerte confirmation
    //Créer et ajouter des nouveaux boutons dans l'alerte confirmation
    ButtonType bt_oui = new ButtonType("Yes");
    ButtonType bt_non = new ButtonType("No");
    confirmer.getButtonTypes().addAll(bt_oui,bt_non);
    //bouton pour gérer la confirmation
    Optional<ButtonType> résultat = confirmer.showAndWait();
    //si le bouton Yes est cliqué donc le programme s'arrête
    if(résultat.get() == bt_oui)System.exit(0);}
    

@FXML//cette méthode va nous afficher l'interface d'ajout de marquage
    void marquer(ActionEvent event) {
    try {//si le load du fichier fxml se fait sinon l'exception se déclenche
    FXMLLoader loader=new FXMLLoader(getClass().getResource("Marquage.fxml"));
    Parent root=loader.load();
  
    MarquageController marq=loader.getController();//créer une instance de type controller de la scène suivante
    marq.transferMessage(login);//affecter le login dans la fonction trouvée dans le controller
            
    Stage stage = new Stage();//créer la scène suivante
    stage.initStyle(StageStyle.UNDECORATED);//enlever la façade par défaut de la fenêtre
    stage.setScene(new Scene(root));//mettre la scène dans une fenêtre
    stage.show();//afficher la scène dans une nouvelle fenêtre 
    
    ((Node)(event.getSource())).getScene().getWindow().hide();//cacher la fenêtre actuelle
    }catch(IOException ex){System.err.println(ex);}}
        
       
    
@FXML//cette méthode va nous afficher l'interface pour la modification des fichiers marqués:
    void modif(ActionEvent event) {
    try {//si le load du fichier fxml se fait sinon l'exception se déclenche
    FXMLLoader loader=new FXMLLoader(getClass().getResource("Modifier.fxml"));
    Parent root=loader.load();
            
    ModifierController modif=loader.getController();//créer une instance de type controller de la scène suivante
    modif.refreshTable(login);//affecter le login dans la fonction trouvée dans le controller
        
    Stage stage = new Stage();//créer la scène suivante
    stage.initStyle(StageStyle.UNDECORATED);//enlever la façade par défaut de la fenêtre
    stage.setScene(new Scene(root));//mettre la scène dans une fenêtre
    stage.show();//afficher la scène dans une nouvelle fenêtre 
    
    ((Node)(event.getSource())).getScene().getWindow().hide();//cacher la fenêtre actuelle
    }catch(IOException ex){System.err.println(ex);}}
    
    
@FXML//cette méthode va nous afficher l'interface de la suppression d'un fichier marqué:
    private void supp(ActionEvent event) {
    try {//si le load du fichier fxml se fait sinon l'exception se déclenche
    FXMLLoader loader=new FXMLLoader(getClass().getResource("Supprimer.fxml"));
    Parent root=loader.load();
  
    SupprimerController supprim=loader.getController();//créer une instance de type controller de la scène suivante
    supprim.refreshTable(login);//affecter le login dans la fonction trouvée dans le controller
                        
    Stage stage = new Stage();//créer la scène suivante
    stage.initStyle(StageStyle.UNDECORATED);//enlever la façade par défaut de la fenêtre
    stage.setScene(new Scene(root));//mettre la scène dans une fenêtre
    stage.show();//afficher la scène dans une nouvelle fenêtre 
    
    ((Node)(event.getSource())).getScene().getWindow().hide();//cacher la fenêtre actuelle
    }catch(IOException ex){System.err.println(ex);}}
 

@FXML//cette méthode va nous afficher l'interface de la recherche d'un fichier marqué:
    private void chercher(ActionEvent event) {
    try {//si le load du fichier fxml se fait sinon l'exception se déclenche
    FXMLLoader loader=new FXMLLoader(getClass().getResource("Recherche.fxml"));
    Parent root=loader.load();
  
    RechercheController rech=loader.getController();//créer une instance de type controller de la scène suivante
    rech.transferMessage(login);//affecter le login dans la fonction trouvée dans le controller
                        
    Stage stage = new Stage();//créer la scène suivante
    stage.initStyle(StageStyle.UNDECORATED);//enlever la façade par défaut de la fenêtre
    stage.setScene(new Scene(root));//mettre la scène dans une fenêtre
    stage.show();//afficher la scène dans une nouvelle fenêtre 
    
    ((Node)(event.getSource())).getScene().getWindow().hide();//cacher la fenêtre actuelle
    }catch (IOException ex) {System.err.println(ex);}}

@FXML//cette méthode va nous afficher l'interface de l'affichage des fichiers marqués:
    private void afficher(ActionEvent event) {
    try {//si le load du fichier fxml se fait sinon l'exception se déclenche
    FXMLLoader loader=new FXMLLoader(getClass().getResource("Listage.fxml"));
    Parent root=loader.load();
  
    ListageController list=loader.getController();//créer une instance de type controller de la scène suivante
    list.transferMessage(login);//affecter le login dans la fonction trouvée dans le controller
                        
    Stage stage = new Stage();//créer la scène suivante
    stage.initStyle(StageStyle.UNDECORATED);//enlever la façade par défaut de la fenêtre
    stage.setScene(new Scene(root));//mettre la scène dans une fenêtre
    stage.show();//afficher la scène dans une nouvelle fenêtre 
    
    ((Node)(event.getSource())).getScene().getWindow().hide();//cacher la fenêtre actuelle
    }catch(IOException ex){System.err.println(ex);}}
  
    
    @FXML
    void deconnect(ActionEvent event) {
    Alert confirmer = new Alert(Alert.AlertType.CONFIRMATION);
    confirmer.setTitle("Confirmation");//donner un nom au box du message
    confirmer.setHeaderText(null);//supprimer l'en tête
    confirmer.setContentText("Do You Really Want To Exit The Application?");//le message d'affirmation
    confirmer.getButtonTypes().removeAll(ButtonType.CANCEL,ButtonType.OK);//supprimer les bouton d'alerte confirmation
    //Créer et ajouter des nouveaux boutons dans l'alerte confirmation
    ButtonType bt_oui = new ButtonType("Yes");
    ButtonType bt_non = new ButtonType("No");
    confirmer.getButtonTypes().addAll(bt_oui,bt_non);
    //bouton pour gérer la confirmation
    Optional<ButtonType> résultat = confirmer.showAndWait();
    //si le bouton Yes est cliqué donc le programme s'arrête
    if(résultat.get() == bt_oui)
    {Parent loglog = null;//on crée un parent
    try {//si le load du fichier fxml se fait sinon l'exception se déclenche
    loglog = FXMLLoader.load(getClass().getResource("Signin.fxml"));
    }catch(IOException ex){JOptionPane.showMessageDialog(null,ex);}
    Scene scene = new Scene(loglog); //la création d'une nouvelle scène
    //Création d'une nouvelle fenêtre où on va afficher la nouvelle scène:
    Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
    window.setScene(scene);
    window.show();}}
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }   

    @FXML//cette méthode va nous afficher l'interface de la configuration d'un compte utilisateur:
    private void parametres(ActionEvent event) {
    try {//si le load du fichier fxml se fait sinon l'exception se déclenche
    FXMLLoader loader=new FXMLLoader(getClass().getResource("Settings.fxml"));
    Parent root=loader.load();
  
    SettingsController s=loader.getController();//créer une instance de type controller de la scène suivante
    s.transferMessage(login);//affecter le login dans la fonction trouvée dans le controller
                        
    Stage stage = new Stage();//créer la scène suivante
    stage.initStyle(StageStyle.UNDECORATED);//enlever la façade par défaut de la fenêtre
    stage.setScene(new Scene(root));//mettre la scène dans une fenêtre
    stage.show();//afficher la scène dans une nouvelle fenêtre 
    
    ((Node)(event.getSource())).getScene().getWindow().hide();//cacher la fenêtre actuelle
    }catch(IOException ex){System.err.println(ex);}}
}
