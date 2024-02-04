package org.example.DTOs;

import java.io.Serializable;
import java.util.Arrays;

public class ChatDTO implements Serializable {
    private int chatID;
    private String chatName;
    private byte[] chatImage;
    private String ReceiverName;
    private int adminID;
    private byte[] receiverImage;



    public String getReceiverName() {
        return ReceiverName;
    }

    public void setReceiverName(String receiverName) {
        ReceiverName = receiverName;
    }


    public byte[] getReceiverImage() {
        return receiverImage;
    }

    public void setReceiverImage(byte[] receiverImage) {
        this.receiverImage = receiverImage;
    }

    public ChatDTO(String chatName, byte[] chatImage, String ReceiverName, byte[] receiverImage, int adminID) {

        this.chatName = chatName;
        this.chatImage = chatImage;
        this.adminID = adminID;
        this.ReceiverName = ReceiverName;
        this.receiverImage = receiverImage;
    }

    public int getChatID() {
        return chatID;
    }

    public void setChatID(int chatID) {
        this.chatID = chatID;
    }

    public String getChatName() {
        return chatName;
    }

    public void setChatName(String chatName) {
        this.chatName = chatName;
    }

    public byte[] getChatImage() {
        return chatImage;
    }

    public void setChatImage(byte[] chatImage) {
        this.chatImage = chatImage;
    }

    public int getAdminID() {
        return adminID;
    }

    public void setAdminID(int adminID) {
        this.adminID = adminID;
    }
}



