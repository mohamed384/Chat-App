package org.example.services;

import org.example.DAO.ChatDAOImpl;
import org.example.DAO.ChatParticipantDAOImpl;
import org.example.DTOs.ChatDTO;
import org.example.models.Chat;
import org.example.models.Mapper.ChatMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ChatService {
    private final ChatDAOImpl chatDAO;
    private final ChatParticipantDAOImpl chatParticipantDAO;
    private final ChatMapper chatMapper = ChatMapper.INSTANCE;

    public ChatService() {
        this.chatDAO = new ChatDAOImpl();
        this.chatParticipantDAO = new ChatParticipantDAOImpl();
    }

    public boolean createChat(String name, byte[] img, String sender,String receiver) {
         return chatDAO.create(name, img, sender, receiver);
    }
    public ChatDTO getPrivateChat(String sender, String receiver){
        return chatMapper.toDTO(chatDAO.getPrivateChat(sender, receiver));
    }
    public List<ChatDTO> getAllChatsForUser(String phoneNumber){
        return chatDAO.getAllChatsForUser(phoneNumber).stream()
                .map(chatMapper::toDTO)
                .collect(Collectors.toList());
    } // CAREE HEEREEEEE

    public String getReceiverPhoneNumber(String senderPhoneNumber, int chatID){
        return chatDAO.getReceiverPhoneNumber(senderPhoneNumber,chatID);
    }
    public List<String> getChatParticipants(String senderPhoneNumber, int chatID){
        return chatDAO.getChatParticipants(senderPhoneNumber,chatID);
    }
    public boolean deleteChat(String sender, String receiver){
        Chat chat = chatDAO.getPrivateChat(sender, receiver);
        boolean result = chatParticipantDAO.deleteChatParticipants(chat.getChatID());
        if(result){
            chatDAO.deleteChat(chat);
            return true;
        }
        return  false;
    }

}
