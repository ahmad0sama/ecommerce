package model;

import java.time.LocalDate;

public class PerishableShippableProduct extends PerishableProduct implements Shippable {
    private final double weightInGrams;

    public PerishableShippableProduct(String name, double price, int quantity,
                                      LocalDate expiryDate, double weightInGrams) {
        super(name, price, quantity, expiryDate);
        this.weightInGrams = weightInGrams;
    }

    @Override
    public double getWeightInGrams() {
        return weightInGrams;
    }

    @Override
    public String getWeightDisplay() {
        return String.format("%.0fg", weightInGrams);
    }
}
