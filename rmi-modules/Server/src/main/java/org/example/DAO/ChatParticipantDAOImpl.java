package org.example.DAO;

import org.example.DAO.interfaces.ChatParticipantDAO;
import org.example.models.ChatParticipant;
import org.example.utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ChatParticipantDAOImpl implements ChatParticipantDAO {
    public boolean create(ChatParticipant chatParticipant) {
        boolean isSaved = false;
        try (Connection connection = DBConnection.getConnection()) {
            isSaved = save(chatParticipant, connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isSaved;
    }

    @Override
    public boolean save(ChatParticipant chatParticipant, Connection connection) {
        String query = "INSERT INTO ChatParticipants (ChatID, ParticipantUserID) VALUES (?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, chatParticipant.getChatID());
            pstmt.setString(2, chatParticipant.getParticipantUserID());

            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean saveGroup(List<ChatParticipant> chatParticipants, Connection connection) {
        String query = "INSERT INTO ChatParticipants (ChatID, ParticipantUserID) VALUES (?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            for (ChatParticipant chatParticipant : chatParticipants) {
                pstmt.setInt(1, chatParticipant.getChatID());
                pstmt.setString(2, chatParticipant.getParticipantUserID());
                pstmt.addBatch();
            }

            pstmt.executeBatch();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void delete(ChatParticipant chatParticipant) {
        String query = "DELETE FROM ChatParticipants WHERE ChatID = ? AND ParticipantUserID = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, chatParticipant.getChatID());
            pstmt.setString(2, chatParticipant.getParticipantUserID());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean deleteChatParticipants(String sender, String receiver) {
        String query = "DELETE FROM ChatParticipants WHERE ChatID IN (SELECT * FROM (SELECT ChatID FROM ChatParticipants WHERE ParticipantUserID = ? OR ParticipantUserID = ?) AS temp)";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, sender);
            pstmt.setString(2, receiver);

            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteChatParticipants(int ChatID) {
        String query = "DELETE FROM ChatParticipants WHERE ChatID = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, ChatID);

            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;

    }
}
