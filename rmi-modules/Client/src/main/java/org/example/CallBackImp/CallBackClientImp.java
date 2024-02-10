package org.example.CallBackImp;

import com.sun.tools.javac.Main;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import org.example.DTOs.MessageDTO;
import org.example.Utils.UserToken;
import org.example.controller.FXMLController.*;
import org.example.controller.FXMLController.AuthContainerController;
import org.example.controller.FXMLController.MessagePage;
import org.example.controller.FXMLController.PaneLoaderFactory;
import org.example.controller.FXMLController.UtilsFX.StageUtils;
import org.example.interfaces.CallBackClient;
import org.example.models.Enums.UserStatus;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;



public class CallBackClientImp extends UnicastRemoteObject implements CallBackClient {

    UserToken userToken = UserToken.getInstance();
    AuthContainerController authContainerController = AuthContainerController.getInstance();

    ContactMainController contactMainController;
    NotificationController notificationController;



    MessageChatController messageChatController;
    MessagePage messagePage;

    MainController mainController;


    public static boolean running=true;
    public CallBackClientImp() throws RemoteException{

    }
    public void setMessage22Controller(MessageChatController messageChatController) {
        this.messageChatController = messageChatController;
    }
    public void setMessagePage(MessagePage messagePage) {
        this.messagePage = messagePage;
    }



//    public CallBackClientImp(MessagePage   messagePage) throws RemoteException {
//
//            this.messagePage = messagePage;
//
//    }

    public void setContactMainController(ContactMainController contactMainController) {
        this.contactMainController = contactMainController;
    }
    public void setNotificationController(NotificationController notificationController){
        this.notificationController = notificationController;
    }



    public void receiveMsg(MessageDTO messageDTO) throws Exception {
//        System.out.println("receiveMsg: "+msg);
////      //  System.out.println("sender: "+senderPhoneNumber);
        boolean done;
        System.out.println("this is receive message");
        System.out.println(messageDTO.getMessageContent());
        System.out.println(messageDTO.getIsAttachment());
        if (messageDTO.getIsAttachment()) {
            System.out.println("this is an attachment");
            done =  messageChatController.receiveAttachment(messageDTO);
        } else{
            done = messageChatController.receiveMessage(messageDTO);
        }

        if(!done){
            this.notification("New Message from " + messageDTO.getSenderID() + ":" + messageDTO.getMessageContent());
        }

    }


    @Override
    public void notification(String msg)  {
        Platform.runLater(() -> {
            try {
                Notifications notificationBuilder = Notifications.create()
                        .title("New Notification")
                        .text(msg)
                        .graphic(null)
                        .hideAfter(Duration.seconds(5))
                        .position(Pos.BOTTOM_RIGHT)
                        .onAction((event) -> {
                            System.out.println("Notification clicked");
                        });

                notificationBuilder.show();
                notificationSound();

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void updateContactList() throws RemoteException {
        System.out.println("updateContactList in call back client imp : ");

            if (contactMainController != null) {
                Platform.runLater(contactMainController::updateContactList);
            }

            // After updateContactList() finishes execution, call updateList()
            if (messagePage != null) {
                Platform.runLater(messagePage::updateList);
            }


    }

    @Override
    public void serveStandUp(){
        System.out.println("Stand up comedy");
        running = true;
    }
    @Override
    public void serverShutdownMessage() {
        running = false;
        Platform.runLater(() -> {
            AtomicInteger i = new AtomicInteger(10);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("Close the Application");
            alert.setContentText("The server is shutting down, and the App will close after " + i.get() + " seconds");

            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
                if (i.get() != 0) {
                    i.decrementAndGet();
                    alert.setContentText("The server is shutting down, and the App will close after " + i.get() + " seconds");
                } else {
                    alert.close();
                    Platform.exit();
                    System.exit(0);
                }
            }));
            timeline.setCycleCount(11);
            timeline.play();

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                timeline.stop();
                Platform.exit();
                System.exit(0);
            }
        });
    }

    @Override
    public void announce(String title, String msg) throws RemoteException {
        Platform.runLater(()->{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(title);
            alert.setHeaderText(msg);
            alert.showAndWait();
        });
    }



    private  void login( ) {
        GridPane pane  = PaneLoaderFactory.authContainerPane().getKey();
        AuthContainerController authContainerController = PaneLoaderFactory.authContainerPane().getValue();

        pane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        pane.setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);


       // Node source = (Node) event.getSource();
      Stage stage = StageUtils.getMainStage();
      stage.setScene(new Scene(pane));
    }


    public void receiveNotification() throws RemoteException{
        mainController = PaneLoaderFactory.getInstance().getMainController();
        Platform.runLater(()-> {
            if(notificationController != null)
                notificationController.updateNotificationList();
            mainController.setNewNotification();
        });
    }

    @Override
    public void onContactStatusChanged(String contactName, UserStatus userStatus) throws RemoteException {
        if(userStatus == UserStatus.Online){
            notification(contactName + " is online");

        } else if (userStatus== UserStatus.Offline) {
            notification(contactName + " is offline");

        }
    }

    private  void notificationSound(){
        String mediaUrl = getClass().getResource("/media/Notification.mp3").toExternalForm();
        //Media media = new Media(mediaUrl);
        Media media = new Media(mediaUrl);
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
    }



}



