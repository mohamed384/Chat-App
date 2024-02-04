package org.example.models;

import java.sql.Timestamp;

public class Message {
    private int messageID;
    private String senderID;
    private int chatID;
    private String messageContent;
    private boolean isAttachment;
    private Timestamp timestamp;

  //  private byte[] attachment;

    public Message(){}

//    public Message(String senderID, int chatID, byte[] attachment) {
//        this.senderID = senderID;
//        this.chatID = chatID;
//        this.attachment = attachment;
//        isAttachment = true; //TODO: check if this is necessary
//    }

    public Message(String senderID, int receiverID, String messageContent, boolean isAttachment) {
        this.senderID = senderID;
        this.chatID = receiverID;
        this.messageContent = messageContent;
        this.isAttachment = isAttachment;
    }

    public Message( String messageContent,String senderID, int chatID, Timestamp timestamp) {
        this.senderID = senderID;
        this.chatID = chatID;
        this.messageContent = messageContent;
        this.timestamp = timestamp;
    }

    public int getMessageID() {
        return messageID;
    }

    public void setMessageID(int messageID) {
        this.messageID = messageID;
    }

    public String getSenderID() {
        return senderID;
    }

    public void setSenderID(String senderID) {
        this.senderID = senderID;
    }

    public int getChatID() {
        return chatID;
    }

    public void setChatID(int chatID) {
        this.chatID = chatID;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public boolean isAttachment() {
        return isAttachment;
    }

    public void setIsAttachment(boolean attachment) {
        isAttachment = attachment;
    }
//    public byte[] getAttachment() {
//        return attachment;
//    }
//
//    public void setAttachment(byte[] attachment) {
//        this.attachment = attachment;
//    }
}
