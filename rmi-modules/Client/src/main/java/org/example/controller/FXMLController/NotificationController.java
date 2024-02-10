package org.example.controller.FXMLController;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import org.example.DTOs.NotificationDto;
import org.example.Utils.StubContext;
import org.example.Utils.UserToken;
import org.example.interfaces.CallBackServer;
import org.example.interfaces.UserSendNotification;

import java.io.IOException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class NotificationController implements Initializable {

    public ImageView noNotificationImg;
    @FXML
    private ListView<NotificationDto> notificationListView;

    ObservableList<NotificationDto> observableList;


    private UserSendNotification UserNotificationController() {
        UserSendNotification remoteObject = null;
        try {
            remoteObject = (UserSendNotification) StubContext.getStub("UserSendNotificationStub");
          //  remoteObject = (UserSendNotification) Naming.lookup("rmi://localhost:1099/UserSendNotificationStub");
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

    public void removeNotification(String phoneNumber) {
        int index = -1;
        for (int i = 0; i < observableList.size(); i++) {
            if (observableList.get(i).getPhoneSender().equals(phoneNumber)) {
                index = i;
                break;
            }
        }
        if (index != -1) {
            observableList.remove(index);
        }
        if (!observableList.isEmpty()) {
            notificationListView.toFront();
        } else {
            notificationListView.toBack();
        }
    }

    UserSendNotification userSendNotification;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        PaneLoaderFactory.getInstance().setNotificationController(this);
        userSendNotification = UserNotificationController();
        updateNotificationList();
        notificationListView.setCellFactory(param -> new ListCell<NotificationDto>() {
            @Override
            protected void updateItem(NotificationDto item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/NotificationNode.fxml"));
                        HBox notificationItem = loader.load();
                        NotificationNodeController controller = loader.getController();
                        controller.setUserName(item.getName());
                        controller.setNotificationController(NotificationController.this);
                        Image image = new Image("/images/profile.jpg");
                        controller.setUserImg(image);
                        controller.setUserNumber(item.getPhoneSender());
                        setGraphic(notificationItem);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void updateNotificationList() {
        try {
            List<NotificationDto> notificationList = userSendNotification.receiveNotification(UserToken.getInstance().getUser().getPhoneNumber());
            observableList = FXCollections.observableArrayList(notificationList);
            if (!observableList.isEmpty()) {
                notificationListView.toFront();
            } else {
                notificationListView.toBack();
            }
            notificationListView.setItems(observableList);

        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}
