package org.example.controller.FXMLController;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;
import org.example.DTOs.UserDTO;
import org.example.Utils.StubContext;
import org.example.interfaces.UserAuthentication;

import java.io.ByteArrayInputStream;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class MessageContainer {


    public static Label labelText(String text){
        Label label = new Label();
        label.setWrapText(true);
        label.setMaxWidth(Double.MAX_VALUE);
        label.setMaxHeight(Double.MAX_VALUE);
        label.setText(text);
        label.setStyle("-fx-background-color: #9b75d0; -fx-background-radius: 5px; -fx-padding: 10px; -fx-text-fill: white;");
        return label;
    }

    public static UserDTO user(String phoneNumber){
        UserAuthentication userAuthentication = (UserAuthentication) StubContext.getStub("UserAuthenticationStub");
        try {

            return (UserDTO) userAuthentication.getUser(phoneNumber);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }


    public static ImageView getImageForReceiveMessage(UserDTO user){
        Image imageSender = new Image(new ByteArrayInputStream(user.getPicture()));
        ImageView imageView = new ImageView();
        Circle clip = new Circle(15, 15, 15);
        imageView.setClip(clip);
        imageView.setImage(imageSender);
        imageView.setFitWidth(40);
        imageView.setFitHeight(40);
        return imageView;
    }


    public static HBox getHBoxForReceiveMessage(ImageView imageView , Node text ){
        HBox hBox = new HBox(10);
//      //  hBox.setMinSize(text.getWidth() , text.getHeight());
        hBox.setPadding(new Insets(5));
        HBox.setMargin(imageView, new Insets(0, 5, 0, 0));
        hBox.getChildren().add(text);
        hBox.getChildren().add(imageView);
        hBox.setAlignment(Pos.CENTER_RIGHT);
        return hBox;
    }


    public static BorderPane getDateTime(Timestamp timestamp){
        Label timeLabel = new Label();
        BorderPane borderPane = new BorderPane();
        LocalTime currentTime = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:mm");
        if(timestamp.toLocalDateTime().toLocalTime() != currentTime){
            currentTime = timestamp.toLocalDateTime().toLocalTime();
        }
        String timeString = currentTime.format(formatter);
        timeLabel.setText(timeString);
        borderPane.setPrefSize(timeLabel.getPrefWidth(), timeLabel.getPrefHeight());
        borderPane.setCenter(timeLabel);
        return borderPane;
    }


}
