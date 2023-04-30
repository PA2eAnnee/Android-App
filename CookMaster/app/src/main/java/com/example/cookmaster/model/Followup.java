package com.example.cookmaster.model;

import java.util.Date;

public class Followup {
    private int id;
    private int senderId;
    private String content;
    private Date sendDate;
    private int ticketId;

    public Followup(int id, int senderId, String content, Date sendDate, int ticketId) {
        this.id = id;
        this.senderId = senderId;
        this.content = content;
        this.sendDate = sendDate;
        this.ticketId = ticketId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }
}

