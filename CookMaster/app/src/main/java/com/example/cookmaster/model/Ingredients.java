package com.example.cookmaster.model;

public class Ingredients {
    private int ID;
    private String Name;
    private int Quantity;
    private String unity;

    public Ingredients(int ID, String name, int quantity, String unity) {
        this.ID = ID;
        this.Name = name;
        this.Quantity = quantity;
        this.unity = unity;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        this.Quantity = quantity;
    }

    public String getUnity() {
        return unity;
    }

    public void setUnity(String unity) {
        this.unity = unity;
    }
}
