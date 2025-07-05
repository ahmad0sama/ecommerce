package model;
import java.time.LocalDate;

public class PerishableProduct extends Product {
    private final LocalDate expiryDate;

    public PerishableProduct(String name, double price, int quantity, LocalDate expiryDate) {
        super(name, price, quantity);
        this.expiryDate = expiryDate;
    }


    public boolean isExpired() {
        return LocalDate.now().isAfter(expiryDate);
    }
}
