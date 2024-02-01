package org.example.controller.FXMLController;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.controlsfx.control.HiddenSidesPane;
import org.example.DTOs.ChatDTO;
import org.example.DTOs.UserDTO;
import org.example.Utils.StubContext;
import org.example.Utils.UserToken;
import org.example.controller.FXMLController.UtilsFX.StageUtils;
import org.example.interfaces.ChatRMI;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

public class ContactMainController implements Initializable {

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
    ChatRMI chatRMI;
    UserDTO selectedItem;

    public ContactMainController() {
        this.chatRMI = (ChatRMI) StubContext.getStub("ChatControllerStub");

        System.out.println(chatRMI);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TreeView<UserDTO> contactsTreeView = (TreeView<UserDTO>) searchList.getChildren().get(1);
        contactsTreeView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && newValue.getValue() != null) {
                 selectedItem = newValue.getValue();
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


    public void messageBtn(MouseEvent event) {
        try {
            ChatDTO existChat = chatRMI.getPrivateChat(UserToken.getInstance().getUser().getPhoneNumber()
                    , phoneLabel.getText()) ;
            if(existChat == null){
                chatRMI.createChat(nameLabel.getText(),selectedItem.getPicture(),0,
                        UserToken.getInstance().getUser().getPhoneNumber(),phoneLabel.getText());
            }
            BorderPane borderPane= PaneLoaderFactory.messagePageLoader().getKey();
            PaneLoaderFactory.messagePageLoader().getValue().setData(UserToken.getInstance().getUser().getDisplayName(),
                   selectedItem.getPicture());
            BorderPane mainBorder = (BorderPane)  StageUtils.getMainStage().getScene().getRoot();
            mainBorder.setCenter(borderPane);

        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

    }
}
