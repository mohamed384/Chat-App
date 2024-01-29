package org.example.controller;

import org.example.DTOs.NotificationDto;
import org.example.services.UserNotificationService;
import org.example.interfaces.UserSendNotification;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class UserNotificationController extends UnicastRemoteObject implements UserSendNotification {
    private final UserNotificationService userNotificationService;
    public UserNotificationController() throws RemoteException {
        this.userNotificationService = new UserNotificationService();
    }

    @Override
    public boolean sendNotification(String sender, String receiver) throws RemoteException {
        return userNotificationService.sendNotification(sender, receiver);
    }

    @Override
    public List<NotificationDto> receiveNotification(String phoneNumber) throws RemoteException {
        return userNotificationService.receiveNotification(phoneNumber);
    }

    @Override
    public boolean notificationExists(String senderId, String receiverId) throws RemoteException{
        return userNotificationService.notificationExists(senderId, receiverId);
    }

    @Override
    public boolean deleteNotification(String userID, String friendID) throws RemoteException {
        return userNotificationService.deleteNotification(userID,friendID);
    }

}
