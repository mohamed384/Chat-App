package org.example.controller.FXMLController;


import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import org.example.DTOs.UserDTO;
import org.example.Utils.LoadImage;
import org.example.Utils.UserToken;
import org.example.models.Enums.UserMode;
import org.example.models.Enums.UserStatus;
import org.example.service.UserProfileService;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UserProfileController implements Initializable {
    @FXML
    private ComboBox status;
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
    public static class StatusItem {
        private String statusText;
        private Image statusIcon;

        public StatusItem(String statusText, String iconPath) {
            this.statusText = statusText;
            this.statusIcon = new Image(iconPath);
        }

        public String getStatusText() {
            return statusText;
        }

        public Image getStatusIcon() {
            return statusIcon;
        }

        @Override
        public String toString() {
            return statusText;
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        userDTO = UserToken.getInstance().getUser();
        nameField.setText(userDTO.getDisplayName());
        emailField.setText(userDTO.getEmailAddress());
        bioField.setText(userDTO.getBio());
        profileImg.setImage(new Image(new ByteArrayInputStream(userDTO.getPicture())));
        initialzeComboBox();
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
        status.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            updateBtn.setDisable(oldValue != null && oldValue.equals(newValue));
        });
    }
    private void initialzeComboBox(){
        status.getItems().addAll(
                new StatusItem("Available",getClass().getResource("/images/online.png").toExternalForm()),
                new StatusItem("Busy",getClass().getResource("/images/busy.png").toExternalForm() ),
                new StatusItem("Away", getClass().getResource("/images/idle.png").toExternalForm())
        );
        UserStatus userStatus = UserToken.getInstance().getUser().getUserStatus();
        UserMode userMode = UserToken.getInstance().getUser().getUserMode();
        if(userStatus == UserStatus.Online){
            if(userMode == UserMode.Available)
                status.setValue(status.getItems().get(0));
            else if(userMode == UserMode.Busy)
                status.setValue(status.getItems().get(1));
            else if(userMode == UserMode.Away)
                status.setValue(status.getItems().get(2));
        }else{
            status.setValue(status.getItems().get(1));
        }
        // status.setValue(status.getItems().get(0));
        status.setCellFactory(param -> createStatusCell());
        status.setButtonCell(createStatusCell());

    }
    private ListCell<StatusItem> createStatusCell() {
        return new ListCell<StatusItem>() {
            private final ImageView imageView = new ImageView();

            @Override
            protected void updateItem(StatusItem item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setText(item.getStatusText());
                    imageView.setImage(item.getStatusIcon());
                    setGraphic(imageView);
                }
            }
        };
    }


    public void updateClick(MouseEvent e) {

        Platform.runLater(() -> {
            updateBtn.setDisable(true);
            isUpdateBtnEnabled = false;
        });
        UserToken.getInstance().getUser().setDisplayName(nameField.getText());
        UserToken.getInstance().getUser().setEmailAddress(emailField.getText());
        UserToken.getInstance().getUser().setBio(bioField.getText());
        UserToken.getInstance().getUser().setUserMode(status.getSelectionModel().getSelectedIndex() == 0 ? UserMode.Available :
                status.getSelectionModel().getSelectedIndex() == 1 ? UserMode.Busy : UserMode.Away);
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
        //userProfileService.updateUser(UserToken.getInstance().getUser());

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

    public byte[] convertImageToByteArray(Image image) {
        try {
            BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "png", baos);
            return baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


}