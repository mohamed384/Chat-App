package org.example.CallBackImp;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import org.example.Utils.UserToken;
import org.example.controller.FXMLController.AuthContainerController;
import org.example.controller.FXMLController.MainController;
import org.example.controller.FXMLController.MessagePage;
import org.example.controller.FXMLController.PaneLoaderFactory;
import org.example.controller.FXMLController.UtilsFX.StageUtils;
import org.example.interfaces.CallBackClient;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class CallBackClientImp extends UnicastRemoteObject implements CallBackClient {

    UserToken userToken = UserToken.getInstance();
    AuthContainerController authContainerController = AuthContainerController.getInstance();



    MessagePage messagePage;

    public CallBackClientImp(MessagePage   messagePage) throws RemoteException {

        this.messagePage = messagePage;

    }



    @Override
    public void receiveMsg(String msg, String senderPhoneNumber ) throws Exception {
        System.out.println("receiveMsg: "+msg);
        System.out.println("sender: "+senderPhoneNumber);

         messagePage.receiveMessage(msg);

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

                notificationBuilder.darkStyle();
                notificationBuilder.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void serverShoutdownMessage() {

        Platform.runLater(() -> {
            AtomicInteger i = new AtomicInteger(10);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("Close the Application");
            alert.setContentText("The server is shutting down, and the App will close after " + i + " seconds");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                    login();
            }

            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
                if (i.get() > 0) {
                     i.decrementAndGet();

                     Platform.runLater(() -> {
                         alert.setContentText("The server is shutting down, and the App will close after " + i.get() + " seconds");
                     });
                } else {

                    login();

                }
            }));
            timeline.setCycleCount(10);
            timeline.play();
        });
    }

    private  void login( ) {
        GridPane pane  = PaneLoaderFactory.authContainerPane().getKey();

        pane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        pane.setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);


       // Node source = (Node) event.getSource();
      Stage stage = StageUtils.getMainStage();
      stage.setScene(new Scene(pane));
    }


}



