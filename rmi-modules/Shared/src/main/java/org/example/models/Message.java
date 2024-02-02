package org.example.models;

public class Message {
    private int messageID;
    private String senderID;
    private int chatID;
    private String messageContent;
    private boolean isAttachment;
    public Message(){}

    public Message(String senderID, int receiverID, String messageContent, boolean isAttachment) {
        this.senderID = senderID;
        this.chatID = receiverID;
        this.messageContent = messageContent;
        this.isAttachment = isAttachment;
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

    public void setAttachment(boolean attachment) {
        isAttachment = attachment;
    }
}
