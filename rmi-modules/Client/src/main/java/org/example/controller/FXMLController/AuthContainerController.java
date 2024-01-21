package org.example.controller.FXMLController;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

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
        try {
            // Load the FXML file for the login pane
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/login.fxml"));
            BorderPane loginPane = loader.load();

            // Set the login pane as the center of the BorderPane
            mainBorderPane.setCenter(loginPane);
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }

    public void switchToPane(Pane signupPane) {
        mainBorderPane.setCenter(signupPane);
    }

}
