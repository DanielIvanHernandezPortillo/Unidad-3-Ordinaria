package com.example.danielarguello.orders.model;

/**
 * Created by tacho on 7/02/17.
 */

public class Product {
    private String idProduct;
    private String name;
    private float price;
    private int stock;

    public Product(String idProduct, String name, float price, int stock) {
        this.idProduct = idProduct;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public Product() {
        this("","",0.0F, 0);
    }

    public String getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(String idProduct) {
        this.idProduct = idProduct;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "Product{" +
                "idProduct='" + idProduct + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                '}';
    }
}
