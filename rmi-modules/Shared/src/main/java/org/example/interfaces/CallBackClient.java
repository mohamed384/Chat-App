package org.example.interfaces;

import org.example.DTOs.UserDTO;
import org.example.models.Enums.UserStatus;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface CallBackClient extends Remote {

     void receiveMsg(String msg , String senderPhoneNumber , int chtID  ) throws RemoteException;
     void serverShoutdownMessage() throws RemoteException;
     void notification(String msg) throws RemoteException;
     void announce( String title ,  String msg) throws RemoteException;

     void updateContactList() throws RemoteException;

     void receiveNotification() throws RemoteException;
      void onContactStatusChanged(String contactName, UserStatus userStatus) throws RemoteException;
}
