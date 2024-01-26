package org.example;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.example.controller.ContactController;
import org.example.controller.UserController;
import org.example.controller.UserNotificationController;
import org.example.interfaces.UserAuthentication;
import org.example.interfaces.UserContact;
import org.example.interfaces.UserSendNotification;
import org.example.utils.DBConnection;
import org.example.utils.StubContext;

import java.awt.desktop.AppEvent;
import java.io.IOException;
import java.sql.Connection;


public class Server {

    public static void main(String[] args) {

        StubContext context = new StubContext();

        try {
            // Hena 3mlna el stubs bta3tna
            UserAuthentication userAuthenticationStub = new UserController();
            UserSendNotification userSendNotificationStub = new UserNotificationController();
            UserContact contactControllerStub = new ContactController();

            // Hena 3mlna add lel stubs bta3tna
            context.addStub("UserAuthenticationStub", userAuthenticationStub);
            context.addStub("UserSendNotificationStub", userSendNotificationStub);
            context.addStub("UserContactStub", contactControllerStub);

            // Hena 3mlna create lel registry w 3mlna rebind lel stubs bta3tna
            java.rmi.registry.LocateRegistry.createRegistry(1099);
            java.rmi.Naming.rebind("UserAuthenticationStub", userAuthenticationStub);
            java.rmi.Naming.rebind("UserSendNotificationStub", userSendNotificationStub);
            java.rmi.Naming.rebind("UserContactStub", contactControllerStub);



            // Hena 3mlna connect lel database
            Connection connection = DBConnection.getConnection();
            System.out.println("RMI Server is running...");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //launch(args);

    //System.exit(0);



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

