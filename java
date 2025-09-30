import java.util.*;

// Encapsulated Product class
class Product {
    private String name;
    private double price;
    private int quantity;

    public Product(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    // Getters
    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getTotalPrice() {
        return price * quantity;
    }
}

// Discount base class (polymorphism)
abstract class Discount {
    public abstract double applyDiscount(List<Product> products);
}

// Festive discount: 10% off total
class FestiveDiscount extends Discount {
    public double applyDiscount(List<Product> products) {
        double total = 0;
        for (Product p : products) {
            total += p.getTotalPrice();
        }
        return total * 0.9; // 10% off
    }
}

// Bulk discount: 20% off if any product's quantity > 5
class BulkDiscount extends Discount {
    public double applyDiscount(List<Product> products) {
        double total = 0;
        for (Product p : products) {
            if (p.getQuantity() > 5) {
                total += p.getTotalPrice() * 0.8; // 20% off
            } else {
                total += p.getTotalPrice();
            }
        }
        return total;
    }
}

// Payment interface
interface Payment {
    void pay(double amount);
}

// CashPayment implementation (could be extended)
class CashPayment implements Payment {
    public void pay(double amount) {
        System.out.printf("Total Amount Payable: %.2f\n", amount);
    }
}

// Main class
public class ShoppingCart {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<Product> cart = new ArrayList<>();

        int n = Integer.parseInt(sc.nextLine());

        // Reading products
        for (int i = 0; i < n; i++) {
            String[] parts = sc.nextLine().split(" ");
            String name = parts[0];
            double price = Double.parseDouble(parts[1]);
            int quantity = Integer.parseInt(parts[2]);

            cart.add(new Product(name, price, quantity));
        }

        // Reading discount type
        String discountType = sc.nextLine().toLowerCase();

        Discount discount;
        if (discountType.equals("festive")) {
            discount = new FestiveDiscount();
        } else if (discountType.equals("bulk")) {
            discount = new BulkDiscount();
        } else {
            System.out.println("Invalid discount type. No discount applied.");
            discount = new Discount() {
                public double applyDiscount(List<Product> products) {
                    double total = 0;
                    for (Product p : products) {
                        total += p.getTotalPrice();
                    }
                    return total;
                }
            };
        }

        // Print products
        for (Product p : cart) {
            System.out.printf("Product: %s, Price: %.2f, Quantity: %d\n",
                    p.getName(), p.getPrice(), p.getQuantity());
        }

        // Apply discount
        double totalPayable = discount.applyDiscount(cart);

        // Make payment
        Payment payment = new CashPayment();
        payment.pay(totalPayable);
    }
}
