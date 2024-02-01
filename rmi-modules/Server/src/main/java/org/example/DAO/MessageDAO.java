package org.example.DAO;
import org.example.models.Message;
import java.sql.Connection;

public class MessageDAO implements DAO<Message> {

    @Override
    public boolean create(Message message) {
        return false;
    }

    @Override
    public boolean save(Message message, Connection connection) {
        return false;
    }

    @Override
    public void delete(Message message) {
        DAO.super.delete(message);
    }
}
