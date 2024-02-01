package org.example.models;

import java.sql.Timestamp;

public class Chat {
    private int chatID;
    private String chatName;
    private byte[] chatImage;
    private int adminID;


    public Chat(String chatName, byte[] chatImage, int adminID) {

        this.chatName = chatName;
        this.chatImage = chatImage;
        this.adminID = adminID;
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
