package org.example.models;

import java.sql.Timestamp;

public class Chat {
    private int chatID;
    private String chatName;
    private byte[] chatImage;
    private String adminID;

    public String getAdminID() {
        return adminID;
    }

    public void setAdminID(String adminID) {
        this.adminID = adminID;
    }

    public String getReceiverName() {
        return ReceiverName;
    }

    public void setReceiverName(String receiverName) {
        ReceiverName = receiverName;
    }

    private String ReceiverName;

    public byte[] getReceiverImage() {
        return receiverImage;
    }

    public void setReceiverImage(byte[] receiverImage) {
        this.receiverImage = receiverImage;
    }

    private byte[] receiverImage;

    public Chat (){}
    public Chat(String chatName, byte[] chatImage,String ReceiverName,byte[] receiverImage) {

        this.chatName = chatName;
        this.chatImage = chatImage;
        this.ReceiverName=ReceiverName;
        this.receiverImage=receiverImage;
    }
    public Chat(String chatName, byte[] chatImage) {

        this.chatName = chatName;
        this.chatImage = chatImage;
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



}
