/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication2;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
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
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author maryo
 */
public class MarquageController implements Initializable {
    @FXML
    private TextField auteur;

    @FXML
    private TextField titre;

    @FXML
    private TextArea resume;

    @FXML
    private TextArea tag;

    @FXML
    private TextArea commentaire;
    
    @FXML
    private Label nomfichier;
   
    @FXML
    private Label welcome;
    
    public String login;
   
    @FXML
    private TextField lien;
    
    //Recevoir le login de la scène menu
    public void transferMessage(String message) {login=message;
    //un message de bienvenue est affiché avec le login de l'utilisateur
    welcome.setText("Welcome "+message);}
    

    @FXML//vider les champs:
    void annuler(ActionEvent event) {
     auteur.setText("");
     titre.setText("");
     resume.setText("");
     tag.setText("");
     commentaire.setText("");
     nomfichier.setText("");
    lien.setText("");}
    
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
    
    
    @FXML//choisir un fichier du pc
    void choisirfichier(ActionEvent event) {
        FileChooser fc = new FileChooser();//créer un filechooser fc
        File selectedFile =fc.showOpenDialog(null);//créer un fichier avec le fichier choisi par l'utilisateur
        if(selectedFile != null)//s'il y a un fichier séléctionné:
        {nomfichier.setText(selectedFile.getAbsolutePath());}//on affiche son chemin dans le label
        //sinon on affiche qu'il n'y a pas de fichier séléctionné dans le lable
        else{nomfichier.setText("No File Was Selected.");}}
    
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
    
    Connection con;//création d'une variable con de type connection
    PreparedStatement pst;//cette variable va contenir les requetes sql 
    ResultSet rs;//cette variable va contenir le résultat de la requete
    
    //créer une connection avec la base de données appelée FileManager
    public void connect()
    {try{Class.forName("com.mysql.jdbc.Driver");
    con=DriverManager.getConnection("jdbc:mysql://localhost/FileManager","root","");
    }catch(ClassNotFoundException | SQLException ex) {JOptionPane.showMessageDialog(null,ex);}}
   
    
    @FXML
    void marquer(ActionEvent event) {
        connect();//faire une connection à la base
        
        //on obtient les valeurs données par l'utilisateur:
        String auth=auteur.getText();
        String tit=titre.getText();
        String resum=resume.getText();
        String comm=commentaire.getText();
        String chemin=nomfichier.getText();
        String li=lien.getText();
        Boolean v=false;
        
        String tg=tag.getText();//obtenir les tags du champs sous forme f'une chaine
        String[] tgchaine = tg.split("\n"); //un tableau des tags 
        
        String chainetag="";//une chaine vide
        for(int i=0;i<tgchaine.length;i++)
        {chainetag+=tgchaine[i]+";";}//remplir la chaine des valeurs du tableau contenant les tags avec des ; entre eux
       
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
        else if(!veriftitre(tg))//si tag n'est pas composé de lettres espaces et chiffres seulement:
        JOptionPane.showMessageDialog(null,"The Tags Can Only Contain Letters, Spaces And Digits");
        
        else if(((chemin.equals("No File Was Selected."))||(chemin.equals("")))&&(li.equals("")))//si la label contenant le chemin du fichier est vide un message d'erruer s'affiche
        JOptionPane.showMessageDialog(null,"Choose A File Or A link To Mark Please");  
        
        //si tout est bon:
        else{try {//pst va contenir la requete de la lecture de a table users:
         pst=con.prepareStatement("select * from files where login=? and FilePath=?");
         pst.setString(1,login);
         if(!li.equals(""))
         pst.setString(2,li);
         else pst.setString(2,chemin);
         rs=pst.executeQuery();//execution de la requete.
        
        //si le fichier existe déjà sous le même utilisateur alors v=true
        if(rs.next()){v=true;}
        
       //s'il n'existe pas(v=false) alors on l'insère dans la table
       //on prépare une chaine contenant la requete sql
        if(v==false){String sql="insert into files values(?,?,?,?,?,?,?)";
        //on Set les valeurs de la formulaire dans les champs dans une ligne 
        PreparedStatement pstmt=con.prepareStatement(sql);
        pstmt.setString(1,login);
        if(!li.equals(""))
        pstmt.setString(2,li);         
        else pstmt.setString(2,chemin);
        pstmt.setString(3,tit);
        pstmt.setString(4,auth);
        pstmt.setString(5,chainetag);
        pstmt.setString(6,resum);
        pstmt.setString(7,comm);
        pstmt.executeUpdate();//insertion dans la base
        JOptionPane.showMessageDialog(null,"Successfully Inserted.");//message d'insertion
        //vider les champs
        auteur.setText("");
        titre.setText("");
        resume.setText("");
        tag.setText("");
        commentaire.setText("");
        nomfichier.setText("");
        lien.setText("");}
        //si v=true ( fichier existe déja)
        else {JOptionPane.showMessageDialog(null,"File exists Already Under This User.");}
        con.close();}//fermer la connexion
        catch (SQLException ex) {JOptionPane.showMessageDialog(null,ex);}}}

    
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
    public void initialize(URL url, ResourceBundle rb) {} 
    }
