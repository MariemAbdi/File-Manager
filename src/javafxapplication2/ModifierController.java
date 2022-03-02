/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author maryo
 */
public class ModifierController implements Initializable {

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
    private TextArea commentaire;
    @FXML
    private TextField auteurr;
    @FXML
    private TextField titree;
    @FXML
    private TextArea resumee;
    @FXML
    private TextArea tagg;
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
    con=DriverManager.getConnection("jdbc:mysql://localhost:3306/FileManager","root","");
    }catch(ClassNotFoundException | SQLException ex) {JOptionPane.showMessageDialog(null,ex);}}      
    
    
    //vider et afficher les fichiers enregistrés sous le login de l'utilisateur actuel
    public void refreshTable(String message) {
    welcome.setText("Welcome "+message);//un message de bienvenue est affiché avec le login de l'utilisateur
    login=message;//login prend la valeur obtenue de menu
    fileList.clear();//vider la liste
    try{String sql="select * from files where Login= '"+login+"'";//la chaine de la requête sql
    pst=con.prepareStatement(sql);
    rs=pst.executeQuery();//execution de la requete.
    //si l'utilisateur existe et a au moins un fichier marqué:
    while(rs.next())//tant que il y a suivant on va l'ajouter à la liste
    {fileList.add(new Files(rs.getString("FilePath"),rs.getString("Title"),rs.getString("Author"),rs.getString("Tags"),rs.getString("Summary"),rs.getString("Comments")));}
    }catch(SQLException ex) //s'il y a un problème en catch on aura un message
    {JOptionPane.showMessageDialog(null,ex);}}
    
    @Override//initialisation des données:
    public void initialize(URL url, ResourceBundle rb){
       connect();//établir la connexion
       refreshTable(login);//un affichage du tableau lors l'affichage de la fenêtre
       //on set les valeurs dans les colonnes du tableview
       chemin.setCellValueFactory(new PropertyValueFactory<>("FilePath"));
       titre.setCellValueFactory(new PropertyValueFactory<>("Title"));
       chemin.setCellValueFactory(new PropertyValueFactory<>("FilePath"));
       auteur.setCellValueFactory(new PropertyValueFactory<>("Author"));
       resume.setCellValueFactory(new PropertyValueFactory<>("Summary"));
       commentaires.setCellValueFactory(new PropertyValueFactory<>("comments"));
       tag.setCellValueFactory(new PropertyValueFactory<>("Tags"));
       //on met les données en tableview
       filetable.setItems(fileList);}    

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
    }catch (IOException ex) {JOptionPane.showMessageDialog(null,ex);}}
    
    //vérifier que la chaine ch ne continet que des lettres et des espaces
    public Boolean veriflettres(String ch){
    boolean fn=true;
    for (int i=0;i<ch.length();i++){
    if ((!Character.isLetter(ch.charAt(i)))&&(!Character.isSpace(ch.charAt(i))))
    {fn=false; break;}} return fn;}
    
    //vérifier que la chaine ch ne continet que des lettres, des espaces et des chiffres
    public Boolean veriftitre(String ch){
    boolean fn=true;
    for (int i=0;i<ch.length();i++){
    if ((!Character.isLetter(ch.charAt(i)))&&(!Character.isSpace(ch.charAt(i)))&&(!Character.isDigit(ch.charAt(i))))
    {fn=false; break;}} return fn;}

    @FXML//Une méthode pour gérer la modification des fichiers:
    private void mod(ActionEvent event) {
        //l'obtenition des valeurs des champs:
        String tit=titree.getText();
        String auth=auteurr.getText();
        String tg=tagg.getText();
        String resu=resumee.getText();
        String comm=commentaire.getText();
        f = filetable.getSelectionModel().getSelectedItem();//obtenir la ligne séléctionnée
       
       
        //vérifier si les champs sont vides et s'ils respectent les contraintes:
        //puisque le champs auteur n'est pas obligatoire, seulement s'il est rempli 
        //on vérifie qu'il est composé des lettres et espaces seulement
        if((auth.equals("")==false)&&(!veriflettres(auth)))
        JOptionPane.showMessageDialog(null,"The Author's Name Can Only Contain Letters And Spaces");
        
        //les champs obligatoires:
        else if(tit.equals(""))//si titre est vide un message d'erruer s'affiche
        JOptionPane.showMessageDialog(null,"Fill In The Title Field Please");
        else if(!veriftitre(tit))//si titre n'est pas composé de lettres espaces et chiffres seulement
        JOptionPane.showMessageDialog(null,"The Title Can Only Contain Letters, Spaces And Digits");
        
        else if(tg.equals(""))//si tag est vide un message d'erruer s'affiche
        JOptionPane.showMessageDialog(null,"Fill In The Tags Field Please");
        else if(!veriftitre(tit))//si tag n'est pas composé de lettres espaces et chiffres seulement
        JOptionPane.showMessageDialog(null,"The Tags Can Only Contain Letters, Spaces And Digits");
        
        //si tout est bon:
        else{try {//la chaine de la requête:
        String sql="update files set Title= '"+tit+"' , Author= '"+auth+"' ,Tags= '"+tg+"' , Summary= '"+resu+"' , Comments= '"+comm+"' where FilePath= ?";
        pst=con.prepareStatement(sql);//affecter la chaine en statement pst
        pst.setString(1,f.getFilePath());//le path de la ligne séléctionnée
        pst.execute();//execution
        JOptionPane.showMessageDialog(null,"File Modified Successfully");//message de succès
        refreshTable(login);//un nouveau affichage du tableau
        con.close();}//fermer la connexion
        catch(SQLException ex){JOptionPane.showMessageDialog(null,ex);}}}
    
    
    
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
    
    @FXML//vider les champs et aucune ligne sera séléctionnée:
    void annuler(ActionEvent event) {
     filetable.getSelectionModel().clearSelection();
     auteurr.setText("");
     titree.setText("");
     resumee.setText("");
     tagg.setText("");
     commentaire.setText("");}

    @FXML//afficher la ligne séléctionnée dans les champs afin de les modifier:
    private void afficher(MouseEvent event) {
        connect();//établir l connection  
        f = filetable.getSelectionModel().getSelectedItem();//obtenir la ligne séléctionnée
        
        String ch=f.getTags();//obtenir les tags du tableau
        String[] tgchaine = ch.split("\\;"); //un tableau des tags 
        
        String chainetag="";//une chaine vide
        for(int i=0;i<tgchaine.length;i++)
        {chainetag+=tgchaine[i]+"\n";}//remplir la chaine des valeurs du tableau contenant les tags
        
        //remplir les champs avec les données de la ligne séléctionnée:
        commentaire.setText(f.getComments());
        auteurr.setText(f.getAuthor());
        titree.setText(f.getTitle());
        resumee.setText(f.getSummary());
        tagg.setText(chainetag);}
    
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
