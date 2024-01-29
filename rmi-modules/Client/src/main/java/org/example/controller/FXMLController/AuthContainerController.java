package org.example.controller.FXMLController;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import org.example.Utils.UserToken;
import org.example.interfaces.CallBackServer;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AuthContainerController implements Initializable {
    private static AuthContainerController instance;

    public static AuthContainerController getInstance() {
        return instance;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        instance = this;
        loadLoginPane();
    }

    @FXML
    private BorderPane mainBorderPane;

    private void loadLoginPane() {
        // Load the login page from the FXML file
        System.out.println("hoooooo");
              BorderPane loginPane = PaneLoaderFactory.loginPageLoader().getKey();
                // Set the login pane as the center of the BorderPane
              mainBorderPane.setCenter(loginPane);

    }

    public void handleLogout() {

        System.out.println("Before loading login pane");
        loadLoginPane();
        System.out.println("After loading login pane");

    }

    public void switchToPane(Pane signupPane) {
        mainBorderPane.setCenter(signupPane);
    }




}
