package com.example.cookmaster;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cookmaster.model.User;

public class BaseActivity extends AppCompatActivity {
    protected User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

