package com.example.wallet.ui.items;

public class ItemsItem {

    int id;
    String chanelavatar;
    String imageitems;
    String namechanel;
    String description;
    String wholikedsId;
    int likes;
    int dislikes;

    public ItemsItem() {
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public String getWholikedsId() {
        return wholikedsId;
    }

    public void setWholikedsId(String wholikedsId) {
        this.wholikedsId = wholikedsId;
    }

    public int getDislikes() {
        return dislikes;
    }

    public void setDislikes(int dislikes) {
        this.dislikes = dislikes;
    }

    public ItemsItem(int id, String chanelavatar, String imageitems, String namechanel, String description, int likes, int dislikes, String wholikedsId) {
        this.id = id;
        this.chanelavatar = chanelavatar;
        this.imageitems = imageitems;
        this.namechanel = namechanel;
        this.description = description;
        this.likes = likes;
        this.dislikes = dislikes;
        this.wholikedsId=wholikedsId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getChanelavatar() {
        return chanelavatar;
    }

    public void setChanelavatar(String chanelavatar) {
        this.chanelavatar = chanelavatar;
    }

    public String getImageitems() {
        return imageitems;
    }

    public void setImageitems(String imageitems) {
        this.imageitems = imageitems;
    }

    public String getNamechanel() {
        return namechanel;
    }

    public void setNamechanel(String namechanel) {
        this.namechanel = namechanel;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
