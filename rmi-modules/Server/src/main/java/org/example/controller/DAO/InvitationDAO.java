package org.example.controller.DAO;

import org.example.models.Invitations;
import org.example.models.User;
import org.example.utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class InvitationDAO implements DAO<Invitations> {
    @Override
    public boolean create(Invitations Invitations) {
        User userRecipient = UserDAO.findByPhoneNumber(Invitations.getRecipientPhoneNumber());
        boolean isSaved = false;
        if (userRecipient != null) {
            isSaved = save(Invitations);
        }
        return isSaved;
    }

    public boolean save(Invitations inv) {
        String sql = "INSERT INTO invitations (sender_phone_number, recipient_phone_number, status,timestamp)" +
                " VALUES (?, ?, ?,?)";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, inv.getSenderPhoneNumber());
            pstmt.setString(2, inv.getRecipientPhoneNumber());
            pstmt.setString(3, inv.getInvitaionStatus().name());
            pstmt.setTimestamp(4, new Timestamp(System.currentTimeMillis()));

            pstmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public void delete(Invitations invitations) {
        String query = "DELETE FROM invitations WHERE sender_phone_number = ? AND recipient_phone_number = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, invitations.getSenderPhoneNumber());
            pstmt.setString(2, invitations.getRecipientPhoneNumber());
            pstmt.executeUpdate();
        } catch (SQLException e) {
           e.printStackTrace();
        }
    }

}

