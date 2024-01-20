package org.example.models;
import java.sql.Timestamp;
public class UserNotification {
    private String notificationID;
    private String receiverID;
    private String senderID;
    private String notificationMessage;
    private Timestamp notificationSentDate;

    public UserNotification(String receiverID, String senderID, String notificationMessage, Timestamp notificationSentDate) {
        this.receiverID = receiverID;
        this.senderID = senderID;
        this.notificationMessage = notificationMessage;
        this.notificationSentDate = notificationSentDate;
    }

    public UserNotification() {
        this.notificationMessage = "Hi, I want to connect with you!";
    }

    public String getNotificationID() {
        return notificationID;
    }

    public void setNotificationID(String notificationID) {
        this.notificationID = notificationID;
    }

    public String getReceiverID() {
        return receiverID;
    }

    public void setReceiverID(String receiverID) {
        this.receiverID = receiverID;
    }

    public String getSenderID() {
        return senderID;
    }

    public void setSenderID(String senderID) {
        this.senderID = senderID;
    }

    public String getNotificationMessage() {
        return notificationMessage;
    }

    public void setNotificationMessage(String notificationMessage) {
        this.notificationMessage = notificationMessage;
    }

    public Timestamp getNotificationSentDate() {
        return notificationSentDate;
    }

    public void setNotificationSentDate(Timestamp notificationSentDate) {
        this.notificationSentDate = notificationSentDate;
    }
}
