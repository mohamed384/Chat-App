package org.example.controller.implementations;

import org.example.controller.services.UserNotificationService;
import org.example.controller.services.UserService;
import org.example.interfaces.UserContact;
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
