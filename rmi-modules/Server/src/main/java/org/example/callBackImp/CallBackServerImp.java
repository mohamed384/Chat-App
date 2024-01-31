package org.example.callBackImp;

import org.example.interfaces.CallBackClient;
import org.example.interfaces.CallBackServer;
import org.example.utils.ChatBot;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

public class CallBackServerImp extends UnicastRemoteObject implements CallBackServer , Serializable {

      static Map<String, CallBackClient> clients = new HashMap<String, CallBackClient>();

    public static int getClients() {
        return clients.size();
    }

    public CallBackServerImp() throws RemoteException {
    }
    @Override
    public boolean login(String phoneNumber, CallBackClient callBackClient) {
            System.out.println("login: "+phoneNumber);
            clients.put(phoneNumber, callBackClient);

        return true;
    }

    @Override
    public boolean logout(String phoneNumber, CallBackClient callBackClient) {

            System.out.println("logout: "+phoneNumber);
            clients.remove(phoneNumber);
        return true;
    }

    @Override
    public void sendMsg(String msg, String senderPhoneNumber, String receiverPhoneNumber) {

        System.out.println("sendMsg: "+msg);
        System.out.println("sender: "+senderPhoneNumber);
        System.out.println("receiver: " +receiverPhoneNumber);

        CallBackClient callBackClient = clients.get(receiverPhoneNumber);

        try {
            callBackClient.receiveMsg(msg, senderPhoneNumber);
          //  callBackClient.notification("You have a new message from "+senderPhoneNumber);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void chatBot( String message ,  String senderPhoneNumber , String receiverPhoneNumber){

        String msg = null;
        try {
            msg = ChatBot.chatBotMessege(message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println("sendMsg: "+message);
        System.out.println("sender: "+senderPhoneNumber);
        System.out.println("receiver: " +receiverPhoneNumber);


        CallBackClient callBackClient = clients.get(receiverPhoneNumber);

        try {
            callBackClient.receiveMsg(msg, "ChatBot");
            //  callBackClient.notification("You have a new message from "+senderPhoneNumber);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
