package org.example.DAO;

import org.example.DAO.interfaces.ChatParticipantDAO;
import org.example.models.ChatParticipant;
import org.example.utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ChatParticipantDAOImpl implements ChatParticipantDAO {
    public boolean create(ChatParticipant chatParticipant) {
        boolean isSaved = false;
        try (Connection connection = DBConnection.getConnection()) {
            isSaved = save(chatParticipant, connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isSaved;
    }

    @Override
    public boolean save(ChatParticipant chatParticipant, Connection connection) {
        String query = "INSERT INTO ChatParticipants (ChatID, ParticipantUserID) VALUES (?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, chatParticipant.getChatID());
            pstmt.setString(2, chatParticipant.getParticipantUserID());

            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean saveGroup(List<ChatParticipant> chatParticipants, Connection connection) {
        String query = "INSERT INTO ChatParticipants (ChatID, ParticipantUserID) VALUES (?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            for (ChatParticipant chatParticipant : chatParticipants) {
                pstmt.setInt(1, chatParticipant.getChatID());
                pstmt.setString(2, chatParticipant.getParticipantUserID());
                pstmt.addBatch();
            }

            pstmt.executeBatch();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

//    @Override
//    public void deleteGroupParticipant(int chatId, String participantId) {
//        String query = "DELETE FROM ChatParticipants WHERE ChatID = ? AND ParticipantUserID = ?";
//        try (Connection connection = DBConnection.getConnection();
//             PreparedStatement pstmt = connection.prepareStatement(query)) {
//            pstmt.setInt(1, chatId);
//            pstmt.setString(2,participantId);
//
//            pstmt.executeUpdate();
//
//            boolean done =  updateAdmin(chatId);
//            if(!done){
//                System.out.println("admin updated");
//                deleteChat(chatId);
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

    @Override
    public void deleteGroupParticipant(int chatId, String participantId) {
        String queryCheckLastMember = "SELECT COUNT(*) FROM ChatParticipants WHERE ChatID = ?";
        String queryDeleteParticipant = "DELETE FROM ChatParticipants WHERE ChatID = ? AND ParticipantUserID = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmtCheckLastMember = connection.prepareStatement(queryCheckLastMember);
             PreparedStatement pstmtDeleteParticipant = connection.prepareStatement(queryDeleteParticipant)) {

            // Check if the participant being deleted is the last member
            pstmtCheckLastMember.setInt(1, chatId);
            ResultSet rs = pstmtCheckLastMember.executeQuery();
            rs.next();
            int memberCount = rs.getInt(1);

            // Delete the participant
            pstmtDeleteParticipant.setInt(1, chatId);
            pstmtDeleteParticipant.setString(2, participantId);
            pstmtDeleteParticipant.executeUpdate();

            boolean update = false;
            if (memberCount == 1) {
               update =  deleteChat(chatId);
            }

            if(!update)
                updateAdmin(chatId);


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public boolean updateAdmin(int chatId) {
        String query = "UPDATE Chat SET AdminID = (SELECT ParticipantUserID FROM ChatParticipants WHERE ChatID = ? ORDER BY ParticipantStartDate ASC LIMIT 1) WHERE ChatID = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setInt(1, chatId);
            pstmt.setInt(2, chatId);
            pstmt.executeUpdate();
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }



    public boolean deleteChat(int chatid) {
        String sql = "DELETE FROM Chat WHERE ChatID = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, chatid);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }



    @Override
    public boolean deleteChatParticipants(String sender, String receiver) {
//        String query = "DELETE FROM ChatParticipants WHERE ChatID IN (SELECT * FROM (SELECT ChatID FROM ChatParticipants WHERE ParticipantUserID = ? OR ParticipantUserID = ?) AS temp)";
//        try (Connection connection = DBConnection.getConnection();
//             PreparedStatement pstmt = connection.prepareStatement(query)) {
//            pstmt.setString(1, sender);
//            pstmt.setString(2, receiver);
//
//            pstmt.executeUpdate();
//            return true;
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
        return false;
    }

    public boolean deleteChatParticipants(int ChatID) {
        String query = "DELETE FROM ChatParticipants WHERE ChatID = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, ChatID);

            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;

    }

    public boolean contactExists(int chatId, String groupName) {
        String query = "SELECT * FROM ChatParticipants WHERE ChatID = ? AND ParticipantUserID = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, chatId);
            pstmt.setString(2, groupName);

            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;

    }

    @Override
    public boolean addNewUserToGroup(int chatId,String participantUserID) {
        String query = "INSERT INTO ChatParticipants (ChatID, ParticipantUserID) VALUES (?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, chatId);
            pstmt.setString(2, participantUserID);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }














}
