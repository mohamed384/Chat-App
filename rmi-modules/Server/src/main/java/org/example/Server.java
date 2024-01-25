package org.example;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.example.controller.UserController;
import org.example.interfaces.UserAuthentication;
import org.example.utils.DBConnection;

import java.awt.desktop.AppEvent;
import java.io.IOException;
import java.sql.Connection;


public class Server extends Application {

    public static void main(String[] args) {


        try {
            UserAuthentication stub = new UserController();
            java.rmi.registry.LocateRegistry.createRegistry(1099);
            java.rmi.Naming.rebind("rmiObject", stub);
            Connection connection = DBConnection.getConnection();
            System.out.println("RMI Server is running...");

        } catch (Exception e) {
            e.printStackTrace();
        }

        launch(args);

       //System.exit(0);
    }



    public void start(Stage primaryStage) throws Exception {

        // Load the FXML file for the start screen
        Parent startScreenParent = FXMLLoader.load(getClass().getResource("/views/opening.fxml"));
        Scene startScreenScene = new Scene(startScreenParent);

        primaryStage.setScene(startScreenScene);
        primaryStage.show();


        PauseTransition pause = new PauseTransition(Duration.seconds(0.2));

        // Set the action to be executed after the pause
        pause.setOnFinished(event ->
        {
            try {
                // Load the FXML file for the main screen
                Parent mainScreenParent = FXMLLoader.load(getClass().getResource("/views/main.fxml"));
                Scene mainScreenScene = new Scene(mainScreenParent);

                // Set the scene of the primary stage to the main screen
                primaryStage.setScene(mainScreenScene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        // Start the pause
        pause.play();
    }


}

