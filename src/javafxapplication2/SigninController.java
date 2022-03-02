
package javafxapplication2;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.sql.*;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.JOptionPane;



public class SigninController implements Initializable {
     
    @FXML
    private PasswordField mdp;

    @FXML
    private TextField log;
    
    public String login;
    
    Connection con;//création d'une variable con de type connection
    PreparedStatement pst;//cette variable va contenir les requetes sql 
    ResultSet rs;//cette variable va contenir le résultat de la requete
    @FXML
    private Label lbl_log;
    @FXML
    private Label lbl_mdp;
    @FXML
    private Hyperlink gotologin;
    @FXML
    private Button quitter;
    @FXML
    private Button annuler;
    @FXML
    private Button connecter;
    
    
    //créer une connection avec la base de données FileManager:
    public void connect()
    {try{Class.forName("com.mysql.jdbc.Driver");
    con=DriverManager.getConnection("jdbc:mysql://localhost/FileManager","root","");}
    catch (ClassNotFoundException | SQLException ex){JOptionPane.showMessageDialog(null,ex);}}
    
    
    @FXML//vider les champs
    void annuler(ActionEvent event) {
     log.setText("");
     mdp.setText("");}
    
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
    
    
    
    @FXML//cette méthode va nous afficher l'interface de login:
    void go_to_login(ActionEvent event) {
        try {//si le load du fichier fxml se fait sinon l'exception se déclenche
    FXMLLoader loader=new FXMLLoader(getClass().getResource("Login.fxml"));
    Parent root=loader.load();
            
    Stage stage = new Stage();//créer la scène suivante
    stage.initStyle(StageStyle.UNDECORATED);//enlever la façade par défaut de la fenêtre
    stage.setScene(new Scene(root));//mettre la scène dans une fenêtre
    stage.show();//afficher la scène dans une nouvelle fenêtre 
    
    ((Node)(event.getSource())).getScene().getWindow().hide();//cacher la fenêtre actuelle
    }catch(IOException ex){System.err.println(ex);}}
    
    
    @FXML//la connection d'un utilisateur: 
    void connecter(ActionEvent event) throws SQLException {
        connect();//se connecter à la base
        //on obtient les valeurs données par l'utilisateur:
        String lg=log.getText();
        String mp=mdp.getText();
        
        
        //vérifier si les champs sont vides et s'ils respectent les contraintes:
        if(lg.equals(""))//si login est vide un message d'erruer s'affiche
        JOptionPane.showMessageDialog(null,"Fill In The Login Field Please");
        
        else if(mp.equals(""))//si mot de passe est vide un message d'erruer s'affiche
        JOptionPane.showMessageDialog(null,"Fill In The Password Field Please");
        //si toutes les vérifications sont complètes donc :
        else
        {try{//pst va contenir la requete de la lecture de a table users:
         pst=con.prepareStatement("select * from users where login=? and password=?");
         pst.setString(1,lg);
         pst.setString(2,mp);
         rs=pst.executeQuery();//execution de la requete.
         
         if(lg.equals("Admin") && mp.equals("123"))
         {try {//si le load du fichier fxml se fait sinon l'exception se déclenche
            FXMLLoader loader=new FXMLLoader(getClass().getResource("Admin_Menu.fxml"));
            Parent root=loader.load();
            Stage stage = new Stage();//créer la scène suivante
            stage.initStyle(StageStyle.UNDECORATED);//enlever la façade par défaut de la fenêtre
            stage.setScene(new Scene(root));//mettre la scène dans une fenêtre
            stage.show();//afficher la scène dans une nouvelle fenêtre 

            ((Node)(event.getSource())).getScene().getWindow().hide();//cacher la fenêtre actuelle
            }catch(IOException ex){JOptionPane.showMessageDialog(null,ex);}}
         
         //si 'utilisateur existe: on lui donne le choix de choisir quoi faire => un menu
         else if(rs.next()){
          try {//si le load du fichier fxml se fait sinon l'exception se déclenche
            FXMLLoader loader=new FXMLLoader(getClass().getResource("Menu.fxml"));
            Parent root=loader.load();
  
            MenuController menu=loader.getController();//créer une instance de type controller de la scène du menu
            menu.transferMessage(lg);//affecter le login dans la fonction trouvée dans le controller
            
            Stage stage = new Stage();//créer la scène suivante
            stage.initStyle(StageStyle.UNDECORATED);//enlever la façade par défaut de la fenêtre
            stage.setScene(new Scene(root));//mettre la scène dans une fenêtre
            stage.show();//afficher la scène dans une nouvelle fenêtre 

            ((Node)(event.getSource())).getScene().getWindow().hide();//cacher la fenêtre actuelle
            }catch(IOException ex){JOptionPane.showMessageDialog(null,ex);}}
         
         //sinon=> il n'existe pas ou il y a un problème on va afficher un message d'erreur:
         else{JOptionPane.showMessageDialog(null,"User Not Found.Please Try Again.");
         //vider les champs:
         log.setText("");
         mdp.setText("");}
        con.close();}//fermer la connection:
        //s'il y a une exception elle sera affichée
         catch (SQLException ex){JOptionPane.showMessageDialog(null,ex);}}}

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    
}
