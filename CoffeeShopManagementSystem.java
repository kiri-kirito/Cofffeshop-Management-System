import java.util.*;
import java.util.*;

// Main class
public class CoffeeShopManagementSystem {
    public static void main(String[] args) {
        CoffeeShop coffeeShop = new CoffeeShop();

        coffeeShop.addMenuItem(new MenuItem("Americano", 180));
        coffeeShop.addMenuItem(new MenuItem("Red Eye", 200));
        coffeeShop.addMenuItem(new MenuItem("Black Coffee", 150));
        coffeeShop.addMenuItem(new MenuItem("Espresso", 150));
        coffeeShop.addMenuItem(new MenuItem("Cappuccino", 200));
        coffeeShop.addMenuItem(new MenuItem("Latte", 220));
        coffeeShop.addMenuItem(new MenuItem("Mocha", 250));
        coffeeShop.addMenuItem(new MenuItem("Iced Coffee", 280));
        coffeeShop.addMenuItem(new MenuItem("Tea", 150));
        coffeeShop.addMenuItem(new MenuItem("Pastries", 250));
        coffeeShop.addMenuItem(new MenuItem("Cookies", 270));
        coffeeShop.addMenuItem(new MenuItem("Brownies", 270));

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            // Displaying the menu when the program starts
            System.out.println("\n*** Coffee Shop Management System ***");
            System.out.println("1. Place Order");
            System.out.println("2. Display All Orders");
            System.out.println("3. Generate Revenue Report");
            System.out.println("4. Load Orders from File");
            System.out.println("5. Checkout Payment");
            System.out.println("6. Edit Order");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    coffeeShop.placeOrderWithInput();
                    break;
                case 2:
                    coffeeShop.displayAllOrders();
                    break;
                case 3:
                    coffeeShop.generateRevenueReport();
                    break;
                case 4:
                    coffeeShop.loadOrdersFromFile();
                    break;
                case 5:
                    System.out.println("Enter Order ID for checkout:");
                    int orderId = scanner.nextInt();
                    scanner.nextLine();
                    Order order = null;
                    for (Order o : coffeeShop.getOrders()) {
                        if (o.getOrderId() == orderId) {
                            order = o;
                            break;
                        }
                    }
                    if (order != null) {
                        coffeeShop.checkoutPayment(order);
                    } else {
                        System.out.println("Order not found.");
                    }
                    break;
                case 6:
                    System.out.println("Enter Order ID for editing:");
                    int editOrderId = -1;  // Declare outside the try-catch for wider scope
                    boolean validInput = false;

                    while (!validInput) {
                        try {
                            editOrderId = scanner.nextInt();
                            scanner.nextLine();  // Consume the newline character
                            validInput = true;   // Input is valid, exit the loop
                        } catch (InputMismatchException e) {
                            System.out.println("Invalid input. Please enter a valid integer Order ID.");
                            scanner.nextLine();  // Clear the invalid input
                        }
                    }

                    // Search for the order with the provided Order ID
                    Order editOrder = null;
                    for (Order o : coffeeShop.getOrders()) {
                        if (o.getOrderId() == editOrderId) {
                            editOrder = o;
                            break;
                        }
                    }

                    if (editOrder != null) {
                        coffeeShop.editOrder(editOrder);  // Edit the order if found
                    } else {
                        System.out.println("Order not found.");  // If the order isn't found
                    }
                    break;
                case 7:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        System.out.println("Thank you for using the Coffee Shop Management System.");
    }
}