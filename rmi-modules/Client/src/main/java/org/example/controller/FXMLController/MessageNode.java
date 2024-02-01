package org.example.controller.FXMLController;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class MessageNode {
    public Label userName;
    public ImageView userImg;
    public HBox messageItem;
    MessagePage messagePage;
    public void setUserName(String name) {
        userName.setText(name);
    }

    public void setMessageController(MessagePage messagePage) {
        this.messagePage=messagePage;
    }

    public void setUserImg(Image image) {
        userImg.setImage(image);
    }
}
