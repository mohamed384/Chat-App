package org.example.callBackImp;

import org.example.interfaces.CallBackClient;
import org.example.interfaces.CallBackServer;
//import org.example.utils.ChatBot;

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
        try {
            callBackClient.notification("Welcome to CyberChat App");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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

        System.out.println("sendMsg: "+ msg);
        System.out.println("sender: "+ senderPhoneNumber);
        System.out.println("receiver: " +receiverPhoneNumber);

        CallBackClient callBackClient = clients.get(receiverPhoneNumber);

        try {
            callBackClient.notification( "You have a new message from "+senderPhoneNumber);
            callBackClient.receiveMsg(msg, senderPhoneNumber);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void sendAnnouncement(String title,  String msg) {
        for (String phoneNumber : clients.keySet()) {
            try {
                clients.get(phoneNumber).announce(title, msg);
            } catch (RemoteException e) {
                e.printStackTrace();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }


    @Override
    public  void logoutAll() {

        for (String phoneNumber : clients.keySet()) {
            try {

                clients.get(phoneNumber).serverShoutdownMessage();
                logout(phoneNumber, clients.get(phoneNumber));
            } catch (RemoteException e) {
                e.printStackTrace();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }


}

