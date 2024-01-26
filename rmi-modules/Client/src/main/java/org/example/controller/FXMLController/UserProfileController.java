package org.example.controller.FXMLController;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import org.example.DTOs.UserDTO;
import org.example.Utils.LoadImage;
import org.example.Utils.UserToken;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class UserProfileController implements Initializable {

    @FXML
    private TextField nameField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField bioField;
    @FXML
    private Button updateBtn;
    @FXML
    private ImageView profileImg;
    SessionManager sessionManager;
    UserDTO userDTO;
    public UserProfileController(){

    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        sessionManager = SessionManager.getInstance();
        //System.out.println(sessionManager.getCurrentUser().toString());
        userDTO= sessionManager.getCurrentUser();
        nameField.setText(userDTO.getDisplayName());
        emailField.setText(userDTO.getEmailAddress());
        bioField.setText(userDTO.getBio());

        profileImg.setImage(new Image(new ByteArrayInputStream(userDTO.getPicture())));
    }

    public void updateClick(MouseEvent e) {

    }

    public void imgClicked() {
        Image image = LoadImage.loadImage();
        if (image != null)
            profileImg.setImage(image);
    }

}