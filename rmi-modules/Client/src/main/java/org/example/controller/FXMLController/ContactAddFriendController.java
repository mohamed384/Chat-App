package org.example.controller.FXMLController;


import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.example.models.Enums.UserMode;
import org.example.models.Enums.UserStatus;

import java.io.ByteArrayInputStream;

public class ContactAddFriendController {

    @FXML
    private VBox contact;

    @FXML
    private ImageView status;

    @FXML
    private ImageView userImg;

    @FXML
    private Label userName;

    @FXML
    private Label userNumber;

    public ContactAddFriendController() {
    }

    public ContactAddFriendController( Image image , String userName, String userNumber , Image status){
        this.userName.setText(userName);
        this.userNumber.setText(userNumber);
        this.userImg.setImage(image);
        this.status.setImage(status);
    }

    @FXML
    void SendAddFriend(MouseEvent event) {


    }

    public void setUserName(String name) {
        userName.setText(name);
    }

    public void setUserNumber(String number) {
        userNumber.setText(number);
    }
    public void setUserImg(byte [] img){
        userImg.setImage(new Image(new ByteArrayInputStream(img)));
    }
    public void setStatus(UserMode userMode, UserStatus userStatus){
        Image statusImage;
        if(userStatus == UserStatus.Offline)
            statusImage = new Image(getClass().getResource("/images/offline-2.png").toExternalForm());
        else {
            statusImage = userMode == UserMode.Available ? new Image(getClass().getResource("/images/online.png").toExternalForm()) :
                    userMode == UserMode.Busy ? new Image(getClass().getResource("/images/busy.png").toExternalForm()):
                            new Image(getClass().getResource("/images/idle.png").toExternalForm());
        }
        this.status.setImage(statusImage);
    }


}
