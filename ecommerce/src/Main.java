import model.*;
import service.CheckoutService;
import service.ShippingService;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {

        // Create products
        Product cheese = new PerishableShippableProduct(
                "Cheese", 100, 100, LocalDate.now().plusDays(10), 400);

        Product biscuits = new NonPerishableShippableProduct(
                "Biscuits", 150, 50, 700);

        Product tv = new NonPerishableShippableProduct(
                "TV", 5000, 10, 15500);

        Product scratchCard = new NonPerishableProduct(
                "Mobile Scratch Card", 10, 200);

        // Create customer
        Customer customer = new Customer("John Doe", 10000);

        // Add to cart
        customer.addToCart(cheese, 2);
        customer.addToCart(biscuits, 1);
        customer.addToCart(scratchCard, 3);
        customer.addToCart(tv,1);

        // Checkout process
        ShippingService shippingService = new ShippingService();
        CheckoutService checkoutService = new CheckoutService(shippingService);

        try {
            checkoutService.processCheckout(customer);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}