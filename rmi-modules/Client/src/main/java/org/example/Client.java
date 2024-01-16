package org.example;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.example.models.User;
import org.example.interfaces.UserAuthentication;


import java.io.IOException;
import java.rmi.Naming;
import java.util.Date;


public class Client extends Application {

    public static void main(String[] args) {
        launch();

        /*
       try {
            UserAuthentication remoteObject = (UserAuthentication) Naming.lookup("rmi://localhost:1099/rmiObject");
            boolean x=false;


            User user = new User("1234567890", "John Doe", "johndoe@example.com",
                    "passwordHash", "Male", "USA",
                    new Date(),  "nada",UserStatus.AVAILABLE, "");
            System.out.println( x = remoteObject.signup(user));

            User user1 = new User("1234567891", "John Doed", "nada@example.com",
                    "passwordHash", "Male", "USA",
                    new Date(),  "nada",UserStatus.AVAILABLE,"D:\\ITI\\download.png");
            System.out.println( x = remoteObject.signup(user1));



        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }




    @Override
    public void start(Stage stage) throws Exception {


            // Load the FXML file for the start screen
        Parent startScreenParent = FXMLLoader.load(getClass().getResource("/views/startScreen.fxml"));
        Scene startScreenScene = new Scene(startScreenParent);

        stage.setScene(startScreenScene);
        stage.show();

            // Create a pause transition of 2 seconds
        PauseTransition pause = new PauseTransition(Duration.seconds(2));

            // Set the action to be executed after the pause
        pause.setOnFinished(event ->
            {
                try {
                    // Load the FXML file for the main screen
                    Parent mainScreenParent = FXMLLoader.load(getClass().getResource("/views/Register.fxml"));
                    Scene mainScreenScene = new Scene(mainScreenParent);

                    // Set the scene of the primary stage to the main screen
                    stage.setScene(mainScreenScene);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            // Start the pause
                pause.play();
        }
















        /*
        FXMLLoader fxmlLoader = new FXMLLoader(Client.class.getResource("/views/Register.fxml"));

        Scene scene = new Scene(fxmlLoader.load());

        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show()
         */
    }