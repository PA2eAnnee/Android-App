package com.example.cookmaster.model;

import java.util.Date;

public class Event {
    private int id;
    private String description;
    private int type;
    private int maxMembers;
    private int price;
    private Date startDate;
    private Date endDate;
    private int siteId;

    public Event(int id, String description, int type, int maxMembers, int price, Date startDate, Date endDate, int siteId) {
        this.id = id;
        this.description = description;
        this.type = type;
        this.maxMembers = maxMembers;
        this.price = price;
        this.startDate = startDate;
        this.endDate = endDate;
        this.siteId = siteId;
    }

    // getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getMaxMembers() {
        return maxMembers;
    }

    public void setMaxMembers(int maxMembers) {
        this.maxMembers = maxMembers;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getSiteId() {
        return siteId;
    }

    public void setSiteId(int siteId) {
        this.siteId = siteId;
    }
}

