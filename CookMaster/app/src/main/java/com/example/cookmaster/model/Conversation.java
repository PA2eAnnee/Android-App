package com.example.cookmaster.model;

import java.util.Date;

public class Conversation {
    private int id;
    private int receiverId;
    private Date sendDate;
    private int senderId;

    public Conversation(int id, int receiverId, Date sendDate, int senderId) {
        this.id = id;
        this.receiverId = receiverId;
        this.sendDate = sendDate;
        this.senderId = senderId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(int receiverId) {
        this.receiverId = receiverId;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }
}

