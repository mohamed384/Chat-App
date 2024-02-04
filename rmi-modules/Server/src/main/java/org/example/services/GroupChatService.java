package org.example.services;

import org.example.DAO.GroupChatDAOImpl;
import org.example.DTOs.ChatDTO;
import org.example.models.Chat;
import org.example.models.Mapper.ChatMapper;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class GroupChatService {
    GroupChatDAOImpl groupChatDAO;
    private final ChatMapper chatMapper = ChatMapper.INSTANCE;


    public GroupChatService() {
        groupChatDAO = new GroupChatDAOImpl();
    }

    public boolean createChat(String name, byte[] img, String adminId, List<String> phones) {

        return groupChatDAO.createChat(name, img, adminId, phones);

    }
    public List<ChatDTO> getAllGroupChatsForUser(String phoneNumber){
        List<Chat> chats = groupChatDAO.getAllGroupChatsForUser(phoneNumber);
        List<ChatDTO> chatDTOS = new ArrayList<>();
        for(Chat c : chats){
            chatDTOS.add( chatMapper.toDTO(c));
        }
        return chatDTOS;
    }

}
