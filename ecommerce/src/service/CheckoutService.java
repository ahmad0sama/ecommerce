package service;

import model.CartItem;
import model.Customer;
import model.Product;
import model.Shippable;

import java.util.ArrayList;
import java.util.List;

// Service class handling the checkout process for customer orders
public class CheckoutService {
    private static final double SHIPPING_FEE_PER_ITEM = 30;
    private final ShippingService shippingService;

    public CheckoutService(ShippingService shippingService) {
        this.shippingService = shippingService;
    }

    //Processes the complete checkout flow for a customer
    public void processCheckout(Customer customer) {
        validateCheckout(customer);

        double subtotal = calculateSubtotal(customer);
        double shippingFee = calculateShippingFee(customer);
        double total = subtotal + shippingFee;

        processPayment(customer, total);
        updateInventory(customer);
        shipEligibleItems(customer);
        printReceipt(customer, subtotal, shippingFee, total);

        customer.clearCart();
    }

    //Validates the customer's cart before processing checkout
    private void validateCheckout(Customer customer) {
        if (customer.getCart().isEmpty()) {
            throw new IllegalStateException("Checkout failed: Cart is empty");
        }

        for (CartItem item : customer.getCart()) {
            Product product = item.getProduct();
            if (product.getQuantity() < item.getQuantity()) {
                throw new IllegalStateException(
                        "Checkout failed: Insufficient stock for " + product.getName()
                );
            }
            if (product.isExpired()) {
                throw new IllegalStateException(
                        "Checkout failed: " + product.getName() + " has expired"
                );
            }
        }
    }

    //Calculates the subtotal of all items in the cart
    private double calculateSubtotal(Customer customer) {
        double total = 0;
        for (CartItem item : customer.getCart()) {
            total += item.getProduct().getPrice() * item.getQuantity();
        }
        return total;
    }

    //Calculates shipping fees based on shippable items
    private double calculateShippingFee(Customer customer) {
        int shippableCount = 0;
        for (CartItem item : customer.getCart()) {
            if (item.getProduct() instanceof Shippable) {
                shippableCount++;
            }
        }
        return shippableCount * SHIPPING_FEE_PER_ITEM;
    }

    private void processPayment(Customer customer, double total) {
        customer.deductBalance(total);
    }

    //Updates inventory levels after successful checkout
    private void updateInventory(Customer customer) {
        for (CartItem item : customer.getCart()) {
            item.getProduct().reduceQuantity(item.getQuantity());
        }
    }

    //Prints a formatted receipt of the transaction
    private void printReceipt(Customer customer, double subtotal, double shippingFee, double total) {
        System.out.println("** Checkout receipt **");
        for (CartItem item : customer.getCart()) {
            System.out.printf("%dx %-15s %.0f%n",item.getQuantity(), item.getProduct().getName(),item.getLineTotal());
        }
        System.out.println("----------------------");
        System.out.printf("Subtotal         %.0f%n", subtotal);
        System.out.printf("Shipping         %.0f%n", shippingFee);
        System.out.printf("Amount           %.0f%n%n", total);
    }

    //Processes shipment for all shippable items in the cart
    private void shipEligibleItems(Customer customer) {
        List<CartItem> shippableItems = new ArrayList<>();
        for (CartItem item : customer.getCart()) {
            if (item.getProduct() instanceof Shippable) {
                shippableItems.add(item);
            }
        }

        if (!shippableItems.isEmpty()) {
            shippingService.processShipment(shippableItems);
        }
    }
}
