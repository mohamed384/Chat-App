package org.example.DAO;

import org.example.DAO.interfaces.MessageDAO;
import org.example.models.Message;
import org.example.utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MessageDAOImpl implements MessageDAO {

    @Override
    public boolean create(Message message) {
        boolean isSaved;
        try(Connection connection = DBConnection.getConnection()){
            isSaved = save(message,connection);
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


}
