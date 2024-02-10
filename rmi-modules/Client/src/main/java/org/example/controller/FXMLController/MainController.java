package org.example.controller.FXMLController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.*;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import org.example.CallBackImp.CallBackClientImp;
import org.example.DTOs.UserDTO;
import org.example.Utils.StubContext;
import org.example.Utils.UserToken;
import org.example.controller.FXMLController.interfaces.Observer;
import org.example.interfaces.CallBackServer;
import org.example.interfaces.UserAuthentication;
import org.example.models.Enums.UserStatus;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainController implements Initializable, Observer {

    public VBox nav;
    @FXML
    private Circle userProfile;
    @FXML
    private ImageView newNotification;
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


    public MainController() {

        notifySound( "/media/Opening.mp3");



        try {
            callBackServer = (CallBackServer) StubContext.getStub("CallBackServerStub");

            System.out.println(callBackServer);
            callBackClient = new CallBackClientImp();

            String number = UserToken.getInstance().getUser().getPhoneNumber();
            System.out.println(number);

            callBackServer.login(number, callBackClient);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
       }
        PaneLoaderFactory.setMainBorderPane(borderPane);
        PaneLoaderFactory.setCallBackClient(callBackClient);

    }
    public BorderPane getBorderPane() {
        return borderPane;
    }

    public void setNewNotification() {
        newNotification.setVisible(true);
    }

    @FXML
    public void goToProfile(MouseEvent event) {

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
        newNotification.setVisible(false);

        BorderPane pane = PaneLoaderFactory.notificationPageLoader().getKey();
        //NotificationController controller = PaneLoaderFactory.notificationPageLoader().getValue();
        //controller.setCallBackServer(callBackServer);
        NotificationController controller = PaneLoaderFactory.getInstance().getNotificationController();
        callBackClient.setNotificationController(controller);
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
        if (result.isPresent() && result.get() == ButtonType.OK) {


            GridPane pane = PaneLoaderFactory.authContainerPane().getKey();

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
                callBackServer.logout(number);
                callBackServer.notifyStatusUpdate(UserToken.getInstance().getUser());


               notifySound( "/media/log_off.mp3");

            } catch (RemoteException e) {
                e.printStackTrace();
            }

        }

    }

    public void notifySound(String url){
        String mediaUrl = getClass().getResource(url).toExternalForm();
        Media media = new Media(mediaUrl);
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Image image = new Image(new ByteArrayInputStream(UserToken.getInstance().getUser().getPicture()));
        userProfile.setFill(new ImagePattern(image));
        
        newNotification.setVisible(false);
        PaneLoaderFactory.getInstance().setMainController(this);
        gradient = new LinearGradient(
                0, 0, 1, 1, true, CycleMethod.NO_CYCLE,
                new Stop(0, Color.web("#6639a6")),
                new Stop(1, Color.web("#9b75d0"))
        );
        //nav.setBackground(new Background(new BackgroundFill(gradient, CornerRadii.EMPTY, Insets.EMPTY)));
    }

    @Override
    public void update(UserDTO user) {
        Image image = new Image(new ByteArrayInputStream(user.getPicture()));
        userProfile.setFill(new ImagePattern(image));
    }


}
