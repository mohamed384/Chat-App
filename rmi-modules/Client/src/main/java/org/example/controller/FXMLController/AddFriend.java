package org.example.controller.FXMLController;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import org.example.DTOs.UserDTO;
import org.example.Utils.StubContext;
import org.example.Utils.UserToken;
import org.example.interfaces.CallBackServer;
import org.example.interfaces.UserAuthentication;
import org.example.interfaces.UserContact;
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

    private CallBackServer callBackServer;
    UserContact remoteObject2;

    public AddFriend() {
        remoteObject2 = UserContactController();
    }

    private UserAuthentication UserAuthRemoteObject() {
        UserAuthentication remoteObject = null;
        try {
            remoteObject = (UserAuthentication) Naming.lookup("rmi://localhost:1099/UserAuthenticationStub");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return remoteObject;
    }

    public void setCallBackServer(CallBackServer callBackServer) {
        this.callBackServer = callBackServer;
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

    private UserContact UserContactController() {
        UserContact remoteObject = null;
        try {
            remoteObject = (UserContact) Naming.lookup("rmi://localhost:1099/UserContactStub");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return remoteObject;
    }

//    private CallBackServer CallBackServerController() {
//        CallBackServer remoteObject = null;
//        try {
//            remoteObject = (CallBackServer) Naming.lookup("rmi://localhost:1099/CallBackServerStub");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return remoteObject;
//    }

    @FXML
    void searchOnUser(ActionEvent event) throws RemoteException {

        UserAuthentication remoteObject = UserAuthRemoteObject();
        if (remoteObject != null) {
            String phoneNumber = friendNumberTxt.getText();
            try {
                userDTO = remoteObject.getUser(phoneNumber);
                if (userDTO != null) {
                    searchImage.setVisible(true);
                    searchNumber.setVisible(true);
                    requestButton.setVisible(true);
                    searchResult.setVisible(true);
                    SearchName.setText(userDTO.getDisplayName());
                    searchImage.setImage(new Image(new ByteArrayInputStream(userDTO.getPicture())));
                    searchNumber.setText(userDTO.getPhoneNumber());
                    if (UserToken.getInstance().getUser().getPhoneNumber().equals(userDTO.getPhoneNumber())) {
                        requestButton.setDisable(true);
                        System.out.println("requestButton is disabled");
                    } else {
                        requestButton.setDisable(false);

                    }
                } else {
                    searchResult.setVisible(true);
                    SearchName.setText("User not found");
                    searchImage.setVisible(false);
                    searchNumber.setVisible(false);
                    requestButton.setVisible(false);
                    return;

                }

            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        String senderId = UserToken.getInstance().getUser().getPhoneNumber();
        String receiverId = userDTO.getPhoneNumber();
        if (remoteObject2.contactExists(senderId, receiverId)) {

            requestButton.setDisable(true);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(userDTO.getDisplayName() + " is already a contact");
            alert.setTitle("Contact Exist");
            alert.showAndWait();
//                    alert.setContentText("Are you sure you want to close Cypher?");
            //Alert
            return;
        }


    }


    @FXML
    void sendAddRequest(ActionEvent event) {
        UserSendNotification remoteObject = UserNotificationController();


        String senderId = UserToken.getInstance().getUser().getPhoneNumber();
        String receiverId = userDTO.getPhoneNumber();
        System.out.println("Sender ID: " + senderId);
        System.out.println("Receiver ID: " + receiverId);
        if (remoteObject != null && receiverId != null && senderId != null) {
            try {
                boolean exists = remoteObject.notificationExists(receiverId, senderId);

                if (exists) {
                    System.out.println("From AddFriend: ana h3ml acceptInvite aho");
                    requestImage.setImage(new Image("/images/remove.png"));
                    remoteObject2.acceptInvite(senderId, receiverId);


                } else {
                    boolean isNotificationSent = remoteObject.sendNotification(senderId, receiverId);
                    callBackServer = (CallBackServer) StubContext.getStub("CallBackServerStub");
                    callBackServer.sendNotificationCallBack(senderId,receiverId);

                    if (isNotificationSent) {
                        requestImage.setImage(new Image("/images/remove.png"));
                        System.out.println("From AddFriend: Ml2tsh notification fa hb3t wa7da.");
                    } else {
                        remoteObject.deleteNotification(receiverId, senderId);
                        requestImage.setImage(new Image("/images/add-friend.png"));
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText("Friend request deleted");
                        alert.setTitle("Alert");
                        alert.showAndWait();
                        System.out.println("Failed to send notification.");
                    }
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



