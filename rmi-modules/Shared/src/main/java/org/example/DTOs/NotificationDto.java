package org.example.DTOs;

import java.io.Serializable;
import java.sql.Timestamp;

public class NotificationDto implements Serializable {

    private String message;
    private String phoneSender;
    private String name;
    private Timestamp notificationSentDate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getNotificationSentDate() {
        return notificationSentDate;
    }

    public void setNotificationSentDate(Timestamp notificationSentDate) {
        this.notificationSentDate = notificationSentDate;
    }


    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    private byte[] picture;


    public String getPhoneSender() {
        return phoneSender;
    }

    public void setPhoneSender(String phoneSender) {
        this.phoneSender = phoneSender;
    }



    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
