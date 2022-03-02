
package javafxapplication2;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.JOptionPane;


public class Nb_fileController implements Initializable {
    @FXML
    private TableView<nb_files> filestable;

    @FXML
    private TableColumn<nb_files, String> login;
    
    @FXML
    private TableColumn<nb_files, String> nom;

    @FXML
    private TableColumn<nb_files, String> prenom;

    @FXML
    private TableColumn<nb_files, Integer> nb_files;
    @FXML
    private Button afficher;
    @FXML
    private Button fermer;


     @FXML//vider le champs 
    void annuler(ActionEvent event) {
    
    listNB.clear();}
    
    
    
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

    @FXML
    void return_menu(ActionEvent event) {
    try{FXMLLoader loader=new FXMLLoader(getClass().getResource("Admin_Menu.fxml"));
            Parent root=loader.load();
            Stage stage = new Stage();//créer la scène suivante
            stage.initStyle(StageStyle.UNDECORATED);//enlever la façade par défaut de la fenêtre
            stage.setScene(new Scene(root));//mettre la scène dans une fenêtre
            stage.show();//afficher la scène dans une nouvelle fenêtre 

            ((Node)(event.getSource())).getScene().getWindow().hide();//cacher la fenêtre actuelle
            }catch(IOException ex){JOptionPane.showMessageDialog(null,ex);}}
    
    
    Connection con;//création d'une variable con de type connection
    PreparedStatement pst;//cette variable va contenir les requetes sql 
    ResultSet rs;//cette variable va contenir le résultat de la requete
    ObservableList<nb_files> listNB = FXCollections.observableArrayList();
    
     //créer une connection avec la base de données FileManager:
    public void connect()
    {try{Class.forName("com.mysql.jdbc.Driver");
    con=DriverManager.getConnection("jdbc:mysql://localhost:3306/fileManager","root","");
    }catch(ClassNotFoundException | SQLException ex) {JOptionPane.showMessageDialog(null,ex);}}
    
    @FXML
    void afficher2(ActionEvent event) throws SQLException {
        listNB.clear();
    String sql="SELECT u.Login, u.Nom,u.Prenom, COUNT(f.FilePath) FROM users u, files f WHERE u.Login = f.Login group BY u.login ";//la chaine de la requête sql
    pst=con.prepareStatement(sql);
    rs=pst.executeQuery();//execution de la requete.
    while(rs.next()){
    listNB.add (new nb_files(rs.getString("Login"),rs.getString("Nom"),rs.getString("Prenom"),rs.getInt("COUNT(f.FilePath)")));
    
       login.setCellValueFactory(new PropertyValueFactory<>("login"));
       nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
       prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
       nb_files.setCellValueFactory(new PropertyValueFactory<>("nbfiles"));
       
       filestable.setItems(listNB);}}
    
    
    @FXML
    void aff_txt_file(ActionEvent event) {
    String txtoutput="";//une chaine qui va contenir l'affichage
    Integer i=1;//compteur
    try{String sql="SELECT u.Login, u.Nom,u.Prenom, COUNT(f.FilePath) FROM users u, files f WHERE u.Login = f.Login group BY u.login";//la chaine de la requête sql
    pst=con.prepareStatement(sql);
    rs=pst.executeQuery();//execution de la requete.
    while(rs.next())//tant que il y a suivant on va l'ajouter à la liste
    {txtoutput+="user number :"+i.toString()//Numéroter les utilisateur pour un meilleur affichage 
            +"\nLogin : "+rs.getString("Login")
            +"\nLast name : "+rs.getString("Prenom")
            +"\nFirst name: "+rs.getString("Nom")
            +"\nNumber of Favorite Files : "+rs.getInt("COUNT(f.FilePath)");
    txtoutput+="\n\n";
    i++;}}catch(SQLException ex) //s'il y a un problème en catch on aura un message
    {JOptionPane.showMessageDialog(null,ex);}
        
         FileChooser fc = new FileChooser();//créer un filechooser fc
         File selectedFile =fc.showSaveDialog(null);
         try {FileWriter fw = new FileWriter(selectedFile.getAbsolutePath());//créer un filewriter
         fw.write(txtoutput);//fw sera rempli par le texte txtoutput
         fw.close();//fermer le fichier
         }catch(IOException ex){//s'il y a un problème en catch on aura un message
         JOptionPane.showMessageDialog(null,ex);}}

    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    connect();//établir la connexion
    }    
    
}
