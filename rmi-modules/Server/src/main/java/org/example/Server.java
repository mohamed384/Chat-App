package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.controller.implementations.UserController;
import org.example.interfaces.UserAuthentication;
import org.example.models.Contact;
import org.example.models.InvitaionStatus;
import org.example.models.Invitations;
import org.example.utils.DBConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Timestamp;


public class Server extends Application {

    public static void main(String[] args) {
        launch();
        try {
            UserAuthentication stub = new UserController();
            java.rmi.registry.LocateRegistry.createRegistry(1099);
            java.rmi.Naming.rebind("rmiObject", stub);
            Connection connection = DBConnection.getConnection();
            System.out.println("RMI Server is running...");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/main.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);

        // Set the title of the window
        primaryStage.setTitle("Server");

        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
