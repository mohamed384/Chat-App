package org.example.services;

import org.example.DAO.MessageDAO;

public class MessageService {
    private MessageDAO messageDAO;
    public MessageService(){
        this.messageDAO = new MessageDAO();
    }
}
