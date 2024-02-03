package org.example.services;

import org.example.DAO.ChatDAOImpl;
import org.example.DTOs.ChatDTO;
import org.example.models.Chat;
import org.example.models.Mapper.ChatMapper;

import java.util.ArrayList;
import java.util.List;

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
    public List<ChatDTO> getAllChatsForUser(String phoneNumber){
        List<Chat> chats= chatDAO.getAllChatsForUser(phoneNumber);
        List<ChatDTO> chatDTOS = new ArrayList<>();

        System.out.println("chats size" + chats.size());
        for(Chat c : chats){
            chatDTOS.add( chatMapper.toDTO(c));
        }

        return  chatDTOS;
    }

    public String getReceiverPhoneNumber(String senderPhoneNumber, int chatID){
        return chatDAO.getReceiverPhoneNumber(senderPhoneNumber,chatID);
    }

}
