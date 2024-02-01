package org.example.services;

import org.example.DAO.ChatDAOImpl;
import org.example.DTOs.ChatDTO;
import org.example.models.Chat;
import org.example.models.Mapper.ChatMapper;

public class ChatService {
    private final ChatDAOImpl chatDAO;
    private final ChatMapper chatMapper = ChatMapper.INSTANCE;

    public ChatService() {
        this.chatDAO = new ChatDAOImpl();
    }

    public boolean createChat(String name, byte[] img, int adminId,String sender,String receiver) {
         return chatDAO.create(name, img, adminId, sender, receiver);
    }
    public ChatDTO getPrivateChat(String sender, String receiver){
        return chatMapper.toDTO(chatDAO.getPrivateChat(sender, receiver));
    }
}
