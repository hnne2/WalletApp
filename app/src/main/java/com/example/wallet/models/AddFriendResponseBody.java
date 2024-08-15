package com.example.wallet.models;

public class AddFriendResponseBody {
    int firstId;
    // String passwordWhoAdd;
    int secondId;


    public AddFriendResponseBody(){}
    public AddFriendResponseBody(int firstId, int secondId) {
        this.firstId = firstId;
        this.secondId = secondId;
    }

    public void setFirstId(int firstId) {
        this.firstId = firstId;
    }

    public void setSecondId(int secondId) {
        this.secondId = secondId;
    }

    public int getFirstId() {
        return firstId;
    }

    public int getSecondId() {
        return secondId;
    }
}
