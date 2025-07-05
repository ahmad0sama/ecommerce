package service;

import model.CartItem;
import model.Shippable;
import java.util.List;

public class ShippingService {
    public void processShipment(List<CartItem> shippableItems) {
        System.out.println("** Shipment notice **");
        double totalWeightGrams = 0;

        for (CartItem item : shippableItems) {
            Shippable shippable = (Shippable) item.getProduct();
            double totalWeightInGrams = item.getQuantity() * shippable.getWeightInGrams() ;
            System.out.printf("%dx %-15s %.0fg%n",item.getQuantity(), item.getProduct().getName(),totalWeightInGrams);
            totalWeightGrams += shippable.getWeightInGrams() * item.getQuantity();
        }

        System.out.printf("Total package weight %.1fkg%n%n", totalWeightGrams / 1000);
    }
}
