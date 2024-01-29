package org.example.DAO;

import org.example.DTOs.NotificationDto;
import org.example.models.UserNotification;
import org.example.utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserNotificationDAOImpl extends HandleContactAndNotification implements DAO<UserNotification> {
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

    public UserNotification findBySenderAndReceiver(String sender, String receiver) {
        String sql = "SELECT * FROM UserNotifications WHERE SenderID = ? AND ReceiverID = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, sender);
            preparedStatement.setString(2, receiver);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                UserNotification notification = new UserNotification();
                notification.setReceiverID(resultSet.getString("ReceiverID"));
                notification.setSenderID(resultSet.getString("SenderID"));
                notification.setNotificationMessage(resultSet.getString("NotificationMessage"));
                return notification;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<NotificationDto> receiveNotification(String phone) {

        List<NotificationDto> notifications = new ArrayList<>();

        String sql = "SELECT u.PhoneNumber, u.DisplayName, n.NotificationMessage, u.ProfilePicture, n.NotificationSentDate FROM Users u JOIN UserNotifications n ON u.PhoneNumber = n.SenderID WHERE n.ReceiverID = ?";

        try (Connection connection = DBConnection.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, phone);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                NotificationDto notification = new NotificationDto();
                notification.setPhoneSender(resultSet.getString("PhoneNumber"));
                notification.setName(resultSet.getString("DisplayName"));
                notification.setMessage(resultSet.getString("NotificationMessage"));
                Blob blob = resultSet.getBlob("ProfilePicture");
                notification.setPicture(blob.getBytes(1, (int) blob.length()));
                notification.setNotificationSentDate(resultSet.getTimestamp("NotificationSentDate"));
                notifications.add(notification);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return notifications;
    }



}


