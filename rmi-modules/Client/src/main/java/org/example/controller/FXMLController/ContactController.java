package org.example.controller.FXMLController;


import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import org.example.models.Enums.UserMode;
import org.example.models.Enums.UserStatus;

import java.io.ByteArrayInputStream;

public class ContactController {

    @FXML
    private VBox contact;

    @FXML
    private ImageView status;

    @FXML
    private Circle userImg;

    @FXML
    private Label userName;

    @FXML
    private Label userNumber;



    public ContactController() {
    }

    public ContactController(Image image , String userName, String userNumber , Image status){
        this.userName.setText(userName);
        this.userNumber.setText(userNumber);
        this.userImg.setFill(new ImagePattern(image));
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
        userImg.setFill(new ImagePattern(new Image(new ByteArrayInputStream(img))));
    }
    public void setStatus(UserMode userMode, UserStatus userStatus){
        Image statusImage;
        if(userMode == null && userStatus == null) {
            status.setVisible(false);
            return;
        }
        if(userStatus == UserStatus.Offline)
            statusImage = new Image(getClass().getResourceAsStream("/images/offline-2.png"));
        else {
            statusImage = userMode == UserMode.Available ? new Image(getClass().getResourceAsStream("/images/online.png")) :
                    userMode == UserMode.Busy ? new Image(getClass().getResourceAsStream("/images/busy.png")):
                            new Image(getClass().getResourceAsStream("/images/idle.png"));
        }
        this.status.setImage(statusImage);
    }


}
