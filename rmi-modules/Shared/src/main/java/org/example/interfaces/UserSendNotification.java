package org.example.interfaces;


import java.rmi.Remote;
import java.rmi.RemoteException;

public interface UserSendNotification extends Remote {
    boolean sendNotification(String sender, String receiver) throws RemoteException;

}
