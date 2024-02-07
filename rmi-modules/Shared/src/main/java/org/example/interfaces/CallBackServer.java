package org.example.interfaces;

import org.example.DTOs.MessageDTO;
import org.example.DTOs.UserDTO;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface CallBackServer extends Remote {

    public boolean login(String phoneNumber , CallBackClient callBackClient) throws RemoteException;

    public boolean logout(String phoneNumber) throws RemoteException ;

    public void sendMsg(MessageDTO messageDTO) throws RemoteException;

   // public  void sendFile(byte[] file , String senderPhoneNumber , String receiverPhoneNumber) throws RemoteException;

  //  public void chatBot( String message, String senderPhoneNumber , String receiverPhoneNumber) throws RemoteException;

    void serverStandUpMessage() throws RemoteException;
    public  void logoutAll() throws RemoteException;

    public void updateContactList(String phoneNumber) throws RemoteException;
    public void notifyStatusUpdate(UserDTO userDTO) throws RemoteException;
    public boolean isOnline(String clientPhoneNumber) throws  RemoteException;

    public void sendNotificationCallBack(String senderPhoneNumber, String receiverPhoneNumber) throws RemoteException;


}
