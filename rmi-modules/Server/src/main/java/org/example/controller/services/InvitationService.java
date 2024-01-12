package org.example.controller.services;

import org.example.controller.DAO.UserDAO;
import org.example.models.User;
import org.example.utils.DBConnection;

import java.sql.Connection;
import java.sql.SQLException;

public class InvitationService {
    private UserDAO userDao;

    public InvitationService() {
        userDao = new UserDAO();
    }

    public void sendInvitation(String phoneNumber) {

        User user = userDao.findByPhoneNumber(phoneNumber);
        try (Connection connection = DBConnection.getConnection()) {


        } catch (SQLException e) {
        }

    }

}
