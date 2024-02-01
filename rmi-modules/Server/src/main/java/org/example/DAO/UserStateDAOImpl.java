package org.example.DAO;

import org.example.DAO.interfaces.UserStateDAO;
import org.example.utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class UserStateDAOImpl implements UserStateDAO {
    @Override
    public int getCounteUsers() {
        int userCount = 0;
        String query = "SELECT COUNT(UserID) AS User_count FROM users";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                userCount = rs.getInt("User_count");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userCount;
    }
}
