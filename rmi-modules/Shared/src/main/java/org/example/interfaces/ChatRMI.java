package org.example.interfaces;

import org.example.DTOs.ChatDTO;
import org.example.models.Chat;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ChatRMI extends Remote {
     boolean createChat(String name, byte[] img, String sender, String receiver) throws RemoteException;

     ChatDTO getPrivateChat(String sender, String receiver) throws RemoteException;

     List<ChatDTO> getAllChatsForUser(String phoneNumber) throws RemoteException;

     String getReceiverPhoneNumber(String senderPhoneNumber, int chatID) throws RemoteException;

     List<String> getChatParticipants(String senderPhoneNumber,int chatID) throws RemoteException;

     boolean deleteChat(String sender, String receiver) throws RemoteException;

     boolean isGroupChat(int chatID) throws RemoteException;
     String getGroupAdminID(int chatID) throws RemoteException;
      void deleteGroupParticipant(int chatId, String participantId) throws RemoteException;

//      boolean addGroupParticipant(int chatId, String participantId) throws RemoteException;
      boolean contactExists( int chatId , String groupName) throws RemoteException;
     boolean addNewUserToGroup(int chatId,String participantUserID) throws RemoteException;
      boolean updateChatGroup(int chatId, byte[] chatImage, String chatName) throws RemoteException;
}


