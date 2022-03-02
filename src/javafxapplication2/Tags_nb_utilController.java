
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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.JOptionPane;


public class Tags_nb_utilController implements Initializable {

     @FXML
    private Button afficher;

    @FXML
    private TableView<util_tag> tags_nb_util_table;

    @FXML
    private TableColumn<util_tag,String> tags;

    @FXML
    private TableColumn<util_tag,String> nb_users;

    @FXML
    private Button fermer;

    

    @FXML
    void deconnect(ActionEvent event) throws IOException {
 try{
          FXMLLoader loader=new FXMLLoader(getClass().getResource("Signin.fxml"));
            Parent root=loader.load();
            Stage stage = new Stage();//créer la scène suivante
            stage.initStyle(StageStyle.UNDECORATED);//enlever la façade par défaut de la fenêtre
            stage.setScene(new Scene(root));//mettre la scène dans une fenêtre
            stage.show();//afficher la scène dans une nouvelle fenêtre 

            ((Node)(event.getSource())).getScene().getWindow().hide();//cacher la fenêtre actuelle
            }catch(IOException ex){JOptionPane.showMessageDialog(null,ex);}
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
    void return_menu(ActionEvent event) {
    try{FXMLLoader loader=new FXMLLoader(getClass().getResource("Admin_Menu.fxml"));
            Parent root=loader.load();
            Stage stage = new Stage();//créer la scène suivante
            stage.initStyle(StageStyle.UNDECORATED);//enlever la façade par défaut de la fenêtre
            stage.setScene(new Scene(root));//mettre la scène dans une fenêtre
            stage.show();//afficher la scène dans une nouvelle fenêtre 

            ((Node)(event.getSource())).getScene().getWindow().hide();//cacher la fenêtre actuelle
            }catch(IOException ex){JOptionPane.showMessageDialog(null,ex);}}
    
   //création d'une liste qui va contenir les données qui vont être affichés dans le tableau
    ObservableList<util_tag> listF = FXCollections.observableArrayList();
    Connection con;//création d'une variable con de type connection
    PreparedStatement pst;//cette variable va contenir les requetes sql 
    ResultSet rs;//cette variable va contenir le résultat de la requete
    
    //créer une connection avec la base de données FileManager:
    public void connect()
    {try{Class.forName("com.mysql.jdbc.Driver");
    con=DriverManager.getConnection("jdbc:mysql://localhost:3306/fileManager","root","");
    }catch(ClassNotFoundException | SQLException ex) {JOptionPane.showMessageDialog(null,ex);}}
    

    @FXML
    void afficher4(ActionEvent event) {
    connect();//établir la connexion
    try{listF.clear();//vider la liste
    String sql="select * from tags_num";//la chaine de la requête sql
    pst=con.prepareStatement(sql);
    rs=pst.executeQuery();//execution de la requete.
    //si l'utilisateur existe et a au moins un fichier marqué:
    while(rs.next())//tant que il y a suivant on va l'ajouter à la liste
    {listF.add(new util_tag(rs.getString("Tag_Id"),rs.getInt("number")));
    tags_nb_util_table.setItems(listF);}
    }catch(SQLException ex) //s'il y a un problème en catch on aura un message
    {JOptionPane.showMessageDialog(null,ex);}
     tags.setCellValueFactory(new PropertyValueFactory<>("Tag_Id"));
    nb_users.setCellValueFactory(new PropertyValueFactory<>("number"));
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    
    }

    @FXML
    private void annuler(ActionEvent event) {
        listF.clear();//vider la liste 
    }

    @FXML
    private void aff_txt_file(ActionEvent event) throws SQLException {
    String txtoutput="";//une chaine qui va contenir l'affichage
    Integer i=1;//compteur
    String sql="select * from tags_num";//la chaine de la requête sql
    pst=con.prepareStatement(sql);
    rs=pst.executeQuery();//execution de la requete.
    while(rs.next()){
        txtoutput+="tag number :"+i.toString()//Numéroter les taégs pour un meilleur affichage 
            +"\nTag name : "+rs.getString("Tag_Id")
            +"\nNumber of users : "+rs.getInt("number");
    txtoutput+="\n\n";
    i++;}
    
     
     FileChooser fc = new FileChooser();//créer un filechooser fc
         File selectedFile =fc.showSaveDialog(null);
         try {FileWriter fw = new FileWriter(selectedFile.getAbsolutePath());//créer un filewriter
         fw.write(txtoutput);//fw sera rempli par le texte txtoutput
         fw.close();//fermer le fichier
         }catch(IOException ex){//s'il y a un problème en catch on aura un message
         JOptionPane.showMessageDialog(null,ex);}
}
     
}
