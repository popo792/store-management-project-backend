package com.store.database.model;

import jakarta.persistence.*;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private double price;
    private int stockQty;

    public Product() {}

    public Product(String name, double price, int stockQty) {
        this.name = name;
        this.price = price;
        this.stockQty = stockQty;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getStockQty() { return stockQty; }
    public void setStockQty(int stockQty) { this.stockQty = stockQty; }

    public void decreaseStock(int quantity) {
        if (this.stockQty >= quantity) {
            this.stockQty -= quantity;
        } else {
            throw new RuntimeException("Not enough stock for: " + this.name); 
        }
    }
}