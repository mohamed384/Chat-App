package org.example.DTOs;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Arrays;

public class MessageDTO implements Serializable {
    private int messageID;
    private String senderID;
    private int chatID;
    private String messageContent;

    @Override
    public String toString() {
        return "MessageDTO{" +
                "messageID=" + messageID +
                ", senderID='" + senderID + '\'' +
                ", chatID=" + chatID +
                ", messageContent='" + messageContent + '\'' +
                ", isAttachment=" + isAttachment +
                ", timestamp=" + timestamp +
                ", attachment=" + Arrays.toString(attachment) +
                '}';
    }

    private boolean isAttachment;
    private Timestamp timestamp;


    private byte[] attachment;
    public MessageDTO(){
    }


    public byte[] getAttachment() {
        return attachment;
    }

    public void setAttachment(byte[] attachment) {
        this.attachment = attachment;
    }





    public MessageDTO( String messageContent,String senderID, int chatID, Timestamp timestamp) {
        this.senderID = senderID;
        this.chatID = chatID;
        this.messageContent = messageContent;
        this.timestamp = timestamp;
    }
    public MessageDTO( String messageContent,String senderID, int chatID, Timestamp timestamp,byte[]attachment,boolean isAttachment) {
        this.senderID = senderID;
        this.chatID = chatID;
        this.messageContent = messageContent;
        this.attachment= attachment;
        this.isAttachment=isAttachment;
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

    public boolean getIsAttachment() {
        return isAttachment;
    }

    public void setIsAttachment(boolean isAttachment){
        this.isAttachment = isAttachment;
    }

}
