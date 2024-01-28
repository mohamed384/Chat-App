package org.example.interfaces;


import org.example.DTOs.NotificationDto;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface UserSendNotification extends Remote {
    boolean sendNotification(String sender, String receiver) throws RemoteException;
    List<NotificationDto> receiveNotification(String phoneNumber) throws RemoteException;

}
