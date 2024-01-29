package org.example.CallBackImp;

import javafx.application.Platform;
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


}
