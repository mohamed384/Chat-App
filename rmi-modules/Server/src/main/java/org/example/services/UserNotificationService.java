package org.example.services;

import org.example.DAO.UserNotificationDAOImpl;
import org.example.models.UserNotification;

public class UserNotificationService {
    private final UserNotificationDAOImpl userNotificationDAO;
    //private UserMapper userMapper = UserMapper.INSTANCE;

    public UserNotificationService() {
        this.userNotificationDAO = new UserNotificationDAOImpl();
    }
    public boolean sendNotification(String sender, String receiver) {
        UserNotification existingNotification =
                userNotificationDAO.findBySenderAndReceiver(sender, receiver);

        if (existingNotification != null) {
            System.out.println("Notification already exists.");
            return false;
        }

        UserNotification userNotification = new UserNotification();
        userNotification.setSenderID(sender);
        userNotification.setReceiverID(receiver);
        return userNotificationDAO.create(userNotification);
    }

}
