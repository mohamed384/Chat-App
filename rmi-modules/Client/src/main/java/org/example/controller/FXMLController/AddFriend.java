package org.example.controller.FXMLController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.example.DTOs.UserDTO;
import org.example.Utils.SessionManager;
import org.example.interfaces.UserAuthentication;
import org.example.interfaces.UserSendNotification;

import java.net.URL;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

public class AddFriend implements Initializable {


    @FXML
    private TextField friendNumberTxt;

    //    @FXML
//    private ListView<String> listview;
    @FXML
    private ListView<UserDTO> listview;

    private SessionManager sessionManager;

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

        sessionManager = SessionManager.getInstance();
        UserDTO userDTO = null;
        UserAuthentication remoteObject = UserAuthRemoteObject();
        if (remoteObject != null) {
            String phoneNumber = friendNumberTxt.getText();
            System.out.println("Phone Number: " + phoneNumber);
            try {
                userDTO = remoteObject.getUser(phoneNumber);
                System.out.println("UserDTO: " + userDTO);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        if (userDTO != null) {
            String displayName = userDTO.getDisplayName();
            System.out.println("Display Name: " + displayName);
            listview.getItems().add(userDTO);
        } else {
            System.out.println("No user found with the provided phone number.");
        }

    }



    @FXML
    void sendAdd(ActionEvent event) {
        sessionManager = SessionManager.getInstance();
        UserSendNotification remoteObject = UserNotificationController();
        String senderId = sessionManager.getCurrentUser().getPhoneNumber();
        UserDTO selectedUser = listview.getSelectionModel().getSelectedItem();
        String receiverId = selectedUser != null ? selectedUser.getPhoneNumber() : null;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listview.setCellFactory(param -> new ListCell<UserDTO>() {
            @Override
            protected void updateItem(UserDTO item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getDisplayName());
                }
            }
        });
    }
}


