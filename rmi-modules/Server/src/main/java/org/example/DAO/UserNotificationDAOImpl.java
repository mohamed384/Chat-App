package org.example.DAO;

import org.example.models.UserNotification;
import org.example.utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserNotificationDAOImpl implements DAO<UserNotification> {
    @Override
    public boolean create(UserNotification notification) {
        try (Connection connection = DBConnection.getConnection()) {
            return save(notification, connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean save(UserNotification notification, Connection connection) {
        String sql = "INSERT INTO UserNotifications (ReceiverID, SenderID, NotificationMessage) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, notification.getReceiverID());
            preparedStatement.setString(2, notification.getSenderID());
            preparedStatement.setString(3, notification.getNotificationMessage());

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
