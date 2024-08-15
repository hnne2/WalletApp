package com.example.wallet.ui.home;

import java.util.Objects;

public class RequestItem {
    String AvatarRes;
    String name;
    String username;
    int id;

    public String getUsername() {
        return username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public RequestItem(String avatarRes, String name, String username, int id) {
        AvatarRes = avatarRes;
        this.name = name;
        this.username = username;
        this.id = id;
    }


    public String getAvatarRes() {
        return AvatarRes;
    }

    public void setAvatarRes(String avatarRes) {
        AvatarRes = avatarRes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
