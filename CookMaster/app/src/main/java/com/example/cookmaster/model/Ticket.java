package com.example.cookmaster.model;

import java.util.Date;

public class Ticket {
    private int id;
    private String title;
    private String description;
    private Date creationDate;
    private int senderId;

    public Ticket(int id, String title, String description, Date creationDate, int senderId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.creationDate = creationDate;
        this.senderId = senderId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }
}

