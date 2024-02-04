package org.example.DAO;

import org.example.DAO.interfaces.MessageDAO;
import org.example.DTOs.MessageDTO;
import org.example.models.Message;
import org.example.utils.DBConnection;

import java.rmi.RemoteException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageDAOImpl implements MessageDAO {
    @Override
    public boolean create(Message message) {
        boolean isSaved;
        try (Connection connection = DBConnection.getConnection()) {
//            if(message.isAttachment())
//                isSaved = saveAttachment(message, connection);
//            else
                isSaved = save(message, connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return isSaved;
    }

    @Override
    public boolean save(Message message, Connection connection) {
        String query = "INSERT INTO Messages (SenderID, ChatID, MessageContent, IsAttachment) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, message.getSenderID());
            preparedStatement.setInt(2, message.getChatID());
            preparedStatement.setString(3, message.getMessageContent());
            preparedStatement.setBoolean(4, message.isAttachment());

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void delete(Message message) {

    }

    @Override
    public List<MessageDTO> getMessagesByChatId(int chatId) {

        String query = "SELECT * FROM Messages WHERE ChatID = ?";

        List<MessageDTO> messages = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, chatId);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                MessageDTO message = new MessageDTO(
                        rs.getString("Content"),
                        rs.getString("SenderPhoneNumber"),
                        rs.getInt("ChatID"),
                        rs.getTimestamp("Timestamp"));
                messages.add(message);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return messages;

    }

//    public boolean saveAttachment(Message message, Connection connection) {
//        String query = "INSERT INTO Messages (SenderID, ChatID, MessageContent, IsAttachment) VALUES (?, ?, ?, ?)";
//        try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
//            preparedStatement.setString(1, message.getSenderID());
//            preparedStatement.setInt(2, message.getChatID());
//            preparedStatement.setString(3, message.getMessageContent());
//            preparedStatement.setBoolean(4, message.isAttachment());
//
//            int rowsAffected = preparedStatement.executeUpdate();
//            if (rowsAffected > 0) {
//                ResultSet rs = preparedStatement.getGeneratedKeys();
//                if (rs.next()) {
//                    int messageId = rs.getInt(1);
//                    return saveAttachment(messageId, message.getAttachment(), connection);
//                }
//            }
//            return false;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    public boolean saveAttachment(int messageId, byte[] attachment, Connection connection) {
//        String query = "INSERT INTO Attachment (MessageID, Attachment) VALUES (?, ?)";
//        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
//            preparedStatement.setInt(1, messageId);
//            preparedStatement.setBytes(2, attachment);
//
//            int rowsAffected = preparedStatement.executeUpdate();
//            return rowsAffected > 0;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
}
