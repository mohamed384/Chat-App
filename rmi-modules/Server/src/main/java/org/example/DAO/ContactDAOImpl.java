package org.example.DAO;

import org.example.DAO.interfaces.ContactDAO;
import org.example.models.Contact;
import org.example.utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

    public boolean acceptInvite(int userID, int friendID) {
        if (isPendingRequest(friendID, userID)) {
            Contact newContact1 = new Contact(friendID, userID);
            Contact newContact2 = new Contact(userID, friendID);
            return create(newContact1) && create(newContact2);
        } else {
            return false;
        }
    }

    private boolean isPendingRequest(int userID, int friendID) {
        String query = "SELECT * FROM UserContacts WHERE UserID = ? AND FriendID = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, userID);
            pstmt.setInt(2, friendID);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
//    private boolean isPendingRequest(int userID, int friendID) {
//        return isFriend(userID, friendID);
//    }
}
