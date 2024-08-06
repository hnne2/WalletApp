package com.example.wallet.ui.home;

import java.util.Objects;

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
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FrendsItem that = (FrendsItem) o;
        return AvatarRes == that.AvatarRes &&
                Objects.equals(name, that.name) &&
                Objects.equals(Place, that.Place) &&
                Objects.equals(Capital, that.Capital) &&
                Objects.equals(username, that.username);
    }

    // Переопределяем метод hashCode
    @Override
    public int hashCode() {
        return Objects.hash(AvatarRes, name, Place, Capital, username);
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
