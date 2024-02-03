package org.example.controller.FXMLController.UtilsFX;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.DTOs.ChatDTO;
import org.example.DTOs.UserDTO;

import java.util.List;

public class ChatListManager {
    private static ChatListManager instance;
    private ObservableList<ChatDTO> chats;

    private ChatListManager() {
        chats = FXCollections.observableArrayList();
    }

    public static ChatListManager getInstance() {
        if (instance == null) {
            instance = new ChatListManager();
        }
        return instance;
    }

    public void addChat(ChatDTO chat) {

        chats.add(chat);
    }

    public void addAllChats(List<ChatDTO> chatDTOS) {
        for( ChatDTO c : chatDTOS){
            System.out.println(c.getChatName());
        }
        chats.addAll(chatDTOS);
    }





    public ObservableList<ChatDTO> getAllChats() {
        return chats;
    }

}