package com.example.cookmaster.model;

public class Contains {
    private int articleId;
    private int orderId;

    public Contains(int articleId, int orderId) {
        this.articleId = articleId;
        this.orderId = orderId;
    }

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
}

