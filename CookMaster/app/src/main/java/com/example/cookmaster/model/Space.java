package com.example.cookmaster.model;

public class Space {
    private int id;
    private int maxAvailability;
    private int siteId;

    public Space(int id, int maxAvailability, int siteId) {
        this.id = id;
        this.maxAvailability = maxAvailability;
        this.siteId = siteId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMaxAvailability() {
        return maxAvailability;
    }

    public void setMaxAvailability(int maxAvailability) {
        this.maxAvailability = maxAvailability;
    }

    public int getSiteId() {
        return siteId;
    }

    public void setSiteId(int siteId) {
        this.siteId = siteId;
    }
}

