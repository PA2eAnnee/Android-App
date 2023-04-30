package com.example.cookmaster.model;

import java.util.Date;

public class Book {
    private int userId;
    private int spaceId;
    private Date bookDate;

    public Book(int userId, int spaceId, Date bookDate) {
        this.userId = userId;
        this.spaceId = spaceId;
        this.bookDate = bookDate;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getSpaceId() {
        return spaceId;
    }

    public void setSpaceId(int spaceId) {
        this.spaceId = spaceId;
    }

    public Date getBookDate() {
        return bookDate;
    }

    public void setBookDate(Date bookDate) {
        this.bookDate = bookDate;
    }
}
