package org.example.DAO.interfaces;

import org.example.DAO.DAO;
import org.example.models.User;

import java.sql.Connection;

public interface UserDAO extends DAO<User> {
    // Optional<T> get(long id);

    // List<T> getAll();

    //  void save(T t);
    boolean create(User t);
    default boolean save(User t, Connection connection){ return false; };

    default boolean update(User t) { return false; };

    default void delete(User t){};
}
