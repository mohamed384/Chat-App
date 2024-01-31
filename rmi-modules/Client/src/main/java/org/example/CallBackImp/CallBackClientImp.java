package org.example.CallBackImp;

import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import org.example.Utils.UserToken;
import org.example.controller.FXMLController.MessagePage;
import org.example.interfaces.CallBackClient;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class CallBackClientImp extends UnicastRemoteObject implements CallBackClient {

    UserToken userToken = UserToken.getInstance();
    MessagePage messagePage;

    public CallBackClientImp(MessagePage   messagePage) throws RemoteException {

        this.messagePage = messagePage;

    }



    @Override
    public void receiveMsg(String msg, String senderPhoneNumber ) throws Exception {
        System.out.println("receiveMsg: "+msg);
        System.out.println("sender: "+senderPhoneNumber);

         messagePage.receiveMessage(msg);

    }

    /*
    public void notification(String msg) throws Exception {
        Notifications.create()
                .darkStyle()
                .title("New Message")
                .text(msg)
                .graphic(new Rectangle(600, 400, Color.RED)) // sets node to display
                .hideAfter(Duration.seconds(10))
                .show();
    }

     */


}
