package com.example.cookmaster;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cookmaster.model.Users;

public class BaseActivity extends AppCompatActivity {
    protected Users users;

    public Users getUser() {
        return users;
    }

    public void setUser(Users users) {
        this.users = users;
    }
}

