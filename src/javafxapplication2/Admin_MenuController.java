
package javafxapplication2;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.JOptionPane;


public class Admin_MenuController implements Initializable {
    
    @FXML
    void aff_list_tags(ActionEvent event) {
      try{FXMLLoader loader=new FXMLLoader(getClass().getResource("list_tags.fxml"));
            Parent root=loader.load();
            Stage stage = new Stage();//créer la scène suivante
            stage.initStyle(StageStyle.UNDECORATED);//enlever la façade par défaut de la fenêtre
            stage.setScene(new Scene(root));//mettre la scène dans une fenêtre
            stage.show();//afficher la scène dans une nouvelle fenêtre 

            ((Node)(event.getSource())).getScene().getWindow().hide();//cacher la fenêtre actuelle
            }catch(IOException ex){JOptionPane.showMessageDialog(null,ex);}}

    
    @FXML
    void aff_list_util(ActionEvent event) {
     try{FXMLLoader loader=new FXMLLoader(getClass().getResource("list_util.fxml"));
            Parent root=loader.load();
            Stage stage = new Stage();//créer la scène suivante
            stage.initStyle(StageStyle.UNDECORATED);//enlever la façade par défaut de la fenêtre
            stage.setScene(new Scene(root));//mettre la scène dans une fenêtre
            stage.show();//afficher la scène dans une nouvelle fenêtre 

            ((Node)(event.getSource())).getScene().getWindow().hide();//cacher la fenêtre actuelle
            }catch(IOException ex){JOptionPane.showMessageDialog(null,ex);}}

    @FXML
    void aff_nb_file(ActionEvent event) {
    try{FXMLLoader loader=new FXMLLoader(getClass().getResource("nb_file.fxml"));
            Parent root=loader.load();
            Stage stage = new Stage();//créer la scène suivante
            stage.initStyle(StageStyle.UNDECORATED);//enlever la façade par défaut de la fenêtre
            stage.setScene(new Scene(root));//mettre la scène dans une fenêtre
            stage.show();//afficher la scène dans une nouvelle fenêtre 

            ((Node)(event.getSource())).getScene().getWindow().hide();//cacher la fenêtre actuelle
            }catch(IOException ex){JOptionPane.showMessageDialog(null,ex);}}

    @FXML
    void aff_tags_nb_util(ActionEvent event) {
    try{FXMLLoader loader=new FXMLLoader(getClass().getResource("tags_nb_util.fxml"));
            Parent root=loader.load();
            Stage stage = new Stage();//créer la scène suivante
            stage.initStyle(StageStyle.UNDECORATED);//enlever la façade par défaut de la fenêtre
            stage.setScene(new Scene(root));//mettre la scène dans une fenêtre
            stage.show();//afficher la scène dans une nouvelle fenêtre 

            ((Node)(event.getSource())).getScene().getWindow().hide();//cacher la fenêtre actuelle
            }catch(IOException ex){JOptionPane.showMessageDialog(null,ex);}}

    @FXML
    void aff_util_tag(ActionEvent event) {
    try{FXMLLoader loader=new FXMLLoader(getClass().getResource("util_tag.fxml"));
        Parent root=loader.load();
        Stage stage = new Stage();//créer la scène suivante
        stage.initStyle(StageStyle.UNDECORATED);//enlever la façade par défaut de la fenêtre
        stage.setScene(new Scene(root));//mettre la scène dans une fenêtre
        stage.show();//afficher la scène dans une nouvelle fenêtre 

        ((Node)(event.getSource())).getScene().getWindow().hide();//cacher la fenêtre actuelle
        }catch(IOException ex){JOptionPane.showMessageDialog(null,ex);}}

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
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
