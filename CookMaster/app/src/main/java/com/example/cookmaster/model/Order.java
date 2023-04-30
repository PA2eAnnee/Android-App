package com.example.cookmaster.model;

import java.util.Date;

public class Order {
    private int id;
    private Date sendDate;
    private int totalPrice;
    private int userId;

    public Order(int id, Date sendDate, int totalPrice, int userId) {
        this.id = id;
        this.sendDate = sendDate;
        this.totalPrice = totalPrice;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}

