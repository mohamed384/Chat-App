package org.example.DAO.interfaces;

import org.example.DAO.DAO;
import org.example.models.Chat;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.util.List;

public interface GroupChatDAO extends DAO<Chat> {
    boolean createChat(String name, byte[] img, String adminId, List<String> phones);

    default boolean create(Chat t){return false;}
    int save(String name, byte[] img, String adminId, Connection connection);

}
