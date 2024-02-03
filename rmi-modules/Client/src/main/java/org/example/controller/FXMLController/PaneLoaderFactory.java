package org.example.controller.FXMLController;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.*;
import javafx.util.Pair;
import org.controlsfx.control.HiddenSidesPane;

import java.io.IOException;

public class PaneLoaderFactory {
    public static ContactMainController contactMainController;
    public static MessagePage messagePage;

    private static PaneLoaderFactory instance;

    private static Message22Controller message22Controller;

    private PaneLoaderFactory(){
    }

    public static PaneLoaderFactory getInstance(){
        if(instance==null){
            instance = new PaneLoaderFactory();
        }
        return instance;
    }
    public void setContactMainController(ContactMainController contactMainControllerCopy) {
        contactMainController = contactMainControllerCopy;
    }
    public void setMessage22Controller(Message22Controller message22){
        message22Controller = message22;
    }
    public Message22Controller getMessage22Controller(){
        return message22Controller;
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



    public static Pair<Pane,USerSignUpController> signUpPageLoader(){
        FXMLLoader loader = new FXMLLoader(PaneLoaderFactory.class.getResource("/views/SignUp.fxml"));
        BorderPane pane = null;
        try {
            pane = (BorderPane) loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        USerSignUpController controller = loader.getController();
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

    public static Pair<BorderPane,Message22Controller > getmessage22Pane(){
        FXMLLoader loader = new FXMLLoader(PaneLoaderFactory.class.getResource("/views/Message22.fxml"));
        BorderPane pane = null;
        try {
            pane = (BorderPane) loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Message22Controller controller = loader.getController();
        return new Pair<>(pane, controller);
    }

}
