package org.example.controller.FXMLController;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class Contact {

    @FXML
    private HBox contact;

    @FXML
    private ImageView status;

    @FXML
    private ImageView userImg;

    @FXML
    private Label userName;

    @FXML
    private Label userNumber;

    public void setUserName(String name) {
        this.userName.setText(name);
    }

    public void setUserNumber(String number) {
        this.userNumber.setText(number);
    }
}


