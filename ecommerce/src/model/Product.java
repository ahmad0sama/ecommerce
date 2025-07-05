package model;

import java.time.LocalDate;

public abstract class Product {
    private final String name;
    private final double price;
    private int quantity;

    public Product(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    // getters
    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }

    //Make sure the quantity is sufficient
    public boolean isAvailable(int amount) {
        return quantity >= amount;
    }

    //update product quantity
    public void reduceQuantity(int amount) {
        if (!isAvailable(amount)) {
            throw new IllegalStateException("Insufficient stock for: " + name);
        }
        quantity -= amount;
    }

    public abstract boolean isExpired();
}
