package com.example.wallet.ui.items;

public class ItemsItem {

    int id;
    int chanelAvatar;
    int imageItems;
    String nameChanel;
    String description;

    public ItemsItem() {
    }

    public ItemsItem(int id, int chanelAvatar, int imageItems, String nameChanel, String description) {
        this.id = id;
        this.chanelAvatar = chanelAvatar;
        this.imageItems = imageItems;
        this.nameChanel = nameChanel;
        this.description = description;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getChanelAvatar() {
        return chanelAvatar;
    }

    public void setChanelAvatar(int chanelAvatar) {
        this.chanelAvatar = chanelAvatar;
    }

    public int getImageItems() {
        return imageItems;
    }

    public void setImageItems(int imageItems) {
        this.imageItems = imageItems;
    }

    public String getNameChanel() {
        return nameChanel;
    }

    public void setNameChanel(String nameChanel) {
        this.nameChanel = nameChanel;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
