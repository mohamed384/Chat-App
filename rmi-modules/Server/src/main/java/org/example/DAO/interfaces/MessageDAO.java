package org.example.DAO.interfaces;
import org.example.DAO.DAO;
import org.example.DTOs.MessageDTO;
import org.example.models.Message;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.util.List;

public interface MessageDAO extends DAO<Message> {

    @Override
    boolean create(Message message);

    @Override
    boolean save(Message message, Connection connection);

    @Override
    void delete(Message message);

    List<MessageDTO> getMessagesByChatId(int chatId);

}