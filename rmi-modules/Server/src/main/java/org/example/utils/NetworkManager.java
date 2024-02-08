package org.example.utils;

import org.example.Utils.StubContext;
import org.example.callBackImp.CallBackServerImp;
import org.example.controller.*;
import org.example.interfaces.*;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import static org.example.Utils.StubContext.stubs;

public class NetworkManager {
    private static NetworkManager instance;

    // Private constructor to prevent instantiation from outside
    private NetworkManager() {
        StubContext.getInstance();
    }

    // Public static method to get the singleton instance
    public static NetworkManager getInstance() {
        if (instance == null) {
            synchronized (NetworkManager.class) {
                if (instance == null) {
                    System.out.println("Network Manager Created");
                    instance = new NetworkManager();
                }
            }
        }
        return instance;
    }

    public void unbindAll() {
        StubContext.isRunning = false;
        System.out.println("UUUUnbindAll function is called");

        try {
            for (String name : StubContext.stubs.keySet()) {
                StubContext.registry.unbind(name);
                java.rmi.server.UnicastRemoteObject.unexportObject(stubs.get(name), true);
            }
            stubs.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void bindAll() {
        try {
            StubContext.isRunning = true;
            System.out.println("bindAll function is called");
            UserAuthentication userAuthenticationStub = new UserController();
            UserSendNotification userSendNotificationStub = new UserNotificationController();
            UserContact contactControllerStub = new ContactController();
            ChatRMI ChatControllerStub = new ChatController();
            CallBackServer callBackServer = new CallBackServerImp();
            GroupChatRMI groupChatRMI = new GroupChatController();
            MessageRMI messageRMI = new MessageController();
            StubContext.addStub("UserAuthenticationStub", userAuthenticationStub);
            StubContext.addStub("UserSendNotificationStub", userSendNotificationStub);
            StubContext.addStub("UserContactStub", contactControllerStub);
            StubContext.addStub("ChatControllerStub", ChatControllerStub);
            StubContext.addStub("CallBackServerStub", callBackServer);
            StubContext.addStub("GroupChatControllerStub", groupChatRMI);
            StubContext.addStub("MessageControllerStub", messageRMI);
        } catch (RemoteException ex) {
            ex.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
