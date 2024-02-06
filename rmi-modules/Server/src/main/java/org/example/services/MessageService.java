package org.example.services;

import org.example.DAO.MessageDAOImpl;
import org.example.DAO.interfaces.MessageDAO;
import org.example.DTOs.MessageDTO;
import org.example.models.Mapper.MessageMapper;
import org.example.models.Message;

import java.util.List;

public class MessageService {
    private MessageDAO messageDAO;
    private final MessageMapper messageMapper = MessageMapper.INSTANCE;

    public MessageService(){
       this.messageDAO = new MessageDAOImpl();
    }

    public List<MessageDTO> getMessagesByChatId(int chatId) {
        return messageDAO.getMessagesByChatId(chatId);
    }

    public MessageDTO retrieveFileFromDB(int messageID) {

        return messageMapper.toDTO(messageDAO.retrieveFileFromDB(messageID));

    }
}
