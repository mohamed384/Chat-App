package org.example.DAO;

import org.example.DAO.interfaces.GroupChatDAO;
import org.example.models.Chat;
import org.example.models.ChatParticipant;
import org.example.utils.DBConnection;

import java.rmi.RemoteException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GroupChatDAOImpl implements GroupChatDAO {
    @Override
    public boolean createChat(String name, byte[] img, String adminId, List<String> phones) {
        boolean isSaved = false;
        System.out.println("createCHATGroup DAO");
        try (Connection connection = DBConnection.getConnection()) {
            int chatId = save(name, img, adminId, connection);
            if (chatId != -1) {
                isSaved = saveGroupChatParticipants(phones,chatId,connection);


            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isSaved;
    }

    @Override
    public int save(String name, byte[] img, String adminId, Connection connection) {
        String query = "INSERT INTO Chat (ChatName, ChatImage, AdminID) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, name);
            pstmt.setBytes(2, img);
            pstmt.setString(3, adminId);
            pstmt.executeUpdate();

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating chat Group failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public boolean saveGroupChatParticipants(List<String> phones,int ChatID ,Connection connection) {
        String query = "INSERT INTO ChatParticipants (ChatID, ParticipantUserID) VALUES (?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            for (String ph : phones) {
                pstmt.setInt(1, ChatID);
                pstmt.setString(2, ph);
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
    public List<Chat> getAllGroupChatsForUser(String phoneNumber) {
        String query = "select  ChatParticipants.chatId, chat.chatName ,chat.AdminID,chat.ChatImage\n" +
                "from users  inner join ChatParticipants on users.PhoneNumber= Chatparticipants.ParticipantUserID \n" +
                "inner join chat on chat.ChatID = chatparticipants.ChatID\n" +
                " where ChatParticipants.ParticipantUserID= ? and chat.AdminID is not null;";

        List<Chat> chats = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, phoneNumber);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Chat chat = new Chat();
                chat.setChatID(rs.getInt("chatId"));
                chat.setChatName(rs.getString("chatName"));
                chat.setAdminID(rs.getString("AdminID"));
                chat.setChatImage(rs.getBytes("ChatImage"));

                chats.add(chat);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return chats;
    }

}
