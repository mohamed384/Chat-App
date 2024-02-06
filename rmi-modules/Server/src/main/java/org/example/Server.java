package org.example;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.example.callBackImp.CallBackServerImp;
import org.example.controller.*;
import org.example.interfaces.*;
import org.example.utils.DBConnection;
import org.example.Utils.StubContext;

import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.Connection;


public class Server  extends  Application{

    public static void main(String[] args) {

         /*
        try {



            // Hena 3mlna el stubs bta3tna
            UserAuthentication userAuthenticationStub = new UserController();
            UserSendNotification userSendNotificationStub = new UserNotificationController();
            UserContact contactControllerStub = new ContactController();
            ChatRMI ChatControllerStub = new ChatController();
            CallBackServer callBackServer = new CallBackServerImp();
            GroupChatRMI groupChatRMI = new GroupChatController();
            MessageRMI messageRMI = new MessageController();

            // Hena 3mlna add lel stubs bta3tna
//            StubContext.addStub("UserAuthenticationStub", userAuthenticationStub);
//            StubContext.addStub("UserSendNotificationStub", userSendNotificationStub);
//            StubContext.addStub("UserContactStub", contactControllerStub);
//            StubContext.addStub("CallBackServer", callBackServer);

            // Hena 3mlna create lel registry w 3mlna rebind lel stubs bta3tna

            StubContext.addStub("ChatControllerStub", ChatControllerStub);
            StubContext.addStub("GroupChatControllerStub", groupChatRMI);
            StubContext.addStub("MessageControllerStub", messageRMI);

            java.rmi.Naming.rebind("UserAuthenticationStub", userAuthenticationStub);
            java.rmi.Naming.rebind("UserSendNotificationStub", userSendNotificationStub);
            java.rmi.Naming.rebind("UserContactStub", contactControllerStub);
                java.rmi.Naming.rebind("ChatControllerStub", ChatControllerStub);
            java.rmi.Naming.rebind("CallBackServerStub", callBackServer);

            // Hena 3mlna connect lel database
            Connection connection = DBConnection.getConnection();
            System.out.println("RMI Server is running...");

        }catch(RemoteException ex){
            ex.printStackTrace();

        } catch (Exception e) {
            e.printStackTrace();
        }

             */

            launch(args);

        System.exit(0);

    }



@Override
    public void start(Stage primaryStage) throws Exception {

        // Load the FXML file for the start screen
        Parent startScreenParent = FXMLLoader.load(getClass().getResource("/views/opening.fxml"));
        Scene startScreenScene = new Scene(startScreenParent);

        primaryStage.setScene(startScreenScene);
        primaryStage.show();


        PauseTransition pause = new PauseTransition(Duration.seconds(0.2));

        // Set the action to be executed after the pause
        pause.setOnFinished(event ->
        {
            try {
                // Load the FXML file for the main screen
                Parent mainScreenParent = FXMLLoader.load(getClass().getResource("/views/main.fxml"));
                Scene mainScreenScene = new Scene(mainScreenParent);

                // Set the scene of the primary stage to the main screen
                primaryStage.setScene(mainScreenScene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    primaryStage.setTitle("Server App");

        // Start the pause
        pause.play();
    }


}

