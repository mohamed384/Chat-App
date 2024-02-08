package org.example.service;


//import javafx.embed.swing.SwingFXUtils;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import org.example.CallBackImp.CallBackClientImp;
import org.example.DTOs.UserDTO;
import org.example.Utils.CheckFiledValidation;
import org.example.Utils.Enum.ValidationTypes;
import org.example.Utils.StubContext;
import org.example.Utils.UserCash;
import org.example.Utils.UserToken;
import org.example.interfaces.CallBackServer;
import org.example.interfaces.UserAuthentication;
import org.example.models.Enums.UserMode;
import org.example.models.Enums.UserStatus;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;


public class UserAuthService {

    CallBackServer callBackServer;
    CallBackClientImp callBackClient;
    public UserAuthService() {
        //TODO Error Here check it please by running client without server Exception thrown from here
        if(CallBackClientImp.running) {
           callBackServer = (CallBackServer) StubContext.getStub("CallBackServerStub");
       }else {
           Alert alert = new Alert(Alert.AlertType.ERROR);
           alert.setTitle("Error");
           alert.setHeaderText("the server is not running");
           alert.setContentText("please try again later..");
       }

    }

    public byte[] convertImageToByteArray(Image image) {
        try {
            BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "png", baos);
            return baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }



    protected void switchToMainPage(ActionEvent actionEvent) {

        Parent newScreenParent;
        try {
            newScreenParent = FXMLLoader.load(getClass().getResource("/views/MainPage.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Scene newScreenScene = new Scene(newScreenParent);

        Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        currentStage.setScene(newScreenScene);

        currentStage.show();


    }



    private UserAuthentication UserAuthRemoteObject() {
        UserAuthentication remoteObject = null;
        try {
            if(CallBackClientImp.running) {
                remoteObject = (UserAuthentication) StubContext.getStub("UserAuthenticationStub");
            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("the server is not running");
                alert.setContentText("please try again later");
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("the server is not running");
            alert.setContentText("please try again later");

           // e.printStackTrace();
        }
        return remoteObject;
    }

    public UserDTO login(ActionEvent actionEvent, PasswordField PasswordLog, TextField PhoneLog) throws IOException {
        if (!CallBackClientImp.running) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("the server is not running");
            alert.setContentText("please try again later");

            return null;
        }
        String password = PasswordLog.getText();
        String phone = PhoneLog.getText();
        UserAuthentication remoteObject = UserAuthRemoteObject();


        UserDTO user;
        if (remoteObject != null) {
            user = remoteObject.login(phone, password);

            callBackServer = (CallBackServer) StubContext.getStub("CallBackServerStub");
            boolean userOnlient = callBackServer.isOnline(phone);

            if (user != null && !userOnlient) {
                UserToken userToken = UserToken.getInstance();
                userToken.setUser(user);
                switchToMainPage(actionEvent);
                UserToken.getInstance().getUser().setUserStatus(UserStatus.Online);
                System.out.println("login from client auth service is successfully done" + UserToken.getInstance().getUser().getUserStatus());
                //UserToken.getInstance().getUser().setUserMode(UserMode.Available);
                remoteObject.updateUser(UserToken.getInstance().getUser());

                callBackServer.notifyStatusUpdate(UserToken.getInstance().getUser());

            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Login Failed");
                alert.setHeaderText(null);
                if(userOnlient){
                    alert.setContentText("These Account already Login.. :)");
                }else {
                    alert.setContentText("Invalid phone number or password");
                }
                alert.showAndWait();
                PhoneLog.getStyleClass().remove("custom-text-field");
                PhoneLog.getStyleClass().add("not-valid-text-field");
                PasswordLog.getStyleClass().remove("custom-text-field");
                PasswordLog.getStyleClass().add("not-valid-text-field");
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Server Error");
            alert.setHeaderText(null);
            alert.setContentText("Server is not available now, please try again later.");
            alert.showAndWait();
            return null;
        }


//        PasswordLog.setText("");
//        PhoneLog.setText("");
        return user;
    }



    public Boolean signup(ActionEvent actionEvent, TextField phoneSignUp, TextField nameSignUp,
                       TextField emailSignUp, String selectedCountry, DatePicker birthDateSignUp
            , PasswordField passwordSignUp, PasswordField passwordConfirmationSignUp, ImageView imageSignUp,
                       String selectedGender, Label phoneValidLabel, Label nameValidLabel,
                       Label emailValidLabel, Label passwordValidLabel, Label confirmPassValidLabel, Label counrtyValidLabel) throws IOException {

        String phone = phoneSignUp.getText();

        String name = nameSignUp.getText();

        String email = emailSignUp.getText();

        LocalDate selectedLocalDate = birthDateSignUp.getValue();
        Date birthDate = Date.from(selectedLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());


        String password = passwordSignUp.getText();

        String passwordConfirm = passwordConfirmationSignUp.getText();

        byte[] image = convertImageToByteArray(imageSignUp.getImage());


        boolean isValidPhone = CheckFiledValidation.checkFiledValidation(phone, phoneValidLabel, phoneSignUp, ValidationTypes.phone);
        boolean isValidName = CheckFiledValidation.checkFiledValidation(name, nameValidLabel, nameSignUp, ValidationTypes.name);
        boolean isValidEmail = CheckFiledValidation.checkFiledValidation(email, emailValidLabel, emailSignUp, ValidationTypes.email);
        boolean isValidPassword = CheckFiledValidation.checkFiledValidation(password, passwordValidLabel, passwordSignUp, ValidationTypes.password);
        if (selectedCountry == null) {
            counrtyValidLabel.getStyleClass().remove("valid-label");
            counrtyValidLabel.getStyleClass().add("not-valid-label");
            return false;
        } else {
            counrtyValidLabel.getStyleClass().add("valid-label");
            counrtyValidLabel.getStyleClass().remove("not-valid-label");
        }
        if (isValidPassword) {
            if (!passwordConfirm.equals(password)) {
                confirmPassValidLabel.getStyleClass().remove("valid-label");
                confirmPassValidLabel.getStyleClass().add("not-valid-label");
                passwordConfirmationSignUp.getStyleClass().remove("custom-text-field");
                passwordConfirmationSignUp.getStyleClass().add("not-valid-text-field");
                return false;


            } else {
                confirmPassValidLabel.getStyleClass().add("valid-label");
                confirmPassValidLabel.getStyleClass().remove("not-valid-label");
                passwordConfirmationSignUp.getStyleClass().add("custom-text-field");
                passwordConfirmationSignUp.getStyleClass().remove("not-valid-text-field");
            }
        }
        if (!(isValidPhone && isValidName && isValidEmail && isValidPassword)) {
            return false;
        }


        UserAuthentication remoteObject = UserAuthRemoteObject();
        if (remoteObject != null) {
            UserDTO user1 = new UserDTO(phone, name, email, password, passwordConfirm, selectedGender, selectedCountry,
                    birthDate, "", UserStatus.Online, UserMode.Available, image);
            if(!remoteObject.signup(user1)) return false;
            UserToken userToken = UserToken.getInstance();
            userToken.setUser(user1);
            System.out.println("signup from client auth service is successfully done");

            callBackServer = (CallBackServer) StubContext.getStub("CallBackServerStub");
            callBackServer.notifyStatusUpdate(UserToken.getInstance().getUser());


            switchToMainPage(actionEvent);
            return true;

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Server Error");
            alert.setHeaderText(null);
            alert.setContentText("Server is not available now, please try again later.");
            alert.showAndWait();
            return false;
        }


    }
}
