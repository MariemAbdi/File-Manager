
package javafxapplication2;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.JOptionPane;


public class List_utilController implements Initializable {

    @FXML
    private TableView<users_Admin> user_table;
    @FXML
    private TableColumn<users_Admin, String> log;
    @FXML
    private TableColumn<users_Admin, String> prenom;
    @FXML
    private TableColumn<users_Admin, String> nom;
    @FXML
    private TableColumn<users_Admin, String> mail;
    @FXML
    private TableColumn<users_Admin, String> genre;
    @FXML
    private RadioButton femme;
    @FXML
    private ToggleGroup sexe;
    @FXML
    private RadioButton homme;
    @FXML
    private Label nb_genre;

    @FXML
    private ComboBox combo;
    
    Connection con;//création d'une variable con de type connection
    PreparedStatement pst;//cette variable va contenir les requetes sql 
    ResultSet rs;//cette variable va contenir le résultat de la requete
    ObservableList<users_Admin> list_Util = FXCollections.observableArrayList();
    
     //créer une connection avec la base de données FileManager:
    public void connect()
    {try{Class.forName("com.mysql.jdbc.Driver");
    con=DriverManager.getConnection("jdbc:mysql://localhost:3306/fileManager","root","");
    }catch(ClassNotFoundException | SQLException ex) {JOptionPane.showMessageDialog(null,ex);}}
    
  
  
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    //création d'une liste qui va contenir les items du comboBox type
    ObservableList<String> combolist= FXCollections.observableArrayList("Login","First name","Last name");
    combo.setItems(combolist);
    connect();
    }    

    
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
    private void return_menu(ActionEvent event) {
        try{FXMLLoader loader=new FXMLLoader(getClass().getResource("Admin_Menu.fxml"));
            Parent root=loader.load();
            Stage stage = new Stage();//créer la scène suivante
            stage.initStyle(StageStyle.UNDECORATED);//enlever la façade par défaut de la fenêtre
            stage.setScene(new Scene(root));//mettre la scène dans une fenêtre
            stage.show();//afficher la scène dans une nouvelle fenêtre 

            ((Node)(event.getSource())).getScene().getWindow().hide();//cacher la fenêtre actuelle
            }catch(IOException ex){JOptionPane.showMessageDialog(null,ex);}
    }

    @FXML
    private void deconnect(ActionEvent event) {
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
    window.show();}
    }

    @FXML
    private void annuler(ActionEvent event) {
        list_Util.clear();
        homme.setSelected(false);
        femme.setSelected(false);
        combo.getSelectionModel().clearSelection();
    }

    @FXML
    private void Aff_sur_écran(ActionEvent event) throws SQLException {
        list_Util.clear();
        
      String sqlF="SELECT COUNT(*) FROM users WHERE Sexe LIKE 'Female'";//la chaine de la requête sql
       pst=con.prepareStatement(sqlF);
       rs=pst.executeQuery();//execution de la requete.
       while(rs.next()){
       int nb_femmes = 0 ;
       nb_femmes+=rs.getInt("COUNT(*)");
       String sqlH="SELECT COUNT(*) FROM users WHERE Sexe LIKE 'Male'";//la chaine de la requête sql
       pst=con.prepareStatement(sqlH);
       rs=pst.executeQuery();//execution de la requete.
       while(rs.next()){
       int nb_hommes = 0;
       nb_hommes +=rs.getInt("COUNT(*)");
       nb_genre.setText("The number of females is "+nb_femmes+ " and the number of Males is "+nb_hommes); 
       
     
    int index=combo.getSelectionModel().getSelectedIndex();//l'indice de ce qui est séléctionné en comboBox
    if(index == -1){list_Util.clear();
    String sql="SELECT * FROM users";//la chaine de la requête sql
    pst=con.prepareStatement(sql);
    rs=pst.executeQuery();//execution de la requete.
    while(rs.next()){
       list_Util.add (new users_Admin(rs.getString("Login"),rs.getString("Nom"),rs.getString("Prenom"),rs.getString("Email"),rs.getString("Sexe")));
    
       log.setCellValueFactory(new PropertyValueFactory<>("login"));
       prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
       nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
       mail.setCellValueFactory(new PropertyValueFactory<>("mail"));
       genre.setCellValueFactory(new PropertyValueFactory<>("genre"));
       
       user_table.setItems(list_Util);}}
     
        else if (index == 0 && !femme.isSelected() && !homme.isSelected())  {
        String sql="SELECT * FROM users ORDER BY Login ";//la chaine de la requête sql
        pst=con.prepareStatement(sql);
       rs=pst.executeQuery();//execution de la requete.
       while(rs.next()){
       list_Util.add (new users_Admin(rs.getString("Login"),rs.getString("Nom"),rs.getString("Prenom"),rs.getString("Email"),rs.getString("Sexe")));    
       log.setCellValueFactory(new PropertyValueFactory<>("login"));
       prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
       nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
       mail.setCellValueFactory(new PropertyValueFactory<>("mail"));
       genre.setCellValueFactory(new PropertyValueFactory<>("genre"));
       user_table.setItems(list_Util);}} 
        
    else if (index == 1 && !femme.isSelected() && !homme.isSelected()){
    String sql="SELECT * FROM users ORDER BY Nom ASC";//la chaine de la requête sql
    pst=con.prepareStatement(sql);
    rs=pst.executeQuery();//execution de la requete.
    while(rs.next()){
    list_Util.add (new users_Admin(rs.getString("Login"),rs.getString("Nom"),rs.getString("Prenom"),rs.getString("Email"),rs.getString("Sexe")));    
       log.setCellValueFactory(new PropertyValueFactory<>("login"));
       prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
       nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
       mail.setCellValueFactory(new PropertyValueFactory<>("mail"));
       genre.setCellValueFactory(new PropertyValueFactory<>("genre"));
       user_table.setItems(list_Util);}} 
    
    else if (index == 2 && !femme.isSelected() && !homme.isSelected()){
    String sql="SELECT * FROM users ORDER BY Prenom ASC";//la chaine de la requête sql
    pst=con.prepareStatement(sql);
    rs=pst.executeQuery();//execution de la requete.
    while(rs.next()){
    list_Util.add (new users_Admin(rs.getString("Login"),rs.getString("Nom"),rs.getString("Prenom"),rs.getString("Email"),rs.getString("Sexe")));    
       log.setCellValueFactory(new PropertyValueFactory<>("login"));
       prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
       nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
       mail.setCellValueFactory(new PropertyValueFactory<>("mail"));
       genre.setCellValueFactory(new PropertyValueFactory<>("genre"));
       user_table.setItems(list_Util);}} 
     
    else if (index == 0 && femme.isSelected()){
    String sql="SELECT * FROM users WHERE Sexe LIKE 'Female' ORDER BY Login";//la chaine de la requête sql
    pst=con.prepareStatement(sql);
    rs=pst.executeQuery();//execution de la requete.
    while(rs.next()){
   list_Util.add (new users_Admin(rs.getString("Login"),rs.getString("Nom"),rs.getString("Prenom"),rs.getString("Email"),rs.getString("Sexe")));    
       log.setCellValueFactory(new PropertyValueFactory<>("login"));
       prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
       nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
       mail.setCellValueFactory(new PropertyValueFactory<>("mail"));
       genre.setCellValueFactory(new PropertyValueFactory<>("genre"));
       user_table.setItems(list_Util);}}
    
    else if (index == 1 && femme.isSelected()){
    String sql="SELECT * FROM users WHERE Sexe LIKE 'Female' ORDER BY Nom ASC";//la chaine de la requête sql
    pst=con.prepareStatement(sql);
    rs=pst.executeQuery();//execution de la requete.
    while(rs.next()){
   list_Util.add (new users_Admin(rs.getString("Login"),rs.getString("Nom"),rs.getString("Prenom"),rs.getString("Email"),rs.getString("Sexe")));    
       log.setCellValueFactory(new PropertyValueFactory<>("login"));
       prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
       nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
       mail.setCellValueFactory(new PropertyValueFactory<>("mail"));
       genre.setCellValueFactory(new PropertyValueFactory<>("genre"));
       user_table.setItems(list_Util);}}
    
    else if (index == 2 && femme.isSelected()){
    String sql="SELECT * FROM users WHERE Sexe LIKE 'Female' ORDER BY Prenom ASC";//la chaine de la requête sql
    pst=con.prepareStatement(sql);
    rs=pst.executeQuery();//execution de la requete.
    while(rs.next()){
   list_Util.add (new users_Admin(rs.getString("Login"),rs.getString("Nom"),rs.getString("Prenom"),rs.getString("Email"),rs.getString("Sexe")));    
       log.setCellValueFactory(new PropertyValueFactory<>("login"));
       prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
       nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
       mail.setCellValueFactory(new PropertyValueFactory<>("mail"));
       genre.setCellValueFactory(new PropertyValueFactory<>("genre"));
       user_table.setItems(list_Util);}}
    
     
     else if (index == 0 && homme.isSelected()){
    String sql="SELECT * FROM users WHERE Sexe LIKE 'Male' ORDER BY Login ASC";//la chaine de la requête sql
    pst=con.prepareStatement(sql);
    rs=pst.executeQuery();//execution de la requete.
    while(rs.next()){
    list_Util.add (new users_Admin(rs.getString("Login"),rs.getString("Nom"),rs.getString("Prenom"),rs.getString("Email"),rs.getString("Sexe")));    
       log.setCellValueFactory(new PropertyValueFactory<>("login"));
       prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
       nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
       mail.setCellValueFactory(new PropertyValueFactory<>("mail"));
       genre.setCellValueFactory(new PropertyValueFactory<>("genre"));
       user_table.setItems(list_Util);}}
     
     else if (index == 1 && homme.isSelected()){
    String sql="SELECT * FROM users WHERE Sexe LIKE 'Male' ORDER BY Nom ASC";//la chaine de la requête sql
    pst=con.prepareStatement(sql);
    rs=pst.executeQuery();//execution de la requete.
    while(rs.next()){
    list_Util.add (new users_Admin(rs.getString("Login"),rs.getString("Nom"),rs.getString("Prenom"),rs.getString("Email"),rs.getString("Sexe")));    
       log.setCellValueFactory(new PropertyValueFactory<>("login"));
       prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
       nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
       mail.setCellValueFactory(new PropertyValueFactory<>("mail"));
       genre.setCellValueFactory(new PropertyValueFactory<>("genre"));
       user_table.setItems(list_Util);}}
     
     else if (index == 2 && homme.isSelected()){
    String sql="SELECT * FROM users WHERE Sexe LIKE 'Male' ORDER BY Prenom ASC";//la chaine de la requête sql
    pst=con.prepareStatement(sql);
    rs=pst.executeQuery();//execution de la requete.
    while(rs.next()){
    list_Util.add (new users_Admin(rs.getString("Login"),rs.getString("Nom"),rs.getString("Prenom"),rs.getString("Email"),rs.getString("Sexe")));    
       log.setCellValueFactory(new PropertyValueFactory<>("login"));
       prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
       nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
       mail.setCellValueFactory(new PropertyValueFactory<>("mail"));
       genre.setCellValueFactory(new PropertyValueFactory<>("genre"));
       user_table.setItems(list_Util);}}
     
     
       }}}


    

    @FXML
    private void aff_txt_file(ActionEvent event) throws SQLException {
        int index=combo.getSelectionModel().getSelectedIndex();//l'indice de ce qui est séléctionné en comboBox
         String txtoutput="";//une chaine qui va contenir l'affichage
         Integer i=1;//compteur
     if(index == -1){list_Util.clear();
    String sql="SELECT * FROM users";//la chaine de la requête sql
    pst=con.prepareStatement(sql);
    rs=pst.executeQuery();//execution de la requete.
    while(rs.next()){//tant que il y a suivant on va l'ajouter à la liste
    {txtoutput+="user number :"+i.toString()//Numéroter les utilisateur pour un meilleur affichage 
            +"\nLogin : "+rs.getString("Login")
            +"\nLast name : "+rs.getString("Prenom")
            +"\nFirst name: "+rs.getString("Nom")
            +"\nEmail : "+rs.getString("Email")
            +"\nGendre : "+rs.getString("Sexe");
    txtoutput+="\n\n";
    i++;}}}
  
        
     else if (index == 0 && !femme.isSelected() && !homme.isSelected())  {
        String sql="SELECT * FROM users ORDER BY Login ";//la chaine de la requête sql
        pst=con.prepareStatement(sql);
       rs=pst.executeQuery();//execution de la requete.
       while(rs.next()){//execution de la requete.
    {txtoutput+="user number :"+i.toString()//Numéroter les utilisateur pour un meilleur affichage 
            +"\nLogin : "+rs.getString("Login")
            +"\nLast name : "+rs.getString("Prenom")
            +"\nFirst name: "+rs.getString("Nom")
            +"\nEmail : "+rs.getString("Email")
            +"\nGendre : "+rs.getString("Sexe");
    txtoutput+="\n\n";
    i++;}}}
     
   else if (index == 1 && !femme.isSelected() && !homme.isSelected()){
    String sql="SELECT * FROM users ORDER BY Nom ASC";//la chaine de la requête sql
    pst=con.prepareStatement(sql);
    rs=pst.executeQuery();//execution de la requete.
    while(rs.next()){//tant que il y a suivant on va l'ajouter à la liste
    {txtoutput+="user number :"+i.toString()//Numéroter les utilisateur pour un meilleur affichage 
            +"\nLogin : "+rs.getString("Login")
            +"\nLast name : "+rs.getString("Prenom")
            +"\nFirst name: "+rs.getString("Nom")
            +"\nEmail : "+rs.getString("Email")
            +"\nGendre : "+rs.getString("Sexe");
    txtoutput+="\n\n";
    i++;}}}
     
    else if (index == 2 && !femme.isSelected() && !homme.isSelected()){
    String sql="SELECT * FROM users ORDER BY Prenom ASC";//la chaine de la requête sql
    pst=con.prepareStatement(sql);
    rs=pst.executeQuery();//execution de la requete.
    while(rs.next()){//tant que il y a suivant on va l'ajouter à la liste
    {txtoutput+="user number :"+i.toString()//Numéroter les utilisateur pour un meilleur affichage 
            +"\nLogin : "+rs.getString("Login")
            +"\nLast name : "+rs.getString("Prenom")
            +"\nFirst name: "+rs.getString("Nom")
            +"\nEmail : "+rs.getString("Email")
            +"\nGendre : "+rs.getString("Sexe");
    txtoutput+="\n\n";
    i++;}}}
     
     else if (index == 0 && femme.isSelected()){
    String sql="SELECT * FROM users WHERE Sexe LIKE 'Female' ORDER BY Login";//la chaine de la requête sql
    pst=con.prepareStatement(sql);
    rs=pst.executeQuery();//execution de la requete.
    while(rs.next()){//tant que il y a suivant on va l'ajouter à la liste
    {txtoutput+="user number :"+i.toString()//Numéroter les utilisateur pour un meilleur affichage 
            +"\nLogin : "+rs.getString("Login")
            +"\nLast name : "+rs.getString("Prenom")
            +"\nFirst name: "+rs.getString("Nom")
            +"\nEmail : "+rs.getString("Email")
            +"\nGendre : "+rs.getString("Sexe");
    txtoutput+="\n\n";
    i++;}}}
     
     
     else if (index == 1 && femme.isSelected()){
    String sql="SELECT * FROM users WHERE Sexe LIKE 'Female' ORDER BY Nom ASC";//la chaine de la requête sql
    pst=con.prepareStatement(sql);
    rs=pst.executeQuery();//execution de la requete.
    while(rs.next()){txtoutput+="user number :"+i.toString()//Numéroter les utilisateur pour un meilleur affichage 
            +"\nLogin : "+rs.getString("Login")
            +"\nLast name : "+rs.getString("Prenom")
            +"\nFirst name: "+rs.getString("Nom")
            +"\nEmail : "+rs.getString("Email")
            +"\nGendre : "+rs.getString("Sexe");
    txtoutput+="\n\n";
    i++;}}
     
     else if (index == 2 && femme.isSelected()){
    String sql="SELECT * FROM users WHERE Sexe LIKE 'Female' ORDER BY Prenom ASC";//la chaine de la requête sql
    pst=con.prepareStatement(sql);
    rs=pst.executeQuery();//execution de la requete.
    while(rs.next()){//tant que il y a suivant on va l'ajouter à la liste
    txtoutput+="user number :"+i.toString()//Numéroter les utilisateur pour un meilleur affichage 
            +"\nLogin : "+rs.getString("Login")
            +"\nLast name : "+rs.getString("Prenom")
            +"\nFirst name: "+rs.getString("Nom")
            +"\nEmail : "+rs.getString("Email")
            +"\nGendre : "+rs.getString("Sexe");
    txtoutput+="\n\n";
    i++;}}
     
     else if (index == 0 && homme.isSelected()){
    String sql="SELECT * FROM users WHERE Sexe LIKE 'Male' ORDER BY Login ASC";//la chaine de la requête sql
    pst=con.prepareStatement(sql);
    rs=pst.executeQuery();//execution de la requete.
    while(rs.next()){//tant que il y a suivant on va l'ajouter à la liste
    txtoutput+="user number :"+i.toString()//Numéroter les utilisateur pour un meilleur affichage 
            +"\nLogin : "+rs.getString("Login")
            +"\nLast name : "+rs.getString("Prenom")
            +"\nFirst name: "+rs.getString("Nom")
            +"\nEmail : "+rs.getString("Email")
            +"\nGendre : "+rs.getString("Sexe");
    txtoutput+="\n\n";
    i++;}}
     
    else if (index == 1 && homme.isSelected()){
    String sql="SELECT * FROM users WHERE Sexe LIKE 'Male' ORDER BY Nom ASC";//la chaine de la requête sql
    pst=con.prepareStatement(sql);
    rs=pst.executeQuery();//execution de la requete.
    while(rs.next()){//tant que il y a suivant on va l'ajouter à la liste
      txtoutput+="user number :"+i.toString()//Numéroter les utilisateur pour un meilleur affichage 
            +"\nLogin : "+rs.getString("Login")
            +"\nLast name : "+rs.getString("Prenom")
            +"\nFirst name: "+rs.getString("Nom")
            +"\nEmail : "+rs.getString("Email")
            +"\nGendre : "+rs.getString("Sexe");
    txtoutput+="\n\n";
    i++;}}
    
      else if (index == 2 && homme.isSelected()){
    String sql="SELECT * FROM users WHERE Sexe LIKE 'Male' ORDER BY Prenom ASC";//la chaine de la requête sql
    pst=con.prepareStatement(sql);
    rs=pst.executeQuery();//execution de la requete.
    while(rs.next()){txtoutput+="user number :"+i.toString()//Numéroter les utilisateur pour un meilleur affichage 
            +"\nLogin : "+rs.getString("Login")
            +"\nLast name : "+rs.getString("Prenom")
            +"\nFirst name: "+rs.getString("Nom")
            +"\nEmail : "+rs.getString("Email")
            +"\nGendre : "+rs.getString("Sexe");
    txtoutput+="\n\n";
    i++;}}
    
    
         FileChooser fc = new FileChooser();//créer un filechooser fc
         File selectedFile =fc.showSaveDialog(null);
         try {FileWriter fw = new FileWriter(selectedFile.getAbsolutePath());//créer un filewriter
         fw.write(txtoutput);//fw sera rempli par le texte txtoutput
         fw.close();//fermer le fichier
         }catch(IOException ex){//s'il y a un problème en catch on aura un message
         JOptionPane.showMessageDialog(null,ex);}}
    

    
    
}
