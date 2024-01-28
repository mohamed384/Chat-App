package org.example.controller.FXMLController;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import org.controlsfx.control.HiddenSidesPane;

import java.io.IOException;
import java.util.Optional;

public class MainController {

    @FXML
    private Button contact;

    @FXML
    private Button message;

    @FXML
    private Button notification;

    @FXML
    private Button profile;

    @FXML
    private BorderPane borderPane;

    @FXML
    public void goToProfile(ActionEvent event) {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/UserProfile.fxml"));

        try {
            BorderPane pane = loader.load();
            pane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
            pane.setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
            borderPane.setCenter(null);
            BorderPane.setAlignment(pane, Pos.CENTER);
            BorderPane.setMargin(pane, new Insets(0, 0, 0, 0));
            borderPane.setCenter(pane);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    public void goToMessage(ActionEvent event) {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/MessagePage.fxml"));

        try {
            HiddenSidesPane pane = loader.load();
            pane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
            pane.setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
            borderPane.setCenter(null);
            BorderPane.setAlignment(pane, Pos.CENTER);
            BorderPane.setMargin(pane, new Insets(0, 0, 0, 0));
            borderPane.setCenter(pane);


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void goToContact(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/ContactList.fxml"));

        try {
            BorderPane pane = loader.load();
            pane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
            pane.setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
            borderPane.setCenter(null);
            borderPane.setCenter(pane);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void goToNotification(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Notification.fxml"));
        try {
            BorderPane pane = loader.load();
            pane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
            pane.setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
            borderPane.setCenter(null);
            borderPane.setCenter(pane);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    public void goToLogOut(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Close the Aplication");
        alert.setContentText("Are you sure you want to close the Cypher?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK){
            Platform.exit();
        }
    }
}
