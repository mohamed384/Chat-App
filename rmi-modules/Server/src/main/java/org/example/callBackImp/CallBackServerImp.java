package org.example.callBackImp;
import org.example.DAO.ChatDAOImpl;
import org.example.DAO.MessageDAOImpl;
import org.example.DTOs.UserDTO;
import org.example.interfaces.CallBackClient;
import org.example.interfaces.CallBackServer;
import org.example.models.Chat;
import org.example.models.Message;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CallBackServerImp extends UnicastRemoteObject implements CallBackServer , Serializable {

    static Map<String, CallBackClient> clients = new HashMap<>();
    private final MessageDAOImpl messageDAO;
    private final ChatDAOImpl chatDAO;

    public static int getClients() {
        return clients.size();
    }

    public CallBackServerImp() throws RemoteException {
        messageDAO  = new MessageDAOImpl();
        chatDAO = new ChatDAOImpl();
    }
    @Override
    public boolean login(String phoneNumber, CallBackClient callBackClient) {
            System.out.println("login: "+phoneNumber);

        try {
            callBackClient.notification("Welcome to CyberChat App");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        clients.put(phoneNumber, callBackClient);

        for (String key : clients.keySet()) {
            System.out.println(key);
        }

        return true;
    }

    @Override
    public boolean logout(String phoneNumber, CallBackClient callBackClient) {

            System.out.println("logout: "+phoneNumber);
            clients.remove(phoneNumber);
        return true;
    }

    @Override
    public void sendMsg(String msg, String senderPhoneNumber, String receiverPhoneNumber) {

        System.out.println("sendMsg: "+ msg);
        System.out.println("sender: "+ senderPhoneNumber);
        System.out.println("clients: " + clients);

        CallBackClient callBackClient = clients.get(receiverPhoneNumber);

        try {
          //  callBackClient.notification( "You have a new message from "+senderPhoneNumber);
            Chat chat = chatDAO.getPrivateChat(senderPhoneNumber, receiverPhoneNumber);
            callBackClient.receiveMsg(msg, chat.getChatID() , senderPhoneNumber , receiverPhoneNumber);
            /// add message in db

            // Create a new Message
            Message message = new Message();
            message.setSenderID(senderPhoneNumber);
            message.setMessageContent(msg);
            message.setIsAttachment(false); // Set this based on whether the message is an attachment

            // Get the ChatID

            int chatID = chatDAO.getPrivateChat(senderPhoneNumber, receiverPhoneNumber).getChatID();
            System.out.println("ChatID: " + chatID);
            message.setChatID(chatID);


            // Save the message to the database
             messageDAO.create(message);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void sendAnnouncement(String title, String msg) {
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        for (String phoneNumber : clients.keySet()) {
            executorService.submit(() -> {
                try {
                    clients.get(phoneNumber).announce(title, msg);
                } catch (RemoteException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        }

        executorService.shutdown();
    }


    @Override
    public  void logoutAll() {

        for (String phoneNumber : clients.keySet()) {
            try {

                clients.get(phoneNumber).serverShoutdownMessage();
                logout(phoneNumber, clients.get(phoneNumber));
            } catch (RemoteException e) {
                e.printStackTrace();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void updateContactList(String phoneNumber) throws RemoteException {
        CallBackClient callBackClient = clients.get(phoneNumber);
        try {
            System.out.println("updateContactList: in CallBackServerImp");
            callBackClient.updateContactList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void notifyStatusUpdate(UserDTO userDTO) throws RemoteException {
        for (String phoneNumber : clients.keySet()) {
            try {
                if(!userDTO.getPhoneNumber().equals(phoneNumber))
                    clients.get(phoneNumber).updateContactList();
            } catch (RemoteException e) {
                e.printStackTrace();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }


}

