package com.example.wallet.ui.lk;

public class BankItemReciclerView {
    String BankName;
    String BalansCount;
    int ImageViewRes;

    public BankItemReciclerView(String bankName, String balansCount, int imageViewRes) {
        BankName = bankName;
        BalansCount = balansCount;
        ImageViewRes = imageViewRes;
    }

    public String getBankName() {
        return BankName;
    }

    public void setBankName(String bankName) {
        BankName = bankName;
    }

    public String getBalansCount() {
        return BalansCount;
    }

    public void setBalansCount(String balansCount) {
        BalansCount = balansCount;
    }

    public int getImageViewRes() {
        return ImageViewRes;
    }

    public void setImageViewRes(int imageViewRes) {
        ImageViewRes = imageViewRes;
    }
}
