package com.example.buyermanagement;

public class OrderClass {
    private String productName;
    private String productCategory;
    private int productPrice;
    private int productQuantity;
    private String productModel;
    private String productId;
    private String date;
    private String link;

    public OrderClass(String productName, String productCategory, int productPrice, int productQuantity, String productModel, String productId, String date, String link) {
        this.productName = productName;
        this.productCategory = productCategory;
        this.productPrice = productPrice;
        this.productQuantity = productQuantity;
        this.productModel = productModel;
        this.productId = productId;
        this.date = date;
        this.link = link;
    }

    public OrderClass(){

    }

    public String getLink(){
        return link;
    }
    public String getProductName() {
        return productName;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public String getProductModel() {
        return productModel;
    }

    public String getProductId() {
        return productId;
    }

    public String getDate() {
        return date;
    }
}
