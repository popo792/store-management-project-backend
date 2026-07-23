package com.store.database.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

import com.store.database.repository.OrderRepository;

import java.util.ArrayList;

@Entity
@Table(name = "store_orders")
public class Order {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @ManyToMany
    @JoinTable(
        name = "order_items",
        joinColumns = @JoinColumn(name = "order_id"),
        inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> items = new ArrayList<>();

    private LocalDateTime orderDate;

    public Order() {}

    public Order(Customer customer, Employee employee) {
        this.customer = customer;
        this.employee = employee;
        this.orderDate = LocalDateTime.now();
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }

    public Employee getHandledBy() { return employee; }
    public void setHandledBy(Employee employee) { this.employee = employee; }

    public List<Product> getItems() { return items; }
    public void setItems(List<Product> items) { this.items = items; }

    public LocalDateTime getOrderDate() { return orderDate; }
    public void setOrderDate(LocalDateTime orderDate) { this.orderDate = orderDate; }

    public void addProduct(Product product) {
        this.items.add(product);
        product.decreaseStock(1); 
    }
}