package org.example.controller;

import org.example.services.UserNotificationService;
import org.example.interfaces.UserSendNotification;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class UserNotificationController extends UnicastRemoteObject implements UserSendNotification {
    private final UserNotificationService userNotificationService;
    public UserNotificationController() throws RemoteException {
        this.userNotificationService = new UserNotificationService();
    }

    @Override
    public boolean sendNotification(String sender, String receiver) throws RemoteException {
        return userNotificationService.sendNotification(sender, receiver);
    }
}
