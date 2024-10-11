package com.example.buyermanagement;

import android.widget.ProgressBar;

public class ProductClass {
    private String productId, productCategory, productName, productModel;
    private int productPrice, productStock;

    public ProductClass(){

    }

    public ProductClass(String productId, String productCategory, String productName, String productModel, int productPrice, int productStock) {
        this.productId = productId;
        this.productCategory = productCategory;
        this.productName = productName;
        this.productModel = productModel;
        this.productPrice = productPrice;
        this.productStock = productStock;
    }

    public ProductClass(int newPrice, int newStock) {
        productPrice = newPrice;
        productStock = newStock;

    }

    public String getProductId() {
        return productId;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductModel() {
        return productModel;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public int getProductStock() {
        return productStock;
    }
}
