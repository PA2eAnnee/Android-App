package com.example.cookmaster.model;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Event {
    private int id;
    private String description;
    private String type;
    private int maxMembers;
    private int price;

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String end_date;
    String start_date;

    private int id_site;


    private int recipe_id;

    public Event(int id, String description, String type, int maxMembers, int price, String startDate, String endDate, int siteId) {
        this.id = id;
        this.description = description;
        this.type = type;
        this.maxMembers = maxMembers;
        this.price = price;
        this.start_date = startDate;
        this.end_date = endDate;
        this.id_site = siteId;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
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

    public String getStartDate() {
        return start_date;
    }

    public void setStartDate(String startDate) {
        this.start_date = startDate;
    }

    public String getEndDate() {
        return end_date;
    }

    public void setEndDate(String endDate) {
        this.end_date = endDate;
    }

    public int getSiteId() {
        return id_site;
    }

    public void setSiteId(int siteId) {
        this.id_site = siteId;
    }

    public int getRecipe_id() {
        return recipe_id;
    }

    public void setRecipe_id(int recipe_id) {
        this.recipe_id = recipe_id;
    }
}

