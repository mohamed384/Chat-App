package org.example.models;
import java.sql.Timestamp;

public class Message {
    private int id;
    private String senderPhoneNumber;
    private String recipientPhoneNumber;
    private String messageContent;
    private Timestamp timestamp;
    private Boolean isRead;
    private String groupId;

    // Constructors

    public Message() {
    }

    public Message(String senderPhoneNumber, String recipientPhoneNumber, String messageContent, Timestamp timestamp, Boolean isRead, String groupId) {
        this.senderPhoneNumber = senderPhoneNumber;
        this.recipientPhoneNumber = recipientPhoneNumber;
        this.messageContent = messageContent;
        this.timestamp = timestamp;
        this.isRead = isRead;
        this.groupId = groupId;
    }

    // Getter and Setter methods

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSenderPhoneNumber() {
        return senderPhoneNumber;
    }

    public void setSenderPhoneNumber(String senderPhoneNumber) {
        this.senderPhoneNumber = senderPhoneNumber;
    }

    public String getRecipientPhoneNumber() {
        return recipientPhoneNumber;
    }

    public void setRecipientPhoneNumber(String recipientPhoneNumber) {
        this.recipientPhoneNumber = recipientPhoneNumber;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public Boolean getIsRead() {
        return isRead;
    }

    public void setIsRead(Boolean isRead) {
        this.isRead = isRead;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    // toString method

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", senderPhoneNumber='" + senderPhoneNumber + '\'' +
                ", recipientPhoneNumber='" + recipientPhoneNumber + '\'' +
                ", messageContent='" + messageContent + '\'' +
                ", timestamp=" + timestamp +
                ", isRead=" + isRead +
                ", groupId='" + groupId + '\'' +
                '}';
    }
}

