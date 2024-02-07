package org.example.DAO;

import org.example.DAO.interfaces.MessageDAO;
import org.example.DTOs.MessageDTO;
import org.example.models.Message;
import org.example.utils.DBConnection;
import org.example.utils.MessageDAOSaveHelper;

import java.rmi.RemoteException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageDAOImpl implements MessageDAO {
    @Override
    public MessageDAOSaveHelper createWithHandler(Message message) {
            MessageDAOSaveHelper isSaved;
        try (Connection connection = DBConnection.getConnection()) {
            isSaved = saveWithHandler(message, connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        System.out.println("this is create message dao impl"+ isSaved);
        System.out.println(message.toString());

        return isSaved;
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

    public MessageDAOSaveHelper saveWithHandler(Message message, Connection connection) {
        String query = "INSERT INTO Messages (SenderID, ChatID, MessageContent, IsAttachment) VALUES (?, ?, ?, ?)";
        int generatedKey = -1;
        boolean success = false;

        try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, message.getSenderID());
            preparedStatement.setInt(2, message.getChatID());
            preparedStatement.setString(3, message.getMessageContent());
            preparedStatement.setBoolean(4, message.isAttachment());

            int rowsAffected = preparedStatement.executeUpdate();
            if(message.isAttachment()){
                ResultSet rs = preparedStatement.getGeneratedKeys();
                if (rs.next()) {
                    generatedKey = rs.getInt(1);
                    success = saveAttachmentFile(generatedKey, message.getAttachment(), connection);
                }
            } else {
                success = rowsAffected > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new MessageDAOSaveHelper(success, generatedKey);
    }

    public boolean saveAttachmentFile(int messageId, byte[] attachment, Connection connection) {
        String query = "INSERT INTO Attachment (MessageID, Attachment) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, messageId);
            preparedStatement.setBytes(2, attachment);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Message retrieveFileFromDB(int messageID) {
        String query = "SELECT Messages.*, Attachment.Attachment FROM Attachment, Messages WHERE " +
                "Attachment.MessageID = Messages.MessageID AND Attachment.MessageID = ?";

        Message message = null;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, messageID);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {


                message = new Message(
                        rs.getString("MessageContent"),
                        rs.getString("SenderID"),
                        rs.getInt("ChatID"),
                        rs.getTimestamp("MessageTimestamp"),
                        rs.getBoolean("IsAttachment"));
                message.setAttachment(rs.getBytes("Attachment"));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return message;
    }

    @Override
    public List<Message> retrieveAllMessages(int chatID) {
        String query = "SELECT * FROM Messages WHERE ChatID = ? ORDER BY MessageTimestamp ASC limit 10";

        List<Message> messages = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, chatID);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Message message = new Message(
                        rs.getString("MessageContent"),
                        rs.getString("SenderID"),
                        rs.getInt("ChatID"),
                        rs.getTimestamp("MessageTimestamp"),
                        rs.getBoolean("IsAttachment"));
                if (rs.getBoolean("IsAttachment")) {
                    message.setAttachment(retrieveFileFromDB(rs.getInt("MessageID")).getAttachment());
                }
                messages.add(message);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return messages;
    }

}
