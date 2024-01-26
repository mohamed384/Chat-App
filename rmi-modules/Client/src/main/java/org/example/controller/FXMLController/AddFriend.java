package org.example.controller.FXMLController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;
import org.example.DTOs.UserDTO;
import org.example.Utils.SessionManager;
import org.example.interfaces.UserAuthentication;
import org.example.interfaces.UserSendNotification;

import java.io.ByteArrayInputStream;
import java.rmi.Naming;
import java.rmi.RemoteException;

public class AddFriend {


    @FXML
    private Label SearchName;

    @FXML
    private TextField friendNumberTxt;

    @FXML
    private Button requestButton;
    @FXML
    private ImageView requestImage;

    @FXML
    private ImageView searchImage;

    @FXML
    private HBox searchResult;
    @FXML
    private Label searchNumber;
    UserDTO userDTO = null;
    SessionManager sessionManager;




    private UserAuthentication UserAuthRemoteObject() {
        UserAuthentication remoteObject = null;
        try {
            remoteObject = (UserAuthentication) Naming.lookup("rmi://localhost:1099/UserAuthenticationStub");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return remoteObject;
    }

    private UserSendNotification UserNotificationController() {
        UserSendNotification remoteObject = null;
        try {
            remoteObject = (UserSendNotification) Naming.lookup("rmi://localhost:1099/UserSendNotificationStub");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return remoteObject;
    }

    @FXML
    void searchOnUser(ActionEvent event) {

        UserAuthentication remoteObject = UserAuthRemoteObject();
        if (remoteObject != null) {
            String phoneNumber = friendNumberTxt.getText();
            try {
                userDTO = remoteObject.getUser(phoneNumber);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        if (userDTO != null) {
            searchResult.setVisible(true);
            SearchName.setText(userDTO.getDisplayName());
            // profileImg.setImage(new Image(new ByteArrayInputStream(userDTO.getPicture())));
            searchImage.setImage(new Image(new ByteArrayInputStream(userDTO.getPicture())));
            searchNumber.setText(userDTO.getPhoneNumber());
        }
    }




    @FXML
    void sendAdd(ActionEvent event) {
        sessionManager = SessionManager.getInstance();
        UserSendNotification remoteObject = UserNotificationController();
        String senderId = sessionManager.getCurrentUser().getPhoneNumber();
        String receiverId = userDTO.getPhoneNumber();
        System.out.println("Sender ID: " + senderId);
        System.out.println("Receiver ID: " + receiverId);
        if (remoteObject != null && receiverId != null && senderId != null) {
            try {
                boolean isNotificationSent = remoteObject.sendNotification(senderId, receiverId);
                if (isNotificationSent) {
                    System.out.println("Notification sent successfully.");
                } else {
                    System.out.println("Failed to send notification.");
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

//    @Override
//    public void initialize(URL url, ResourceBundle resourceBundle) {
//        listview.setCellFactory(param -> new ListCell<UserDTO>() {
//            @Override
//            protected void updateItem(UserDTO item, boolean empty) {
//                super.updateItem(item, empty);
//                if (empty || item == null) {
//                    setText(null);
//                } else {
//                    setText(item.getDisplayName());
//                }
//            }
//        });
//    }
}



