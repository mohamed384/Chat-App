package org.example.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface CallBackServer extends Remote {

    public boolean login(String phoneNumber , CallBackClient callBackClient) throws RemoteException;

    public boolean logout(String phoneNumber , CallBackClient callBackClient) throws RemoteException ;

    public void sendMsg(String msg , String senderPhoneNumber , String receiverPhoneNumber) throws RemoteException;



}
