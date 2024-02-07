package org.example.DAO.interfaces;
import org.example.DAO.DAO;
import org.example.DTOs.MessageDTO;
import org.example.models.Message;
import org.example.utils.MessageDAOSaveHelper;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.util.List;

public interface MessageDAO extends DAO<Message> {

    @Override
    default boolean create(Message message){return false;};

    @Override
    default boolean save(Message message, Connection connection){return false;};

    @Override
    void delete(Message message);

    MessageDAOSaveHelper saveWithHandler(Message message, Connection connection);
    MessageDAOSaveHelper createWithHandler(Message message);
    List<MessageDTO> getMessagesByChatId(int chatId);

    Message retrieveFileFromDB(int messageID);

    List<Message> retrieveAllMessages(int chatID);
}