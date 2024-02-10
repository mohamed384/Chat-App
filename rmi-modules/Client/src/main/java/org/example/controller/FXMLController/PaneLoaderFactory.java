package org.example.controller.FXMLController;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.*;
import javafx.util.Pair;
import org.controlsfx.control.HiddenSidesPane;
import org.example.CallBackImp.CallBackClientImp;

import java.io.IOException;

public class PaneLoaderFactory {

    private static BorderPane mainBorderPane;

    private static CallBackClientImp callBackClient;

    public static ContactMainController contactMainController;
    public static MessagePage messagePage;

    private static PaneLoaderFactory instance;

    private static MessageChatController messageChatController;

    private static NotificationController notificationController;

    private static MainController mainController;

    private static UserProfileController userProfileController;

    private PaneLoaderFactory(){
    }


    public static PaneLoaderFactory getInstance(){
        if(instance==null){
            instance = new PaneLoaderFactory();
        }
        return instance;
    }
    public void setMainController(MainController mainControllerCopy) {
        mainController = mainControllerCopy;
    }
    public MainController getMainController() {
        return mainController;
    }
    public void setContactMainController(ContactMainController contactMainControllerCopy) {
        contactMainController = contactMainControllerCopy;
    }
    public void setMessage22Controller(MessageChatController message22){
        messageChatController = message22;
    }
    public MessageChatController getMessage22Controller(){
        return messageChatController;
    }

    public ContactMainController getContactMainController() {
        return contactMainController;
    }
    public void setMessagePage(MessagePage messagePageCopy) {
        messagePage = messagePageCopy;
    }
    public MessagePage getMessagePage() {
       return messagePage;
    }

    public void setNotificationController(NotificationController notificationControllerCopy) {
        notificationController = notificationControllerCopy;
    }
    public NotificationController getNotificationController(){
        return notificationController;
    }

    public void setUserProfileController(UserProfileController userProfileControllerCopy) {
        userProfileController = userProfileControllerCopy;
    }
    public UserProfileController getUserProfileController(){
        return userProfileController;
    }

    public static Pair<BorderPane, MessagePage> messagePageLoader(){
        FXMLLoader loader = new FXMLLoader(PaneLoaderFactory.class.getResource("/views/MessagePage.fxml"));
        BorderPane pane = null;
        try {
            pane = (BorderPane) loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        MessagePage controller = loader.getController();
        return new Pair<>(pane, controller);
    }



    public static Pair<BorderPane, ContactMainController> ContactPageLoader(){
        FXMLLoader loader = new FXMLLoader(PaneLoaderFactory.class.getResource("/views/ContactMain.fxml"));
        BorderPane pane = null;
        try {
            pane =  loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ContactMainController controller = loader.getController();
        return new Pair<>(pane, controller);
    }

    public static Pair<BorderPane, UserProfileController> profilePageLoader(){
        FXMLLoader loader = new FXMLLoader(PaneLoaderFactory.class.getResource("/views/UserProfile.fxml"));
        BorderPane pane = null;
        try {
            pane = (BorderPane) loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        UserProfileController controller = loader.getController();
        return new Pair<>(pane, controller);

    }


    public static void setMainBorderPane(BorderPane borderPane) {
        mainBorderPane = borderPane;
    }

    public static BorderPane getMainBorderPane() {
        return mainBorderPane;
    }

    public static void setCallBackClient(CallBackClientImp callBackClient) {
        callBackClient = callBackClient;
    }

    public static CallBackClientImp getCallBackClient() {
        return callBackClient;
    }





    public static Pair<BorderPane,NotificationController> notificationPageLoader(){
        FXMLLoader loader = new FXMLLoader(PaneLoaderFactory.class.getResource("/views/Notification.fxml"));
        BorderPane pane = null;
        try {
            pane = (BorderPane) loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        NotificationController controller = loader.getController();
        return new Pair<>(pane, controller);

    }

    public static Pair<BorderPane, UserLoginController> loginPageLoader(){
        FXMLLoader loader = new FXMLLoader(PaneLoaderFactory.class.getResource("/views/Login.fxml"));
        BorderPane pane = null;
        try {
            pane = (BorderPane) loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        UserLoginController controller = loader.getController();
        return new Pair<>(pane, controller);

    }



    public static Pair<Pane, UserSignUpController> signUpPageLoader(){
        FXMLLoader loader = new FXMLLoader(PaneLoaderFactory.class.getResource("/views/SignUp.fxml"));
        BorderPane pane = null;
        try {
            pane = (BorderPane) loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        UserSignUpController controller = loader.getController();
        return new Pair<>(pane, controller);

    }

    public static Pair<GridPane,AuthContainerController> authContainerPane(){
        FXMLLoader loader = new FXMLLoader(PaneLoaderFactory.class.getResource("/views/AuthConatiner.fxml"));
        GridPane pane = null;
        try {
            pane = (GridPane) loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        AuthContainerController controller = loader.getController();
        return new Pair<>(pane, controller);
    }

    public static Pair<HiddenSidesPane, MessageChatController> getmessage22Pane(){
        FXMLLoader loader = new FXMLLoader(PaneLoaderFactory.class.getResource("/views/MessageChat.fxml"));
        HiddenSidesPane pane = null;
        try {
            pane = (HiddenSidesPane) loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        MessageChatController controller = loader.getController();
        return new Pair<>(pane, controller);
    }

}
