package org.example.DAO;

import org.example.utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class HandleContactAndNotification {
    public boolean deleteNotification(String userID, String friendID) {
        String query = "DELETE FROM UserNotifications WHERE (ReceiverID = ? AND SenderID = ?)";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, userID);
            pstmt.setString(2, friendID);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
