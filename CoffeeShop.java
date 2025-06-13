import java.io.*;
import java.text.*;
import java.util.*;

// CoffeeShop class
class CoffeeShop {
    private List<MenuItem> menu;
    private List<Order> orders;
    private int nextOrderId;

    public CoffeeShop() {
        menu = new ArrayList<>();
        orders = new ArrayList<>();
        nextOrderId = 1;
    }

    public void addMenuItem(MenuItem item) {
        menu.add(item);
    }

    public void displayMenu() {
        System.out.println("\n*** Coffee Shop Menu ***");
        for (int i = 0; i < menu.size(); i++) {
            System.out.print((i + 1) + ". ");
            menu.get(i).displayDetails();
        }
        System.out.println("*************************");
    }

    public MenuItem getMenuItemByIndex(int index) {
        if (index >= 0 && index < menu.size())
            return menu.get(index);
        return null;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public Order placeOrderWithInput() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter customer name:");
        String customerName = scanner.nextLine();
        System.out.println("Enter customer contact:");
        String customerContact = scanner.nextLine();

        Customer customer = new Customer(customerName, customerContact);
        Order order = new Order(nextOrderId++, customer);
        displayMenu();  // Display the menu every time user tries to place an order
        System.out.println("Enter the number of the item to order (or 0 to finish):");

        while (true) {

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            if (choice == 0)
                break;

            MenuItem item = getMenuItemByIndex(choice - 1);
            if (item != null) {
                order.addItem(item);
                System.out.println(item.getName() + " added to the order.");
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }

        if (order.getItems().isEmpty()) {
            System.out.println("No items ordered. Order cancelled.");
            return null;
        } else {
            orders.add(order);
            System.out.println("Order placed successfully.");
            order.displayDetails();
            return order;
        }
    }

    public void displayAllOrders() {
        System.out.println("\n*** All Orders ***");
        for (Order order : orders) {
            order.displayDetails();
            System.out.println("--------------------");
        }
    }

    public void saveOrderToFile(Order order) {
        try (FileWriter writer = new FileWriter("orders.txt", true)) {
            writer.write(order.toString() + "\n");
        } catch (IOException e) {
            System.out.println("Error saving orders to file: " + e.getMessage());
        }
    }

    public void loadOrdersFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("orders.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found. Creating a new file.");
            try {
                File file = new File("orders.txt");
                if (file.createNewFile()) {
                    System.out.println("File created: " + file.getName());
                } else {
                    System.out.println("File already exists.");
                }
            } catch (IOException ioException) {
                System.out.println("Error creating file: " + ioException.getMessage());
            }
        } catch (IOException e) {
            System.out.println("Error reading orders from file: " + e.getMessage());
        }
    }


    public void generateRevenueReport() {
        Map<String, Double> revenueByDate = new HashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        for (Order order : orders) {
            if (order.isPaid()) {
                String date = sdf.format(order.getOrderDate());
                revenueByDate.put(date, revenueByDate.getOrDefault(date, 0.0) + order.getTotal());
            }
        }

        System.out.println("\n*** Revenue Report ***");
        for (Map.Entry<String, Double> entry : revenueByDate.entrySet()) {
            System.out.println("Date: " + entry.getKey() + ", Revenue: " + entry.getValue() + " TK");
        }
    }

    public void checkoutPayment(Order order) {
        if (order.isPaid()) {
            System.out.println("This order has already been paid.");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        double totalAmount = order.getTotal();
        double remainingAmount = totalAmount - order.getPaidAmount();
        System.out.println("Total amount: " + totalAmount + " TK");
        System.out.println("Remaining amount to pay: " + remainingAmount + " TK");

        System.out.println("Enter payment amount:");
        double payment = scanner.nextDouble();

        if (payment >= remainingAmount) {
            double change = payment - remainingAmount;
            order.setPaidAmount(totalAmount);
            order.setPaidStatus(true);
            System.out.println("Payment successful. Change: " + change + " TK");
        } else {
            order.setPaidAmount(order.getPaidAmount() + payment);
            System.out.println("Partial payment accepted. Remaining balance: " + (remainingAmount - payment) + " TK");
        }

        if (order.isPaid()) {
            saveOrderToFile(order);
        }
    }

    public void editOrder(Order order) {
        if (order.isPaid()) {
            System.out.println("This order has already been paid and cannot be edited.");
            return; // Exit the method if the order is paid
        }
        Scanner scanner = new Scanner(System.in);
        System.out.println("Editing Order ID: " + order.getOrderId());

        // Display existing order items
        System.out.println("\nCurrent Order Items:");
        List<MenuItem> orderItems = order.getItems();
        for (int i = 0; i < orderItems.size(); i++) {
            System.out.println((i + 1) + ". " + orderItems.get(i).getName() + " (" + orderItems.get(i).getPrice() + " TK)");
        }

        System.out.println("\nDo you want to:");
        System.out.println("1. Add an item");
        System.out.println("2. Remove an item");
        System.out.print("Enter your choice : ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (choice == 1) {
            // Add item
            displayMenu();
            System.out.println("Enter the number of the item to add (or 0 to cancel):");
            int addItemChoice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            if (addItemChoice == 0) {
                System.out.println("Item addition cancelled.");
            } else {
                MenuItem item = getMenuItemByIndex(addItemChoice - 1);
                if (item != null) {
                    order.addItem(item);
                    System.out.println(item.getName() + " added to the order.");
                } else {
                    System.out.println("Invalid choice. Item not added.");
                }
            }
        } else if (choice == 2) {
            // Remove item
            if (orderItems.isEmpty()) {
                System.out.println("No items to remove.");
            } else {
                System.out.println("Select the item number to remove (or 0 to cancel):");
                int removeChoice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                if (removeChoice == 0) {
                    System.out.println("Item removal cancelled.");
                } else if (removeChoice > 0 && removeChoice <= orderItems.size()) {
                    MenuItem itemToRemove = orderItems.get(removeChoice - 1);
                    order.removeItem(itemToRemove);
                    System.out.println(itemToRemove.getName() + " removed from the order.");
                } else {
                    System.out.println("Invalid choice. No item removed.");
                }
            }
        } else {
            System.out.println("Invalid choice. Please try again.");
        }

        // Display updated order details
        order.displayDetails();
    }
}