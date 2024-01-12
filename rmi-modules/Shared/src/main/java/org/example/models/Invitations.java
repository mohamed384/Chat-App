package org.example.models;

import java.io.Serializable;
import java.sql.Timestamp;

public class Invitations implements Serializable {
    String id;
    String senderPhoneNumber;
    String recipientPhoneNumber;
    InvitaionStatus invitaionStatus;
    Timestamp   timestamp;

    public Invitations(String senderPhoneNumber, String recipientPhoneNumber, InvitaionStatus invitaionStatus, Timestamp timestamp) {
        this.senderPhoneNumber = senderPhoneNumber;
        this.recipientPhoneNumber = recipientPhoneNumber;
        this.invitaionStatus = invitaionStatus;
        this.timestamp = timestamp;
    }
}
