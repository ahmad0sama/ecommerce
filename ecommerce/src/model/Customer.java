package model;

import java.util.ArrayList;
import java.util.List;

public class Customer {
    private final String name;
    private double balance;
    private final List<CartItem> cart = new ArrayList<>();

    public Customer(String name, double balance) {
        this.name = name;
        this.balance = balance;
    }

    public void addToCart(Product product, int quantity) {
        if (!product.isAvailable(quantity)) {
            throw new IllegalArgumentException("Not enough stock for: " + product.getName());
        }
        cart.add(new CartItem(product, quantity));
    }

    public void clearCart() {
        cart.clear();
    }

    public void deductBalance(double amount) {
        if (amount > balance) {
            throw new IllegalStateException("Insufficient funds");
        }
        balance -= amount;
    }

    public String getName() { return name; }
    public double getBalance() { return balance; }
    public List<CartItem> getCart() { return new ArrayList<>(cart); }
}
