package org.example.controller.FXMLController;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.Pair;
import org.controlsfx.control.HiddenSidesPane;
import org.controlsfx.control.Notifications;
import org.example.CallBackImp.CallBackClientImp;
import org.example.Utils.StubContext;
import org.example.Utils.UserToken;
import org.example.interfaces.CallBackClient;
import org.example.interfaces.CallBackServer;
import org.example.interfaces.UserAuthentication;
import org.example.models.Enums.UserStatus;

import javax.management.Notification;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    public VBox nav;
    @FXML
    private Button contact;

    @FXML
    private Button message;

    @FXML
    private Button notification;

    @FXML
    private Button profile;

    @FXML
    private BorderPane borderPane;

    CallBackServer callBackServer;
    CallBackClientImp callBackClient;
    LinearGradient gradient;




    public MainController(){

       try {
            callBackServer = (CallBackServer) Naming.lookup("rmi://localhost:1099/CallBackServerStub");

           System.out.println(callBackServer);
           callBackClient = new CallBackClientImp();

            String number = UserToken.getInstance().getUser().getPhoneNumber();
            System.out.println(number);

            callBackServer.login(number, callBackClient);
       } catch (RemoteException | NotBoundException | MalformedURLException e) {
            throw new RuntimeException(e);
       }



    }

    @FXML
    public void goToProfile(ActionEvent event) {

            BorderPane pane = PaneLoaderFactory.profilePageLoader().getKey();
            pane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
            pane.setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
            borderPane.setCenter(null);
            BorderPane.setAlignment(pane, Pos.CENTER);
            BorderPane.setMargin(pane, new Insets(0, 0, 0, 0));
            borderPane.setCenter(pane);

    }

    @FXML
    public void goToMessage(ActionEvent event) {

        ///
        BorderPane pane = PaneLoaderFactory.messagePageLoader().getKey();
        MessagePage page = PaneLoaderFactory.getInstance().getMessagePage();

        page.setCallBackClient(callBackClient);
        callBackClient.setMessagePage(page);


       // MessagePage messagePage = PaneLoaderFactory.messagePageLoader().getValue();
        //messagePage.setCallBackClient(callBackClient);
        //messagePage.setCallBackServer(callBackServer);
       //callBackClient.setMessagePage(PaneLoaderFactory.getInstance().getMessagePage());

        pane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        pane.setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        borderPane.setCenter(null);
        BorderPane.setAlignment(pane, Pos.CENTER);
        BorderPane.setMargin(pane, new Insets(0, 0, 0, 0));
        borderPane.setCenter(pane);


    }

    public void getContactMainController(ContactMainController contactMainController) {
        callBackClient.setContactMainController(contactMainController);
    }

    @FXML
    public void goToContact(ActionEvent event) {

            BorderPane pane = PaneLoaderFactory.ContactPageLoader().getKey();
          //  ContactMainController controller = PaneLoaderFactory.ContactPageLoader().getValue();
            callBackClient.setContactMainController(PaneLoaderFactory.getInstance().getContactMainController());
           // System.out.println(PaneLoaderFactory.getInstance().getContactMainController());

           // callBackClient.setContactMainController(controller);

            pane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
            pane.setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
            borderPane.setCenter(null);
            borderPane.setCenter(pane);

    }

    @FXML
    public void goToNotification(ActionEvent event) {

            BorderPane pane = PaneLoaderFactory.notificationPageLoader().getKey();
            //NotificationController controller = PaneLoaderFactory.notificationPageLoader().getValue();
            //controller.setCallBackServer(callBackServer);
            pane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
            pane.setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
            borderPane.setCenter(null);
            borderPane.setCenter(pane);

    }


    @FXML
    public void goToLogOut(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Close the Aplication");
        alert.setContentText("Are you sure you want to close the Cypher?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK){


            GridPane pane  = PaneLoaderFactory.authContainerPane().getKey();

            pane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
            pane.setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);


            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();

            stage.setScene(new Scene(pane));


            try {
                String number = UserToken.getInstance().getUser().getPhoneNumber();
                UserAuthentication remoteObject = (UserAuthentication) StubContext.getStub("UserAuthenticationStub");
                UserToken.getInstance().getUser().setUserStatus(UserStatus.Offline);
                remoteObject.logout(UserToken.getInstance().getUser());
                remoteObject.updateUser(UserToken.getInstance().getUser());
                callBackServer.logout( number);
                callBackServer.notifyStatusUpdate(UserToken.getInstance().getUser());

            } catch (RemoteException e) {
                e.printStackTrace();
            }

        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gradient = new LinearGradient(
                0, 0, 1, 1, true, CycleMethod.NO_CYCLE,
                new Stop(0, Color.web("#6639a6")),
                new Stop(1, Color.web("#9b75d0"))
        );
        //nav.setBackground(new Background(new BackgroundFill(gradient, CornerRadii.EMPTY, Insets.EMPTY)));
    }
}
