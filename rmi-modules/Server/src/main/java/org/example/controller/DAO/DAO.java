package org.example.controller.DAO;

import org.example.models.User;

public interface DAO<T> {

   // Optional<T> get(long id);

   // List<T> getAll();

  //  void save(T t);
     boolean create(T t);

   // void update(T t, String[] params);

   // void delete(T t);
}