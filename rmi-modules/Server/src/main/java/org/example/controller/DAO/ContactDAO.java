package org.example.controller.DAO;

import org.example.models.Contact;
import org.example.utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ContactDAO implements DAO<Contact> {
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
        String query = "INSERT INTO contacts (user_id, contact_phone_number, is_blocked) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, contact.getUserID());
            pstmt.setInt(2, contact.getFriendID());
            pstmt.setBoolean(3, false);
            pstmt.executeUpdate();
            System.out.println(contact.getUserID() + " "+contact.getFriendID() );
            System.out.println("the contact is added");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
