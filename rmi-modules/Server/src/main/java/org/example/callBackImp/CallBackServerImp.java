package org.example.callBackImp;
import org.example.DAO.ChatDAOImpl;
import org.example.DAO.MessageDAOImpl;
import org.example.DTOs.MessageDTO;
import org.example.DTOs.UserDTO;
import org.example.interfaces.CallBackClient;
import org.example.interfaces.CallBackServer;
import org.example.interfaces.MessageRMI;
import org.example.models.Message;
import org.example.services.MessageService;
import org.example.utils.MessageDAOSaveHelper;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CallBackServerImp extends UnicastRemoteObject implements CallBackServer , Serializable {

    static Map<String, CallBackClient> clients = new HashMap<>();
    static Map<String, CallBackClient> logoutClients = new HashMap<>();
    private final MessageDAOImpl messageDAO;
    private final ChatDAOImpl chatDAO;
    private final  MessageService messageService ;
    public static int getClients() {
        return clients.size();
    }

    public CallBackServerImp() throws RemoteException {
        messageDAO  = new MessageDAOImpl();
        chatDAO = new ChatDAOImpl();
        messageService = new MessageService();
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

    public boolean isOnline(String clientPhoneNumber) {
        if(clients.containsKey(clientPhoneNumber)){
            return true;
        }else  {
            return  false;
        }

    }

    @Override
    public boolean logout(String phoneNumber) {
        System.out.println("logout: "+phoneNumber);
        clients.remove(phoneNumber);
        return true;
    }

    @Override
    public void sendMsg(MessageDTO messageDTO) throws RemoteException {

        Message message = new Message();
        MessageDAOSaveHelper test;
        MessageDTO ee = null;
        if (!messageDTO.isAttachment()) {

            message.setSenderID(messageDTO.getSenderID());
            message.setMessageContent(messageDTO.getMessageContent());
            message.setIsAttachment(false);
            message.setChatID(messageDTO.getChatID());
             test = messageDAO.createWithHandler(message);
            System.out.println("this is send mesg 3adyaaa call back server  " + test);
        } else {
            message.setSenderID(messageDTO.getSenderID());
            message.setMessageContent(messageDTO.getMessageContent());
            message.setIsAttachment(true);
            message.setChatID(messageDTO.getChatID());
            message.setAttachment(messageDTO.getAttachment());
            System.out.println(message.toString());
             test = messageDAO.createWithHandler(message);
            System.out.println("this is send mesg FILEEE call back server  " + test);
        }

        List<String> participants = chatDAO.getChatParticipants(messageDTO.getSenderID(), messageDTO.getChatID());

        if(messageDTO.isAttachment()) {
            ee = messageService.retrieveFileFromDB(test.getGeneratedKey());
            System.out.println("######################");
            System.out.println(ee.getMessageContent());
            System.out.println(ee.getSenderID());
            System.out.println(ee.isAttachment());
            System.out.println("test Key "+test.getGeneratedKey());
        }

        for (String senderPhoneNumber :participants ) {
            CallBackClient callBackClient = clients.get(senderPhoneNumber);
            try {
                if(callBackClient != null) {

                    if(messageDTO.isAttachment()){
                        callBackClient.receiveMsg(ee);
                    }else {
                        callBackClient.receiveMsg(messageDTO);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
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

  public   void serverStandUpMessage(){
        for (String phoneNumber : logoutClients.keySet()) {
            try {
                logoutClients.get(phoneNumber).serveStandUp();
            } catch (RemoteException e) {
                e.printStackTrace();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public  void logoutAll() {

        for (String phoneNumber : clients.keySet()) {
            try {

                clients.get(phoneNumber).serverShutdownMessage();
                logoutClients.put(phoneNumber,clients.get(phoneNumber));
                logout(phoneNumber);
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

