package org.example.models;

import java.io.Serializable;
import java.sql.Timestamp;

public class Invitations implements Serializable {
    String id;
    String senderPhoneNumber;
    String recipientPhoneNumber;
    InvitaionStatus invitaionStatus;

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

    public InvitaionStatus getInvitaionStatus() {
        return invitaionStatus;
    }

    public void setInvitaionStatus(InvitaionStatus invitaionStatus) {
        this.invitaionStatus = invitaionStatus;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    Timestamp   timestamp;

    public Invitations(String senderPhoneNumber, String recipientPhoneNumber, InvitaionStatus invitaionStatus, Timestamp timestamp) {
        this.senderPhoneNumber = senderPhoneNumber;
        this.recipientPhoneNumber = recipientPhoneNumber;
        this.invitaionStatus = invitaionStatus;
        this.timestamp = timestamp;
    }
}
