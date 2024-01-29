package org.example.controller.FXMLController;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.util.Pair;
import org.controlsfx.control.HiddenSidesPane;

import java.io.IOException;

public class PaneLoaderFactory {

    public static Pair<HiddenSidesPane, MessagePage> messagePageLoader(){
        FXMLLoader loader = new FXMLLoader(PaneLoaderFactory.class.getResource("/views/MessagePage.fxml"));
        HiddenSidesPane pane = null;
        try {
            pane = (HiddenSidesPane) loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        MessagePage controller = loader.getController();
        return new Pair<>(pane, controller);
    }


    public static Pair<BorderPane, SearchController> ContactPageLoader(){
        FXMLLoader loader = new FXMLLoader(PaneLoaderFactory.class.getResource("/views/ContactList.fxml"));
        BorderPane pane = null;
        try {
            pane = (BorderPane) loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        SearchController controller = loader.getController();
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

}