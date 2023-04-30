package com.example.cookmaster.model;

import java.util.Date;

public class Message {
    private int id;
    private int senderId;
    private String content;
    private Date sendDate;
    private int conversationId;

    public Message(int id, int senderId, String content, Date sendDate, int conversationId) {
        this.id = id;
        this.senderId = senderId;
        this.content = content;
        this.sendDate = sendDate;
        this.conversationId = conversationId;
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

    public int getConversationId() {
        return conversationId;
    }

    public void setConversationId(int conversationId) {
        this.conversationId = conversationId;
    }
}

