package com.example.cookmaster.model;

public class Goesto {
    private int id_user;
    private int id_event;

    public Goesto(int userId, int eventId) {
        this.id_user = userId;
        this.id_event = eventId;
    }

    public int getUserId() {
        return id_user;
    }

    public void setUserId(int userId) {
        this.id_user = userId;
    }

    public int getEventId() {
        return id_event;
    }

    public void setEventId(int eventId) {
        this.id_user = eventId;
    }
}

