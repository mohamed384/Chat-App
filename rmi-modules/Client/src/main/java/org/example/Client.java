package org.example;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.example.CallBackImp.CallBackClientImp;
import org.example.Utils.StubContext;
import org.example.Utils.UserToken;
import org.example.controller.FXMLController.UtilsFX.StageUtils;
import org.example.interfaces.CallBackServer;
import org.example.interfaces.UserAuthentication;
import org.example.models.Enums.UserStatus;


import java.io.IOException;
import java.rmi.RemoteException;


public class Client extends Application {

    public static void main(String[] args) {
        launch();
        System.exit(0);


    }




    @Override
    public void start(Stage stage) throws Exception {


            // Load the FXML file for the start screen
        Parent startScreenParent = FXMLLoader.load(getClass().getResource("/views/StartScreen.fxml"));
        Scene startScreenScene = new Scene(startScreenParent);

        stage.setScene(startScreenScene);
        stage.show();

        stage.setOnCloseRequest(event -> {
            CallBackServer  callBackServer = null;
            try {
                if(CallBackClientImp.running){
                    callBackServer = (CallBackServer) org.example.Utils.StubContext.getStub("CallBackServerStub");
                }else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("the server is not running");
                    alert.setContentText("please try again later");
                }

                if(UserToken.getInstance().getUser() != null && callBackServer != null) {
                    UserAuthentication remoteObject = (UserAuthentication) StubContext.getStub("UserAuthenticationStub");
                    UserToken.getInstance().getUser().setUserStatus(UserStatus.Offline);
                    remoteObject.logout(UserToken.getInstance().getUser());
                    remoteObject.updateUser(UserToken.getInstance().getUser());
                    callBackServer.logout( UserToken.getInstance().getUser().getPhoneNumber());
                    callBackServer.notifyStatusUpdate(UserToken.getInstance().getUser());
                }
            } catch (RemoteException e) {

                throw new RuntimeException(e);

            }
            Platform.exit();
            System.exit(0);
        });
            // Create a pause transition of 2 seconds
        PauseTransition pause = new PauseTransition(Duration.seconds(1));

            // Set the action to be executed after the pause
        pause.setOnFinished(event ->
            {
                try {
                    // Load the FXML file for the main screen
                    Parent mainScreenParent = FXMLLoader.load(getClass().getResource("/views/AuthConatiner.fxml"));
                    Scene mainScreenScene = new Scene(mainScreenParent);

                    // Set the scene of the primary stage to the main screen
                    stage.setScene(mainScreenScene);

                } catch (IOException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("the server is not running");
                    alert.setContentText("please try again later");
                    e.printStackTrace();
                }
            });

        stage.setTitle("Cypher App");
        StageUtils.setMainStage(stage);
            // Start the pause
                pause.play();
        }
















        /*
        FXMLLoader fxmlLoader = new FXMLLoader(Client.class.getResource("/views/AuthConatiner.fxml"));

        Scene scene = new Scene(fxmlLoader.load());

        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show()
         */
    }
