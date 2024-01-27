package org.example.controller.FXMLController;


import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import org.example.DTOs.UserDTO;
import org.example.Utils.LoadImage;
import org.example.Utils.UserToken;
import org.example.service.UserProfileService;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class UserProfileController implements Initializable {
    @FXML
    private Label emailValidLabel;
    @FXML
    private Label bioValidLabel;
    @FXML
    private Label nameValidLabel;
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
    boolean isUpdateBtnEnabled = false;
    UserDTO userDTO;
    private final UserProfileService userProfileService;

    public UserProfileController() {
        userProfileService = new UserProfileService();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        userDTO = UserToken.getInstance().getUser();
        nameField.setText(userDTO.getDisplayName());
        emailField.setText(userDTO.getEmailAddress());
        bioField.setText(userDTO.getBio());
        profileImg.setImage(new Image(new ByteArrayInputStream(userDTO.getPicture())));
        btnUpdateAccordingField();
    }


    private void btnUpdateAccordingField() {
        nameField.textProperty().addListener((observable, oldValue, newValue) -> {
            updateBtn.setDisable(oldValue.equals(newValue));
        });
        emailField.textProperty().addListener((observable, oldValue, newValue) -> {
            updateBtn.setDisable(oldValue.equals(newValue));
        });
        bioField.textProperty().addListener((observable, oldValue, newValue) -> {
            updateBtn.setDisable(oldValue.equals(newValue));
        });
    }

    public void updateClick(MouseEvent e) {

        Platform.runLater(() -> {
            updateBtn.setDisable(true);
            isUpdateBtnEnabled = false;
        });
        UserToken.getInstance().getUser().setDisplayName(nameField.getText());
        UserToken.getInstance().getUser().setEmailAddress(emailField.getText());
        UserToken.getInstance().getUser().setBio(bioField.getText());
        if (userProfileService.updateUserDetails(UserToken.getInstance().getUser(),nameField,nameValidLabel,
                emailValidLabel,emailField, bioValidLabel, bioField)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Updated Successfully");
            alert.setHeaderText(null);
            alert.setContentText("Profile Fields Update Successfully");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Updated Failed");
            alert.setHeaderText(null);
            alert.setContentText("Updated Failed please try again later");
            alert.showAndWait();
        }

    }

    public void imgClicked() {
        Image image = LoadImage.loadImage();
        if (image != null) {
            Platform.runLater(() -> {
                profileImg.setImage(image);
                updateBtn.setDisable(false);
                isUpdateBtnEnabled = true;
                UserToken.getInstance().getUser().setPicture(convertImageToByteArray(image));

            });

        }

    }

    private byte[] convertImageToByteArray(Image image) {
        int width = (int) image.getWidth();
        int height = (int) image.getHeight();
        byte[] imageBytes = new byte[width * height * 4];

        PixelReader pixelReader = image.getPixelReader();
        int pixelIndex = 0;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color color = pixelReader.getColor(x, y);
                imageBytes[pixelIndex++] = (byte) (color.getRed() * 255);     // Red
                imageBytes[pixelIndex++] = (byte) (color.getGreen() * 255);   // Green
                imageBytes[pixelIndex++] = (byte) (color.getBlue() * 255);    // Blue
                imageBytes[pixelIndex++] = (byte) (color.getOpacity() * 255); // Alpha
            }
        }

        return imageBytes;
    }

}