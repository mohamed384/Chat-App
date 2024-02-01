package org.example.controller.FXMLController;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.util.Pair;
import org.example.Utils.UserToken;
import org.example.interfaces.CallBackServer;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AuthContainerController implements Initializable {
    private static AuthContainerController instance;

    public static AuthContainerController getInstance() {
        return instance;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        instance = this;
        if (UserToken.getInstance() != null && UserToken.getInstance().getUser() != null) {
            // If the user is already logged in, load the main page
            loadLoginPane(UserToken.getInstance().getUser().getPhoneNumber());
        } else {
            // If the user is not logged in, load the login page
            loadLoginPane();
        }
    }

    @FXML
    private BorderPane mainBorderPane;

    private void loadLoginPane() {

        BorderPane loginPane = PaneLoaderFactory.loginPageLoader().getKey();
        mainBorderPane.setCenter(loginPane);

    }

    private void loadLoginPane(String phoneNumber) {

        Pair<BorderPane, UserLoginController> pair = PaneLoaderFactory.loginPageLoader();
        BorderPane loginPane = pair.getKey();
        UserLoginController controller = pair.getValue();

        controller.setPhoneNumber(phoneNumber);

        mainBorderPane.setCenter(loginPane);

    }

    public void switchToPane(Pane signupPane) {
        mainBorderPane.setCenter(signupPane);
    }




}
