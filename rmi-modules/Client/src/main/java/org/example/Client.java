package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.example.interfaces.UserAuthentication;

import java.rmi.Naming;
import java.util.List;

public class Client extends Application {

    public static void main(String[] args) {
        launch();
        try {
            UserAuthentication remoteObject = (UserAuthentication) Naming.lookup("rmi://localhost:1099/rmiObject");
            boolean x=false;
           // System.out.println( x = remoteObject.signup());


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Client.class.getResource("/views/Register.fxml"));

        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }
}
