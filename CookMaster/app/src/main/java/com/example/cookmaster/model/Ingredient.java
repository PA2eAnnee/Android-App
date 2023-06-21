package com.example.cookmaster.model;

public class Ingredient {
    private int ID;
    private String name;

    public Ingredient(int ID, String name) {
        this.ID = ID;
        this.name = name;
    }

    // Getter and Setter methods
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
