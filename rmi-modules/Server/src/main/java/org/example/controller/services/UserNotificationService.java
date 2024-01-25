package org.example.controller.services;

import org.example.controller.DAO.UserDAOImpl;
import org.example.controller.DAO.UserNotificationDAOImpl;
import org.example.controller.DAO.interfaces.UserNotificationDAO;
import org.example.models.Contact;
import org.example.models.Mapper.UserMapper;
import org.example.models.UserNotification;

public class UserNotificationService {
    private final UserNotificationDAOImpl userNotificationDAO;
    //private UserMapper userMapper = UserMapper.INSTANCE;

    public UserNotificationService() {
        this.userNotificationDAO = new UserNotificationDAOImpl();
    }
    public boolean sendNotification(String sender, String receiver) {
        UserNotification userNotification = new UserNotification();
        userNotification.setSenderID(sender);
        userNotification.setReceiverID(receiver);
        return userNotificationDAO.create(userNotification);
    }

}
