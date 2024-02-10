package org.example.controller.FXMLController;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

public class MessageNode {
    public Label userName;
    public Circle userImg;
    public HBox messageItem;
    MessagePage messagePage;
    public void setUserName(String name) {
        userName.setText(name);
    }

    public void setMessageController(MessagePage messagePage) {
        this.messagePage=messagePage;
    }

    public void setUserImg(Image image) {
        userImg.setFill(new ImagePattern(image));
    }
}
