package org.example.DAO;

import org.example.DAO.interfaces.ChatDAO;
import org.example.models.Chat;
import org.example.models.ChatParticipant;
import org.example.utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ChatDAOImpl implements ChatDAO {
    ChatParticipantDAOImpl chatParticipantDAO;

    public ChatDAOImpl() {
        chatParticipantDAO = new ChatParticipantDAOImpl();
    }

    @Override
    public boolean create(String name, byte[] img,String sender, String receiver) {
        boolean isSaved = false;
        System.out.println("createCHAT DAOOO ana fel daoo");
        try (Connection connection = DBConnection.getConnection()) {
            int chatId = save(name, img,connection);
            if (chatId != -1) {
                chatParticipantDAO.create(new ChatParticipant(chatId, sender));
                chatParticipantDAO.create(new ChatParticipant(chatId, receiver));
                isSaved = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isSaved;
    }

    public int save(String name, byte[] img, Connection connection) {
        String query = "INSERT INTO Chat (ChatName, ChatImage) VALUES (?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, name);
            pstmt.setBytes(2, img);
            pstmt.executeUpdate();

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating chat failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }


    @Override
    public void delete(Chat chat) {
        String sql = "DELETE FROM Chat WHERE ChatID = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, chat.getChatID());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    @Override
    public Chat getPrivateChat(String sender, String receiver) {
        String sql = "SELECT C.* FROM Chat C " +
                "JOIN ChatParticipants CP1 ON C.ChatID = CP1.ChatID " +
                "JOIN ChatParticipants CP2 ON C.ChatID = CP2.ChatID " +
                "WHERE CP1.ParticipantUserID = ? AND CP2.ParticipantUserID = ? " +
                "AND C.AdminID IS NULL";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, sender);
            pstmt.setString(2, receiver);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Chat chat = new Chat(rs.getString("ChatName"),
                        rs.getBytes("ChatImage"));
                chat.setChatID(rs.getInt("ChatID"));

                return chat;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Chat> getAllChatsForUser(String phoneNumber) {
        String query = "SELECT C.*, U2.DisplayName,U2.ProfilePicture\n" +
                "FROM Chat C\n" +
                "JOIN ChatParticipants CP1 ON C.ChatID = CP1.ChatID\n" +
                "JOIN Users U1 ON CP1.ParticipantUserID = U1.PhoneNumber\n" +
                "JOIN ChatParticipants CP2 ON C.ChatID = CP2.ChatID AND CP2.ParticipantUserID != U1.PhoneNumber\n" +
                "JOIN Users U2 ON CP2.ParticipantUserID = U2.PhoneNumber\n" +
                "WHERE U1.PhoneNumber = ?" +
                " AND C.AdminID IS NULL;";

        List<Chat> chats = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, phoneNumber);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Chat chat = new Chat(rs.getString("ChatName"),
                        rs.getBytes("ChatImage"),
                        rs.getString("DisplayName"),
                        rs.getBytes("ProfilePicture"));
                chat.setChatID(rs.getInt("ChatID"));

                chats.add(chat);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return chats;
    }
    public List<String> getChatParticipants(String sender, int ChatID){
        String query = "SELECT CP.* FROM ChatParticipants CP " +
                "WHERE CP.ChatID = ? AND CP.ParticipantUserID != ?";
        List<String> participants = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, ChatID);
            pstmt.setString(2, sender);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                ChatParticipant participant = new ChatParticipant(rs.getInt("ChatID"),
                        rs.getString("ParticipantUserID"));
                participants.add(participant.getParticipantUserID());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return participants;
    }


    //    @Override
//    public String getReceiverPhoneNumber(String senderPhoneNumber) {
//        String query = "SELECT U.PhoneNumber " +
//                "FROM ChatParticipants CP " +
//                "JOIN Users U ON CP.ParticipantUserID = U.PhoneNumber " +
//                "WHERE CP.ChatID IN (SELECT ChatID FROM ChatParticipants WHERE ParticipantUserID = ?) " +
//                "AND U.PhoneNumber != ?";
//
//        try (Connection connection = DBConnection.getConnection();
//             PreparedStatement pstmt = connection.prepareStatement(query)) {
//            pstmt.setString(1, senderPhoneNumber);
//            pstmt.setString(2, senderPhoneNumber);
//
//            ResultSet rs = pstmt.executeQuery();
//
//            if (rs.next()) {
//                return rs.getString("PhoneNumber");
//            } else {
//                return null;
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
    @Override
    public String getReceiverPhoneNumber(String senderPhoneNumber, int chatId) {
        String query = "SELECT U.PhoneNumber " +
                "FROM ChatParticipants CP " +
                "JOIN Users U ON CP.ParticipantUserID = U.PhoneNumber " +
                "WHERE CP.ChatID = ? " +
                "AND U.PhoneNumber != ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, chatId);
            pstmt.setString(2, senderPhoneNumber);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getString("PhoneNumber");
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }




}
