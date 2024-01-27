package org.example.DAO;

import org.example.DAO.interfaces.ContactDAO;
import org.example.models.Contact;
import org.example.utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
            pstmt.setString(1, contact.getFriendID());
            pstmt.setString(2, contact.getUserID());
            pstmt.executeUpdate();
            System.out.println("The contact is added: UserID " + contact.getUserID() + ", FriendID " + contact.getFriendID());
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean acceptInvite(String userID, String friendID) {
        if (isPendingRequest(friendID, userID)) {
            Contact newContact1 = new Contact(friendID, userID);
            Contact newContact2 = new Contact(userID, friendID);
            return create(newContact1) && create(newContact2);
        } else {
            return false;
        }

    }

    private boolean inviteAccepted(String userID, String friendID) {
        String query = "SELECT * FROM usernotifications  WHERE ReceiverID = ? AND SenderID = ? AND" +
                "NotificationMessage = 'Hi, I want to connect with you!'";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1,userID  );
            pstmt.setString(2, friendID );
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    private boolean isPendingRequest(String userID, String friendID) {
        String query = "SELECT * FROM usernotifications  WHERE ReceiverID = ? AND SenderID = ? AND" +
                "NotificationMessage = 'Hi, I want to connect with you!'";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, userID );
            pstmt.setString(2,  friendID );
            ResultSet rs = pstmt.executeQuery();
           return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    @Override
    public List<Contact> getAllContactsByUserId(String userId) {
        List<Contact> contacts = new ArrayList<>();
        String query = "SELECT * FROM UserContacts WHERE UserID = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, userId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                contacts.add(new Contact(rs.getString("FriendID"), rs.getString("UserID")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contacts;

    }


    public boolean deleteContact(String userID, String friendID) {
        String query = "DELETE FROM UserContacts WHERE (UserID = ? AND FriendID = ?) AND (UserID = ? AND FriendID = ?)";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, userID);
            pstmt.setString(2, friendID);
            pstmt.setString(3, friendID);
            pstmt.setString(4, userID);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


}
