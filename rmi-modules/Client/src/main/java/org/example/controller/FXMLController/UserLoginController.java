package org.example.controller.FXMLController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import org.example.DTOs.UserDTO;
import org.example.Utils.UserToken;
import org.example.service.UserAuthService;

import java.io.IOException;

public class UserLoginController {
    private final UserAuthService userAuthService;

    public UserLoginController() {
        this.userAuthService = new UserAuthService();
    }

    @FXML
    BorderPane bordePane;

    @FXML
    protected TextField phoneNumber;
    @FXML
    protected PasswordField password;
    @FXML
    protected Label btnDontHaveAccount;

    @FXML
    protected UserDTO login(ActionEvent actionEvent) throws IOException {
        return userAuthService.login(actionEvent, password, phoneNumber);
    }


    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber.setText(phoneNumber);
    }


    @FXML
    protected void onBtnDontHaveAccountClicked() throws IOException {
       // AuthContainerController authContainerController = AuthContainerController.getInstance();
       AuthContainerController authContainerController = AuthContainerController.getInstance();

        Pane signupPane = PaneLoaderFactory.signUpPageLoader().getKey();
        authContainerController.switchToPane(signupPane);


    }

}
