package org.example.DAO;

import org.example.DAO.interfaces.ChatDAO;
import org.example.models.Chat;
import org.example.utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ChatDAOImpl implements ChatDAO {
    @Override
    public boolean create(String name, byte[] img,int adminId) {
        boolean isSaved = false;
        try (Connection connection = DBConnection.getConnection()) {
            isSaved = save(name, img, adminId, connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isSaved;
    }

    @Override
    public boolean save(String name, byte[] img, int adminId, Connection connection) {
        String query = "INSERT INTO Chat (ChatName, ChatImage, AdminID) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, name);
            pstmt.setBytes(2, img);
            pstmt.setInt(3, adminId);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void delete(Chat chat) {
        String sql = "DELETE FROM Chat WHERE ChatID = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, chat.getChatID());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Chat getChat(int chatId) {
        String sql = "SELECT * FROM Chat WHERE ChatID = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, chatId);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Chat chat = new Chat(rs.getString("ChatName"),
                        rs.getBytes("ChatImage"),
                        rs.getInt("AdminID"));
                        chat.setChatID(rs.getInt("ChatID"));


                chat.setChatID(rs.getInt("ChatID"));
                chat.setChatName(rs.getString("ChatName"));
                chat.setChatImage(rs.getBytes("ChatImage"));
                chat.setAdminID(rs.getInt("AdminID"));

                return chat;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

}
