package com.example.wallet.ui.items;

public class ItemsItem {
    int chanelAvatar;
    int imageItems;
    String nameChanel;
    String descriptionsItem;

    public ItemsItem(int chanelAvatar, int imageItems, String nameChanel, String descriptionsItem) {
        this.chanelAvatar = chanelAvatar;
        this.imageItems = imageItems;
        this.nameChanel = nameChanel;
        this.descriptionsItem = descriptionsItem;
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

    public String getDescriptionsItem() {
        return descriptionsItem;
    }

    public void setDescriptionsItem(String descriptionsItem) {
        this.descriptionsItem = descriptionsItem;
    }
}
