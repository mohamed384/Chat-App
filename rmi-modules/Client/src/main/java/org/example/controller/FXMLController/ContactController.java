package org.example.controller.FXMLController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class ContactController {

    @FXML
    private ListView<?> userlistView;

    @FXML
    public void onOfflineButtonClick(ActionEvent event) {
    }

    @FXML
    public void onOnlineButtonClick(ActionEvent event) {

    }

    @FXML
    private BorderPane borderPane;
    public void initialize() {
        borderPane.heightProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("Height of the parent BorderPane: " + newValue);
        });
    }
    @FXML
    public void onAddContactClick(ActionEvent event) {

        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/views/AddFriend.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Scene secondScene = new Scene(root, 550, 300);

        Stage newWindow = new Stage();
        newWindow.initModality(Modality.APPLICATION_MODAL);
        newWindow.initStyle(StageStyle.UTILITY);
        newWindow.setTitle("Add Friend");
        newWindow.setScene(secondScene);
        newWindow.show();



    }
}

