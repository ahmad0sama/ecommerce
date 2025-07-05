package model;

public class NonPerishableShippableProduct extends NonPerishableProduct implements Shippable {
    private final double weightInGrams;

    public NonPerishableShippableProduct(String name, double price, int quantity, double weightInGrams) {
        super(name, price, quantity);
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
