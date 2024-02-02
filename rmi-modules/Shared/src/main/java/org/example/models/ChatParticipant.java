package org.example.models;


public class ChatParticipant {
    private int chatID;
    private String participantUserID;

    public ChatParticipant(int chatID, String participantUserID) {
        this.participantUserID = participantUserID;
        this.chatID=chatID;
    }

    public int getChatID() {
        return chatID;
    }

    public void setChatID(int chatID) {
        this.chatID = chatID;
    }

    public String getParticipantUserID() {
        return participantUserID;
    }

    public void setParticipantUserID(String participantUserID) {
        this.participantUserID = participantUserID;
    }

}
