package org.example.controllerFx;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.example.callBackImp.CallBackServerImp;

import java.rmi.RemoteException;

public class ServiceController {

    @FXML
    private Button onOffButton;
    @FXML
    private ImageView toggleImageView;

    private Image oldImage;
    private Image newImage;
    @FXML
    private Label serverStatus;

    CallBackServerImp callBackServerImp;

    private  boolean isrunning = true;
    public void initialize() {
        try {
             callBackServerImp = new CallBackServerImp();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        oldImage = new Image("/images/green.png");
        newImage = new Image("/images/red.png");

        toggleImageView.setImage(oldImage);

        onOffButton.setOnAction(event -> {
            isrunning = !isrunning;
            if (!isrunning) {
                toggleImageView.setImage(newImage);
                serverStatus.setText("Server Off");
                serverStatus.setStyle("-fx-font-weight: bold;");
                callBackServerImp.logoutAll();

            } else {
                toggleImageView.setImage(oldImage);
                serverStatus.setText("Server On");
                serverStatus.setStyle("-fx-font-weight: bold;");
            }
        });

    }



}
