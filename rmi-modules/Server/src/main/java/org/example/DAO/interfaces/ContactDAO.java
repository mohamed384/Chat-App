package org.example.DAO.interfaces;

import org.example.DAO.DAO;
import org.example.models.Contact;

import java.sql.Connection;
import java.util.List;

public interface ContactDAO extends DAO<Contact> {
    // Optional<T> get(long id);

    // List<T> getAll();

    boolean create(Contact t);
    boolean save(Contact t, Connection connection);

    default boolean update(Contact t) { return false; };

    default List<Contact> getAllContactsByUserId(String sender){return null;};

    default void delete(Contact t){};
}
