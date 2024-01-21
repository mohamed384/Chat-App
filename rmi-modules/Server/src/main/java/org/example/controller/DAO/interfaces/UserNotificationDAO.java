package org.example.controller.DAO.interfaces;

import org.example.controller.DAO.DAO;
import org.example.models.User;
import org.example.models.UserNotification;

import java.sql.Connection;

public interface UserNotificationDAO extends DAO<UserNotification> {
    boolean create(UserNotification t);
    default boolean save(UserNotification t, Connection connection){ return false; };

    default boolean update(UserNotification t) { return false; };

    default void delete(UserNotification t){};
}
