package javafxapplication2;

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
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.JOptionPane;


public class RechercheController implements Initializable {

    @FXML 
    private TextField recherche;
    
    public String login;
    @FXML
    private Label welcome;
    @FXML
    private TableView<Files> filetable;
    @FXML
    private TableColumn<Files, String> chemin;
    @FXML
    private TableColumn<Files, String> resume;
    @FXML
    private TableColumn<Files, String> commentaires;
    @FXML
    private TableColumn<Files, String> tag;
    @FXML
    private TableColumn<Files, String> auteur;
    @FXML
    private TableColumn<Files, String> titre;
    
    //Recevoir le login de la scène menu
    public void transferMessage(String message){login=message;
    //un message de bienvenue est affiché avec le login de l'utilisateur
    welcome.setText("Welcome "+message);}
    
    
    //création d'une liste qui va contenir les données qui vont être affichés dans le tableau
    ObservableList<Files> fileList= FXCollections.observableArrayList();
    
    Connection con;//création d'une variable con de type connection
    PreparedStatement pst;//cette variable va contenir les requetes sql 
    ResultSet rs;//cette variable va contenir le résultat de la requete
    
    //créer une connection avec la base de données FileManager:
    public void connect()
    {try{Class.forName("com.mysql.jdbc.Driver");
    con=DriverManager.getConnection("jdbc:mysql://localhost:3306/FileManager","root","");
    }catch (ClassNotFoundException | SQLException ex) {JOptionPane.showMessageDialog(null,ex);}}      
    
    
    
    @FXML
    private ComboBox type;
    //création d'une liste qui va contenir les items du comboBox type
    ObservableList<String> combolist= FXCollections.observableArrayList("Author","Tag","Title");
           
    
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
    
    
@FXML//vider le champs de la recherche et remettre le comboBox à l'état initial
    void annuler(ActionEvent event) {
    recherche.setText("");
    type.getSelectionModel().clearSelection();
    fileList.clear();}
    
    
    //vider et afficher les fichiers enregistrés sous le login de l'utilisateur actuel
    public void refreshTable() {
    fileList.clear();//vider la liste
    try{String sql="select * from files where Login= '"+login+"'";//la chaine de la requête sql
    pst=con.prepareStatement(sql);
    rs=pst.executeQuery();//execution de la requete.
    //si l'utilisateur existe et a au moins un fichier marqué:
    while(rs.next())//tant que il y a suivant on va l'ajouter à la liste
    {fileList.add(new Files(rs.getString("FilePath"),rs.getString("Title"),rs.getString("Author"),rs.getString("Tags"),rs.getString("Summary"),rs.getString("Comments")));}
    }catch(SQLException ex) //s'il y a un problème en catch on aura un message
    {JOptionPane.showMessageDialog(null,ex);}
    //on set les valeurs dans les colonnes du tableview
       chemin.setCellValueFactory(new PropertyValueFactory<>("FilePath"));
       titre.setCellValueFactory(new PropertyValueFactory<>("Title"));
       auteur.setCellValueFactory(new PropertyValueFactory<>("Author"));
       resume.setCellValueFactory(new PropertyValueFactory<>("Summary"));
       commentaires.setCellValueFactory(new PropertyValueFactory<>("Comments"));
       tag.setCellValueFactory(new PropertyValueFactory<>("Tags"));
       //on met les données en tableview
       filetable.setItems(fileList);}
    
    
    
@Override//initialisation des données: (comboBox items)
    public void initialize(URL url, ResourceBundle rb) {
    type.setItems(combolist);//remplissage du comboBox avec la liste d'items trouvé dans la liste list
    connect();//établir la connexion
    refreshTable();}//un affichage du tableau lors l'affichage de la fenêtre
       
    
@FXML//cette méthode va nous faire retourner au menu principal:
    private void return_menu(ActionEvent event) {
    try {//si le load du fichier fxml se fait sinon l'exception se déclenche
    FXMLLoader loader=new FXMLLoader(getClass().getResource("Menu.fxml"));
    Parent root=loader.load();
  
    MenuController menu=loader.getController();//créer une instance de type controller de la scène du menu
    menu.transferMessage(login);//affecter le login dans la fonction trouvée dans le controller
            
    Stage stage = new Stage();//créer la scène suivante
    stage.initStyle(StageStyle.UNDECORATED);//enlever la façade par défaut de la fenêtre
    stage.setScene(new Scene(root));//mettre la scène dans une fenêtre
    stage.show();//afficher la scène dans une nouvelle fenêtre 
    
    ((Node)(event.getSource())).getScene().getWindow().hide();//cacher la fenêtre actuelle
    }catch (IOException ex) {System.err.println(ex);}}

    
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

    @FXML//Une méthode pour gérer la recherche des fichiers:
    void chercher(ActionEvent event) {
        int index=type.getSelectionModel().getSelectedIndex();//l'indice de ce qui est séléctionné en comboBox
        String rech=recherche.getText();
            
        if((index == 0 )&&(!rech.equals("")))//si auteur est séléctionné en comboBox et le champ de la recherche est non vide
        {fileList.clear();//vider la liste
        try{String sql="select * from files where Login= '"+login+"' and Author like '%"+rech+"%'";;//la chaine de la requête sql
         pst=con.prepareStatement(sql);
        rs=pst.executeQuery();//execution de la requete.
        //si l'utilisateur existe et a au moins un fichier marqué:
        while(rs.next())//tant que il y a suivant on va l'ajouter à la liste
        {fileList.add(new Files(rs.getString("FilePath"),rs.getString("Title"),rs.getString("Author"),rs.getString("Tags"),rs.getString("Summary"),rs.getString("Comments")));}
        }catch(SQLException ex) //s'il y a un problème en catch on aura un message
        {JOptionPane.showMessageDialog(null,ex);}}
        else if((index == 1 )&&(!rech.equals("")))//si tag est séléctionné en comboBox et le champ de la recherche est non vide
        {fileList.clear();//vider la liste
        try{String sql="select * from files where Login= '"+login+"' and Tags like '%"+rech+"%'";//la chaine de la requête sql
         pst=con.prepareStatement(sql);
        rs=pst.executeQuery();//execution de la requete.
        //si l'utilisateur existe et a au moins un fichier marqué:
        while(rs.next())//tant que il y a suivant on va l'ajouter à la liste
        {fileList.add(new Files(rs.getString("FilePath"),rs.getString("Title"),rs.getString("Author"),rs.getString("Tags"),rs.getString("Summary"),rs.getString("Comments")));}
        }catch(SQLException ex) //s'il y a un problème en catch on aura un message
        {JOptionPane.showMessageDialog(null,ex);}}
        else if((index == 2 )&&(!rech.equals("")))//si titre est séléctionné en comboBox et le champ de la recherche est non vide
        {fileList.clear();//vider la liste
        try{String sql="select * from files where Login= '"+login+"' and Title like '%"+rech+"%'";//la chaine de la requête sql
         pst=con.prepareStatement(sql);
        rs=pst.executeQuery();//execution de la requete.
        //si l'utilisateur existe et a au moins un fichier marqué:
        while(rs.next())//tant que il y a suivant on va l'ajouter à la liste
        {fileList.add(new Files(rs.getString("FilePath"),rs.getString("Title"),rs.getString("Author"),rs.getString("Tags"),rs.getString("Summary"),rs.getString("Comments")));}
        }catch(SQLException ex) //s'il y a un problème en catch on aura un message
        {JOptionPane.showMessageDialog(null,ex);}}
        else refreshTable();//si le champs est vide ou bien rien n'est choisi en comboBox etc.
       }
    
   
    
    
}