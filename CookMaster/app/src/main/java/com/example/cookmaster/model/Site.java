package com.example.cookmaster.model;

public class Site {
    private int id;
    private String address;
    private String postcode;

    public Site(int id, String address, String postcode) {
        this.id = id;
        this.address = address;
        this.postcode = postcode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }
}

