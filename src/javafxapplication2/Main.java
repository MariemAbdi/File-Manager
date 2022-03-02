/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author maryo
 */
public class Main extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        //le root de type parent va charger la première interface de sign in:
        Parent root = FXMLLoader.load(getClass().getResource("Signin.fxml"));
        //to remove the outside and make ur own design
        stage.initStyle(StageStyle.UNDECORATED);
       //créer une scène avec le contenu en root :
        Scene scene = new Scene(root);
        //set et affichage de la scène:
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
