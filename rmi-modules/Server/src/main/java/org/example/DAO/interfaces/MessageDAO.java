package org.example.DAO.interfaces;
import org.example.DAO.DAO;
import org.example.models.Message;
import java.sql.Connection;

public interface MessageDAO extends DAO<Message> {

    @Override
     boolean create(Message message);

    @Override
     boolean save(Message message, Connection connection);

    @Override
     void delete(Message message);
}
