package org.example.DAO.interfaces;

import org.example.DAO.DAO;
import org.example.models.Contact;

import java.sql.Connection;

public interface ContactDAO extends DAO<Contact> {
    // Optional<T> get(long id);

    // List<T> getAll();

    boolean create(Contact t);
    boolean save(Contact t, Connection connection);

    default boolean update(Contact t) { return false; };

    default void delete(Contact t){};
}
