package org.example.controller.FXMLController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import org.example.DTOs.UserDTO;
import org.example.Utils.UserCash;
import org.example.service.UserAuthService;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UserLoginController implements Initializable {
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
        UserDTO user = userAuthService.login(actionEvent, password, phoneNumber);
        if (user != null) {
            UserCash.saveUser(phoneNumber.getText(), password.getText());
        }
        return user;
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


    @FXML
    void initialize() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        String[] credentials = UserCash.loadUser();
        if (credentials != null) {
            String username = credentials[0];
            String password = credentials[1];
            phoneNumber.setText(username);
            this.password.setText(password);
        } else {
            System.out.println("No cached credentials found.");
        }

    }
}
