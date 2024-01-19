package org.example.controller.DAO;

import org.example.controller.DAO.interfaces.ContactDAO;
import org.example.models.Contact;
import org.example.utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ContactDAOImpl implements ContactDAO {
    @Override
    public boolean create(Contact contact) {
        boolean isSaved=false;
        try (Connection connection = DBConnection.getConnection()) {
            isSaved = save(contact,connection);
        }catch (SQLException e){e.printStackTrace();}
        return isSaved;
    }

    @Override
    public boolean save(Contact contact, Connection connection) {
        String query = "INSERT INTO UserContacts (FriendID, UserID) VALUES (?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, contact.getFriendID());
            pstmt.setInt(2, contact.getUserID());
            pstmt.executeUpdate();
            System.out.println("The contact is added: UserID " + contact.getUserID() + ", FriendID " + contact.getFriendID());
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //public void search
}
