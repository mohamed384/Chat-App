package org.example.controllerFx;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

import java.io.IOException;
import java.util.Optional;

public class MainController {


    @FXML
    private ToggleButton serverControlButton;
    @FXML
    private ToggleButton statisticalShowButton;
    @FXML
    private ToggleButton broadcastShowButton;
    @FXML
    private ToggleButton logOut;

    private ToggleGroup toggleGroup;
    @FXML
    private GridPane gridPane;



    public void initialize() {
        toggleGroup = new ToggleGroup();

        serverControlButton.setToggleGroup(toggleGroup);
        statisticalShowButton.setToggleGroup(toggleGroup);
        broadcastShowButton.setToggleGroup(toggleGroup);
        logOut.setToggleGroup(toggleGroup);

        toggleGroup.selectedToggleProperty().addListener((observable, oldToggle, newToggle) -> {
            if (oldToggle != null) {
                ((ToggleButton) oldToggle).setStyle("-fx-background-color: transparent;");
            }
            if (newToggle != null) {
                ((ToggleButton) newToggle).setStyle("-fx-background-color: #4F2C80;");
            }
        });


    }

    @FXML
    private void servercontrolShow(ActionEvent actionEvent) {


        try {
            // Load the new pane from an FXML file
            Pane newPane = FXMLLoader.load(getClass().getResource("/views/servicecontroller.fxml"));

            gridPane.getChildren().removeIf(node -> {
                Integer columnIndex = GridPane.getColumnIndex(node);
                Integer rowIndex = GridPane.getRowIndex(node);
                Integer rowSpan = GridPane.getRowSpan(node);

                return (columnIndex != null && columnIndex == 1) &&
                        (rowIndex != null && rowIndex == 0) &&
                        (rowSpan != null && rowSpan == 3);
            });

            // Add the new pane to the grid
            gridPane.add(newPane, 1, 0);

            // Set the constraints on the new pane
            GridPane.setRowSpan(newPane, 3);
            GridPane.setHgrow(newPane, Priority.ALWAYS);
            GridPane.setVgrow(newPane, Priority.ALWAYS);


        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @FXML
    private void statisticalShow(ActionEvent actionEvent) {

        try {
            // Load the new pane from an FXML file
                Pane newPane = FXMLLoader.load(getClass().getResource("/views/statistic.fxml"));


            gridPane.getChildren().removeIf(node -> {
                Integer columnIndex = GridPane.getColumnIndex(node);
                Integer rowIndex = GridPane.getRowIndex(node);
                Integer rowSpan = GridPane.getRowSpan(node);

                return (columnIndex != null && columnIndex == 1) &&
                        (rowIndex != null && rowIndex == 0) &&
                        (rowSpan != null && rowSpan == 3);
            });

            // Add the new pane to the grid
            gridPane.add(newPane, 1, 0);

            // Set the constraints on the new pane
            GridPane.setRowSpan(newPane, 3);
            GridPane.setHgrow(newPane, Priority.ALWAYS);
            GridPane.setVgrow(newPane, Priority.ALWAYS);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


@FXML
    private void broadcastShow(ActionEvent actionEvent) {

        try {
            // Load the new pane from an FXML file
            Pane newPane = FXMLLoader.load(getClass().getResource("/views/broadcast.fxml"));


            gridPane.getChildren().removeIf(node -> {
                Integer columnIndex = GridPane.getColumnIndex(node);
                Integer rowIndex = GridPane.getRowIndex(node);
                Integer rowSpan = GridPane.getRowSpan(node);

                return (columnIndex != null && columnIndex == 1) &&
                        (rowIndex != null && rowIndex == 0) &&
                        (rowSpan != null && rowSpan == 3);
            });

            // Add the new pane to the grid
            gridPane.add(newPane, 1, 0);

            // Set the constraints on the new pane
            GridPane.setRowSpan(newPane, 3);
            GridPane.setHgrow(newPane, Priority.ALWAYS);
            GridPane.setVgrow(newPane, Priority.ALWAYS);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @FXML
    private void closeServer(ActionEvent actionEvent){

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Close the server");
        alert.setContentText("Are you sure you want to close the server?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK){

            Platform.exit();
        }

    }

}
