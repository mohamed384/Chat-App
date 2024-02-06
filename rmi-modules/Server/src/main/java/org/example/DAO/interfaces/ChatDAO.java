package org.example.DAO.interfaces;

import org.example.DAO.DAO;
import org.example.DTOs.ChatDTO;
import org.example.models.Chat;
import org.example.models.Contact;
import org.example.models.User;

import java.sql.Connection;
import java.util.List;

public interface ChatDAO extends DAO<Chat> {
    boolean create(String name, byte[] img, String sender, String receiver);

    int save(String name, byte[] img, Connection connection);

    default boolean create(Chat t) {
        return false;
    }

    Chat getPrivateChat(String sender, String receiver);

    List<Chat> getAllChatsForUser(String phoneNumber);

    String getReceiverPhoneNumber(String senderPhoneNumber, int chatID);

    List<String> getChatParticipants(String sender, int ChatID);

    void deleteChat(Chat chat);


}

