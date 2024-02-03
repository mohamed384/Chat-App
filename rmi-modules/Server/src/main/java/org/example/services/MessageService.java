package org.example.services;

import org.example.DAO.MessageDAOImpl;
import org.example.DAO.interfaces.MessageDAO;
import org.example.DTOs.MessageDTO;

import java.rmi.RemoteException;
import java.util.List;

public class MessageService {
    private MessageDAO messageDAOImpl;
    public MessageService(){
       this.messageDAOImpl = new MessageDAOImpl();
    }

    public List<MessageDTO> getMessagesByChatId(int chatId) throws RemoteException {
        return messageDAOImpl.getMessagesByChatId(chatId);
    }
}
