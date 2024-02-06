package org.example.DAO;

import org.example.DAO.interfaces.ContactDAO;
import org.example.interfaces.CallBackServer;
import org.example.models.Contact;
import org.example.models.Enums.UserStatus;
import org.example.models.User;
import org.example.utils.DBConnection;
import org.example.utils.ImageConvertor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContactDAOImpl extends HandleContactAndNotification implements ContactDAO {
    @Override
    public boolean create(Contact contact) {
        boolean isSaved = false;
        try (Connection connection = DBConnection.getConnection()) {
            isSaved = save(contact, connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
        if (isPendingRequest(userID, friendID)) {
            Contact newContact1 = new Contact(friendID, userID);
            Contact newContact2 = new Contact(userID, friendID);
            if (create(newContact1) && create(newContact2)) {
                deleteNotification(userID, friendID);
                deleteNotification(friendID, userID);
                return true;
            }
        }

        return false;

    }

    private boolean isPendingRequest(String userID, String friendID) {
        String query = "SELECT * FROM usernotifications  WHERE ReceiverID = ? AND SenderID = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, userID);
            pstmt.setString(2, friendID);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    @Override
//    public List<Contact> getAllContactsByUserId(String userId) {
//        List<Contact> contacts = new ArrayList<>();
//        String query = "SELECT * FROM UserContacts WHERE UserID = ?";
//        try (Connection connection = DBConnection.getConnection();
//             PreparedStatement pstmt = connection.prepareStatement(query)) {
//            pstmt.setString(1, userId);
//            ResultSet rs = pstmt.executeQuery();
//            while (rs.next()) {
//                contacts.add(new Contact(rs.getString("FriendID"), rs.getString("UserID")));
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        System.out.println(contacts);
//        return contacts;
//
//    }
    public List<User> getAllContactsByUserId(String userId) {
        List<User> contacts = new ArrayList<>();
        String query = "SELECT users.* FROM \n" +
                "users inner join usercontacts \n" +
                "on usercontacts.UserID  = ? \n" +
                "where usercontacts.FriendID = users.PhoneNumber;";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, userId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Blob picture = rs.getBlob(5);
                byte[] pictureBytes = ImageConvertor.BlobToBytes(picture);
                User contact = new User(
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        pictureBytes,
                        rs.getString(6),
                        rs.getString(7),
                        rs.getString(8),
                        rs.getDate(9),
                        rs.getString(10),
                        UserStatus.valueOf(rs.getString(12)),
                        rs.getString(11),
                        rs.getTimestamp(13)
                );
                contacts.add(contact);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contacts;
    }


    public boolean deleteContact(String userID, String friendID) {
        String query = "DELETE FROM UserContacts WHERE (UserID = ? AND FriendID = ?) OR (UserID = ? AND FriendID = ?)";
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


    public boolean contactExists(String userPhone, String friendPhone) {
        String query = "SELECT * FROM UserContacts WHERE UserID = ? AND FriendID = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, userPhone);
            pstmt.setString(2, friendPhone);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
