
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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.JOptionPane;


public class LoginController implements Initializable {

    
    @FXML
    private TextField first;

    @FXML
    private TextField fam;

    @FXML
    private TextField mail;

    @FXML
    private PasswordField pass;

    @FXML
    private TextField log;
   
    @FXML
    private ComboBox gender;
    //création d'une liste qui va contenir les items du comboBox type
    ObservableList<String> combolist= FXCollections.observableArrayList("Female","Male");
    @FXML
    private Hyperlink gotosign;
    @FXML
    private Button btexit;
    @FXML
    private Button creer;
    @FXML
    private Button annuler;
    

    @FXML//vider les champs:
    void annuler(ActionEvent event) {
        gender.getSelectionModel().clearSelection();
        log.setText("");
        pass.setText("");
        mail.setText("");
        fam.setText("");
        first.setText("");}
    
    //vérifier que la chaine ch ne contient que des lettres et des espaces
    public Boolean veriflettres(String ch){
    boolean fn=true;
    for (int i=0;i<ch.length();i++){
    if ((!Character.isLetter(ch.charAt(i)))&&(!Character.isSpace(ch.charAt(i))))
    {fn=false; break;}}
    return fn;}
    
    //vérifier que la chaine ch ne contient que des lettres et des chiffres
    public Boolean veriflog(String ch){
    boolean fn=true;
    for (int i=0;i<ch.length();i++){
    if ((!Character.isLetter(ch.charAt(i)))&&(!Character.isDigit(ch.charAt(i))))
    {fn=false; break;}}
    return fn;}
    
    //vérifier que la chaine ch ne contient que des lettres,des chiffres et que le premier caractère est en majuscule
    public Boolean verifmdp(String ch){
    boolean fn=true;
    char ch1=ch.charAt(0);
    for (int i=0;i<ch.length();i++){
    if ((!Character.isLetter(ch.charAt(i)))&&(!Character.isDigit(ch.charAt(i))))
    {fn=false; break;}}
    if((fn)&&(!Character.isUpperCase(ch1)))fn=false;
    return fn;}
    
    //verifier un mail:
     public boolean verifmail(String email) {
     String patenr = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
     java.util.regex.Pattern p = java.util.regex.Pattern.compile(patenr);
     java.util.regex.Matcher m = p.matcher(email);
     return m.matches();}
    
    Connection con;//création d'une variable con de type connection
    PreparedStatement pst;//cette variable va contenir les requetes sql 
    ResultSet rs;//cette variable va contenir le résultat de la requete
    
    public void connect()//créer une connection avec la base de données appelée FileManager:
    {try{Class.forName("com.mysql.jdbc.Driver");
    con=DriverManager.getConnection("jdbc:mysql://localhost/FileManager","root","");
    }catch(ClassNotFoundException | SQLException ex){JOptionPane.showMessageDialog(null,ex);}}
    
    @FXML//la création d'un utilisateur: 
    void creer(ActionEvent event) {
        connect();//se connecter à la base
        //on obtient les valeurs données par l'utilisateur:
        String lg=log.getText();
        String mdp=pass.getText();
        String ma=mail.getText();
        String pren=first.getText();
        String nom=fam.getText();
        int index=gender.getSelectionModel().getSelectedIndex();//l'indice de ce qui est séléctionné en comboBox
        
        
        
        boolean v=false;// on va utiliser v pour vérifier si l'utilisateur existe déjà ou non

        //vérifier si les champs sont vides et s'ils respectent les contraintes:
        if(pren.equals(""))//si prénom est vide un message d'erruer s'affiche
        JOptionPane.showMessageDialog(null,"Fill In The First Name Field Please");
        else if(!veriflettres(pren))//on vérifie que prénom est composé des lettres et espaces seulement
        JOptionPane.showMessageDialog(null,"The First Name Can Only Contain Letters And Spaces");
        
        else if(nom.equals(""))//si nom est vide un message d'erruer s'affiche
        JOptionPane.showMessageDialog(null,"Fill In The Last Name Field Please");
        else if(!veriflettres(nom))//on vérifie que nom est composé des lettres et espaces seulement
        JOptionPane.showMessageDialog(null,"The Last Name Can Only Contain Letters And Spaces");
        
        else if(ma.equals(""))//si e-mail est vide un message d'erruer s'affiche
        JOptionPane.showMessageDialog(null,"Fill In The E-Mail Field Please");
        else if(!verifmail(ma))//on vérifie que mail est bien formulé
        JOptionPane.showMessageDialog(null,"The E-Mail Format Is Invalid");
         
        else if(index == -1 )
        JOptionPane.showMessageDialog(null,"Choose A Gender  Please");
        
        else if(lg.equals(""))//si login est vide un message d'erruer s'affiche
        JOptionPane.showMessageDialog(null,"Fill In The Login Field Please");
        else if(!veriflog(lg))//on vérifie que login est composé des lettres et chiffres seulement
        JOptionPane.showMessageDialog(null,"The Login Can Only Contain Lettres and Digits");
        else if(lg.length()<6)//si logueur du login est inférieur à 6 un message d'erruer s'affiche
        JOptionPane.showMessageDialog(null,"The Login Can't Have Less Than 6 Caracters");
        
        else if(mdp.equals(""))//si mot de pasee est vide un message d'erruer s'affiche
        JOptionPane.showMessageDialog(null,"Fill In The Password Field Please");
        else if(!verifmdp(mdp))//on vérifie que mot de passe est composé des lettres et espaces seulement et la première lettre en majuscule
        JOptionPane.showMessageDialog(null,"The Password Can Only Contain Lettres and Digits : The First Caracter Should Be An Uppercase");
        else if(mdp.length()<6)//si logueur du mot de passe est inférieur à 6 un message d'erruer s'affiche
        JOptionPane.showMessageDialog(null,"The Password Can't Have Less Than 6 Caracters");
       
        //si toutes les vérifications sont complètes donc :
        else{try{Statement st=con.createStatement();//on créer une statement dont on va executer la requete sql
        String verif="SELECT * FROM users where Login='"+lg+"'";//requete sql pour la selection des logins
        rs=st.executeQuery(verif);//execution de la requete dans la base
        //si l'utilisateur existe déjà alors v=true
        if(rs.next()) {v=true;}
        //s'il n'existe pas(v=false) alors
        if(v==false){String sql="insert into users values(?,?,?,?,?,?)";//requete sql pour l'insertion
        //on met les valeurs de la formulaire dans les champs dans une ligne de la base 
        pst=con.prepareStatement(sql);
        pst.setString(1,lg);
        pst.setString(2,mdp);
        pst.setString(3,nom);
        pst.setString(4,pren);
        pst.setString(5,ma); 
        pst.setString(6, (String) gender.getSelectionModel().getSelectedItem());
        pst.executeUpdate();//insertion dans le tableau
        JOptionPane.showMessageDialog(null,"User Inserted Successfully!");//message d'insertion
        //on vide les champs pour un autre utilisateur
        log.setText("");
        pass.setText("");
        mail.setText("");
        fam.setText("");
        first.setText("");
        gender.getSelectionModel().clearSelection();}
        //si v==true ( utilisateur existe déja):
        else {JOptionPane.showMessageDialog(null,"User Already Exists.");
        //on vide juste le login et mot de passe en cas où le même utilisateur veut faire un autre compte:
        log.setText("");
        pass.setText("");}
        //fermer la connection:
        con.close();}catch(Exception ex)//s'il y a une exception elle sera affichée
        {JOptionPane.showMessageDialog(null,ex);}}}

   
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
    private void go_to_signin(ActionEvent event) {
    try {//si le load du fichier fxml se fait sinon l'exception se déclenche
    FXMLLoader loader=new FXMLLoader(getClass().getResource("Signin.fxml"));
    Parent root=loader.load();
            
    Stage stage = new Stage();//créer la scène suivante
    stage.initStyle(StageStyle.UNDECORATED);//enlever la façade par défaut de la fenêtre
    stage.setScene(new Scene(root));//mettre la scène dans une fenêtre
    stage.show();//afficher la scène dans une nouvelle fenêtre 
    
    ((Node)(event.getSource())).getScene().getWindow().hide();//cacher la fenêtre actuelle
    }catch(IOException ex){System.err.println(ex);}}

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    gender.setItems(combolist);//remplissage du comboBox avec la liste d'items trouvé dans la liste list
        }         
}
