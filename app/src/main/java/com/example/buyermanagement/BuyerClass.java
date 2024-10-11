package com.example.buyermanagement;

public class BuyerClass {
    private String buyerName;
    private String buyerMobNo;
    private String buyerAddress;
    private String buyerId;
    public BuyerClass(String buyerName, String buyerId, String buyerMobNo, String buyerAddress) {
        this.buyerName = buyerName;
        this.buyerId = buyerId;
        this.buyerMobNo = buyerMobNo;
        this.buyerAddress = buyerAddress;
    }

    public BuyerClass() {
    }

    public String getBuyerId() {
        return buyerId;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public String getBuyerMobNo() {
        return buyerMobNo;
    }

    public String getBuyerAddress() {
        return buyerAddress;
    }
}
