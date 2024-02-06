package org.example.controller.FXMLController;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import org.example.Utils.StubContext;
import org.example.Utils.UserToken;
import org.example.interfaces.CallBackServer;
import org.example.interfaces.UserContact;
import org.example.interfaces.UserSendNotification;

import java.net.URL;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

public class NotificationNodeController implements Initializable {
    @FXML
    public HBox notificationItem;
    @FXML
    public ImageView userImg;
    @FXML
    public Label userName;
    @FXML
    public Label userNumber;
    public Button acceptBtn;
    public Button rejectBtn;
    NotificationController notificationController;
    UserSendNotification remoteNotificationObject;
    UserContact remoteContactObject ;


    public  NotificationNodeController() {
        remoteNotificationObject = UserNotificationController();
        remoteContactObject=  UserContactController();
    }


    public void setNotificationController(NotificationController notificationController) {
        this.notificationController = notificationController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    void setUserName(String name) {
        userName.setText(name);
    }

    void setUserNumber(String number) {
        userNumber.setText(number);
    }

    void setUserImg(Image image) {
        userImg.setImage(image);
    }


    public void acceptClicked() {
        notificationController.removeNotification(userNumber.getText());
        acceptFriendRequest();
    }

    public void rejectClicked() {
        notificationController.removeNotification(userNumber.getText());
        try {
            remoteNotificationObject.deleteNotification(UserToken.getInstance().getUser().getPhoneNumber(),userNumber.getText());
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

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
    private CallBackServer CallBackServerController() {
        CallBackServer remoteObject = null;
        try {
            remoteObject = (CallBackServer) StubContext.getStub("CallBackServerStub");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return remoteObject;
    }

    void acceptFriendRequest() {
        UserContact remoteContactObject = UserContactController();
        CallBackServer callBackServer = CallBackServerController();
        String senderId = userNumber.getText();
        String receiverId = UserToken.getInstance().getUser().getPhoneNumber();
        if (remoteNotificationObject != null && receiverId != null && senderId != null) {
            try {
                remoteContactObject.acceptInvite(receiverId, senderId);
                System.out.println("acceptFriendRequest: in NotificationNodeController");
                callBackServer.updateContactList(senderId);
                callBackServer.updateContactList(receiverId);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
}
