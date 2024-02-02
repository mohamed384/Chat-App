package org.example.DAO.interfaces;

import org.example.DAO.DAO;
import org.example.models.ChatParticipant;

import java.sql.Connection;

public interface ChatParticipantDAO extends DAO<ChatParticipant> {
    boolean create(ChatParticipant t);
    default boolean save(ChatParticipant t, Connection connection){ return false; };

    default void delete(ChatParticipant t){};
}
