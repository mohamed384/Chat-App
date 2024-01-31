package org.example.controller.FXMLController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import org.example.DTOs.UserDTO;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class ContactMainController  implements Initializable {

    public BorderPane searchList;
    @FXML
    private ImageView contactImage;
    @FXML
    private Label nameLabel;
    @FXML
    private Label bioLabel;
    @FXML
    private Label phoneLabel;
    @FXML
    private Label emailLabel;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TreeView<UserDTO> contactsTreeView = (TreeView<UserDTO>) searchList.getChildren().get(1);
        contactsTreeView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && newValue.getValue() != null) {
                UserDTO selectedItem = newValue.getValue();
                if (selectedItem != null) {
                    contactImage.setImage(new Image(new ByteArrayInputStream(selectedItem.getPicture())));
                    nameLabel.setText(selectedItem.getDisplayName());
                    bioLabel.setText(selectedItem.getBio());
                    phoneLabel.setText(selectedItem.getPhoneNumber());
                    emailLabel.setText(selectedItem.getEmailAddress());
                }
            }
        });
    }



}
