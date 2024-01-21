package org.example.controller.FXMLController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import org.example.DTOs.UserDTO;
import org.example.service.UserAuthService;

import java.io.IOException;

public class UserLoginController {
    private final UserAuthService userAuthService;

    public UserLoginController() {
        this.userAuthService = new UserAuthService();
    }


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


    @FXML
    protected void onBtnDontHaveAccountClicked() throws IOException {
        AuthContainerController authContainerController = AuthContainerController.getInstance();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/SignUp.fxml"));
        Pane signupPane = loader.load();
        authContainerController.switchToPane(signupPane);


    }
}
