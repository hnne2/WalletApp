package com.example.wallet.ui.home;

public class FrendsItem {
    int AvatarRes;
    String name;
    String Place;
    String Capital;
    String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public FrendsItem(int avatarRes, String name, String username,String place, String capital) {
        this.AvatarRes = avatarRes;
        this.name = name;
        this.Place = place;
        this.Capital = capital;
        this.username=username;

    }

    public int getAvatarRes() {
        return AvatarRes;
    }

    public void setAvatarRes(int avatarRes) {
        AvatarRes = avatarRes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlace() {
        return Place;
    }

    public void setPlace(String place) {
        Place = place;
    }

    public String getCapital() {
        return Capital;
    }

    public void setCapital(String capital) {
        Capital = capital;
    }
}
