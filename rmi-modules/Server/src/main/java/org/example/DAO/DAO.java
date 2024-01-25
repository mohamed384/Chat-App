package org.example.DAO;

import org.example.models.User;

import java.sql.Connection;

public interface DAO<T> {

   // Optional<T> get(long id);

   // List<T> getAll();

  //  void save(T t);
     boolean create(T t);
     default boolean save(T t, Connection connection){ return false; };

   default boolean update(T t) { return false; };

    default void delete(T t){};
}