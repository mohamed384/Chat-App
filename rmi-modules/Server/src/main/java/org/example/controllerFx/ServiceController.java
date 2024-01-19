package org.example.controllerFx;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ServiceController {

    @FXML
    private Button onOffButton;
    @FXML
    private ImageView toggleImageView;

    private Image oldImage;
    private Image newImage;
    @FXML
    private Label serverStatus;




    public void initialize() {
        oldImage = new Image("/images/green.png");
        newImage = new Image("/images/red.png");

        toggleImageView.setImage(oldImage);

        onOffButton.setOnAction(event -> {
            if (toggleImageView.getImage() == oldImage) {
                toggleImageView.setImage(newImage);
                serverStatus.setText("Server Off");
                serverStatus.setStyle("-fx-font-weight: bold;");
            } else {
                toggleImageView.setImage(oldImage);
                serverStatus.setText("Server On");
                serverStatus.setStyle("-fx-font-weight: bold;");
            }
        });

    }


}
