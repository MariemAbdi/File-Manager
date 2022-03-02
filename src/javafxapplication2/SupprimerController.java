
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.JOptionPane;


public class SupprimerController implements Initializable {

    @FXML
    private TableColumn<Files, String> chemin;
    @FXML
    private TableColumn<Files, String> titre;
    @FXML
    private TableColumn<Files, String> auteur;
    @FXML
    private TableColumn<Files, String> resume;
    @FXML
    private TableColumn<Files, String> commentaires;
    @FXML
    private TableColumn<Files, String> tag;
    @FXML
    private TableView<Files> filetable;
    @FXML
    private Label welcome;
    
    public String login;

    //création d'une liste qui va contenir les données qui vont être affichés dans le tableau
    ObservableList<Files> fileList= FXCollections.observableArrayList();
    
    Connection con;//création d'une variable con de type connection
    PreparedStatement pst;//cette variable va contenir les requetes sql 
    ResultSet rs;//cette variable va contenir le résultat de la requete
    Files f;
    
    //créer une connection avec la base de données FileManager:
    public void connect()
    {try{Class.forName("com.mysql.jdbc.Driver");
    con=DriverManager.getConnection("jdbc:mysql://localhost/FileManager","root","");
    }catch(ClassNotFoundException | SQLException ex) {JOptionPane.showMessageDialog(null,ex);}}      
    
    
    public void refreshTable(String message){
    fileList.clear();//vider la liste
    welcome.setText("Welcome "+message);//un message de bienvenue est affiché avec le login de l'utilisateur
    login=message;//login prend la valeur obtenue de menu
    try{String sql="select * from files where Login= '"+message+"'";//requete sql
    pst=con.prepareStatement(sql);
    rs=pst.executeQuery();//execution de la requete.
    //si l'utilisateur existe et a au moins un fichier marqué:
    while(rs.next())//tant que il y a suivant on va l'ajouter à la liste
    {fileList.add(new Files(rs.getString("FilePath"),rs.getString("Title"),rs.getString("Author"),rs.getString("Tags"),rs.getString("Summary"),rs.getString("Comments")));}
    }catch(SQLException ex) //s'il y a un problème en catch on aura un message
    {JOptionPane.showMessageDialog(null,ex);}}

    @Override//on va afficher les données de la table "files" dans le tableview "filetable"
    public void initialize(URL url, ResourceBundle rb) {
    connect();//établir la connexion
    refreshTable(login);
    //on set les valeurs dans les colonnes du tableview
    chemin.setCellValueFactory(new PropertyValueFactory<>("FilePath"));
    titre.setCellValueFactory(new PropertyValueFactory<>("Title"));
    chemin.setCellValueFactory(new PropertyValueFactory<>("FilePath"));
    auteur.setCellValueFactory(new PropertyValueFactory<>("Author"));
    resume.setCellValueFactory(new PropertyValueFactory<>("Summary"));
    commentaires.setCellValueFactory(new PropertyValueFactory<>("comments"));
    tag.setCellValueFactory(new PropertyValueFactory<>("Tags"));
    filetable.setItems(fileList);}//on met les données en tableview
           

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
    
    @FXML//cette méthode va nous faire rendre au menu principal:
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
    }catch(IOException ex){JOptionPane.showMessageDialog(null,ex);}}
    
   
    @FXML//aucune ligne n'est séléctionnée
    private void annuler(ActionEvent event) {filetable.getSelectionModel().clearSelection();}

    @FXML//supprimer la ligne séléctionnée
    private void supprim(ActionEvent event) {
        connect();//établir l connection  
        try {f = filetable.getSelectionModel().getSelectedItem();//obtenir la ligne séléctionnée
        String query = "DELETE FROM files WHERE FilePath = ?";//chaine de la requete
        pst=con.prepareStatement(query);//création de la requete
        pst.setString(1,f.getFilePath());//le path de la ligne séléctionnée
        pst.execute();//execution de la requete
        JOptionPane.showMessageDialog(null,"File Deleted Successfully");//message de succès
        refreshTable(login);//un nouveau affichage du tableau
        con.close();//fermer la connection
        }catch(SQLException ex) //s'il y a un problème en catch on aura un message
        {JOptionPane.showMessageDialog(null,ex);}}
    
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
}
