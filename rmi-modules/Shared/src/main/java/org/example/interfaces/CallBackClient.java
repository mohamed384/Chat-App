package org.example.interfaces;

import org.example.DTOs.MessageDTO;
import org.example.DTOs.UserDTO;
import org.example.models.Enums.UserStatus;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface CallBackClient extends Remote {

    public void receiveMsg(MessageDTO messageDTO) throws Exception;
    public void serverShutdownMessage() throws Exception;
    public void notification(String msg) throws Exception;
    public void announce( String title ,  String msg) throws Exception;
    public void serveStandUp() throws Exception;
    public void updateContactList() throws Exception;

     void receiveNotification() throws RemoteException;
      void onContactStatusChanged(String contactName, UserStatus userStatus) throws RemoteException;
}
