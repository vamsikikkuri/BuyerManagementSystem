package com.example.buyermanagement;

public class CartClass {
    private String productName;
    private String productCategory;
    private int productPrice;
    private int productQuantity;
    private String productModel;
    private String productId;

    public CartClass(){}

    public CartClass(String productName, String productCategory, int productPrice, int productQuantity, String productModel, String productId) {
        this.productName = productName;
        this.productCategory = productCategory;
        this.productPrice = productPrice;
        this.productQuantity = productQuantity;
        this.productModel = productModel;
        this.productId = productId;
    }

    public String getProductId() {
        return productId;
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
}
