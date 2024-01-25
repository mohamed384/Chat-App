package org.example.controller.FXMLController;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import org.example.DTOs.UserDTO;
import org.example.Utils.LoadImage;
import org.example.Utils.SessionManager;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

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
        sessionManager= SessionManager.getInstance();
        System.out.println(sessionManager.getCurrentUser().toString());
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