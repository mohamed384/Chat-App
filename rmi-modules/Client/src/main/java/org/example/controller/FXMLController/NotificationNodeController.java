package org.example.controller.FXMLController;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

public class NotificationNodeController implements Initializable {
    @FXML
    public HBox notificationItem;
    @FXML
    public ImageView userImg;
    @FXML
    public Label userName;
    @FXML
    public Label userNumber;
    public Button acceptBtn;
    public Button rejectBtn;
    NotificationController notificationController;

    public void setNotificationController(NotificationController notificationController) {
        this.notificationController = notificationController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    void setUserName(String name) {
        userName.setText(name);
    }

    void setUserNumber(String number) {
        userNumber.setText(number);
    }

    void setUserImg(Image image) {
        userImg.setImage(image);
    }


    public void acceptClicked() {
        notificationController.removeNotification(userNumber.getText());


    }

    public void rejectClicked() {
        notificationController.removeNotification(userNumber.getText());

    }


}
