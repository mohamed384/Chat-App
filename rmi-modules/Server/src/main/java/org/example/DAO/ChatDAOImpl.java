package org.example.DAO;

import org.example.DAO.interfaces.ChatDAO;
import org.example.models.Chat;
import org.example.models.ChatParticipant;
import org.example.utils.DBConnection;

import java.sql.*;

public class ChatDAOImpl implements ChatDAO {
    ChatParticipantDAOImpl chatParticipantDAO;

    public ChatDAOImpl(){
        chatParticipantDAO = new ChatParticipantDAOImpl();
    }
    @Override
    public boolean create(String name, byte[] img, int adminId,String sender, String receiver) {
        boolean isSaved = false;
        System.out.println("createCHAT DAOOO ana fel daoo");
        try (Connection connection = DBConnection.getConnection()) {
            int chatId = save(name, img, adminId, connection);
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

    public int save(String name, byte[] img, int adminId, Connection connection) {
        String query = "INSERT INTO Chat (ChatName, ChatImage, AdminID) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, name);
            pstmt.setBytes(2, img);
            if(adminId!=0){
                pstmt.setInt(3, adminId);
            }else{
                pstmt.setObject(3, null);
            }

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

    public Chat getChat(int chatId) {
        String sql = "SELECT * FROM Chat WHERE ChatID = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, chatId);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Chat chat = new Chat(rs.getString("ChatName"),
                        rs.getBytes("ChatImage"),
                        rs.getInt("AdminID"));
                        chat.setChatID(rs.getInt("ChatID"));


                chat.setChatID(rs.getInt("ChatID"));
                chat.setChatName(rs.getString("ChatName"));
                chat.setChatImage(rs.getBytes("ChatImage"));
                chat.setAdminID(rs.getInt("AdminID"));

                return chat;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Chat getPrivateChat(String sender, String receiver) {
        String sql = "SELECT C.* FROM Chat C " +
                "JOIN ChatParticipants CP1 ON C.ChatID = CP1.ChatID " +
                "JOIN ChatParticipants CP2 ON C.ChatID = CP2.ChatID " +
                "WHERE (CP1.ParticipantUserID = ? OR CP1.ParticipantUserID = ?) " +
                "  AND (CP2.ParticipantUserID = ? OR CP2.ParticipantUserID = ?) " +
                "  AND C.AdminID IS NULL";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, sender);
            pstmt.setString(2, receiver);
            pstmt.setString(3, sender);
            pstmt.setString(4, receiver);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Chat chat = new Chat(rs.getString("ChatName"),
                        rs.getBytes("ChatImage"),
                        rs.getInt("AdminID"));
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


}
