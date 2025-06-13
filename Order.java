import java.text.*;
import java.util.*;

// Order class
class Order implements Displayable {
    private int orderId;
    private Customer customer;
    private List<MenuItem> items;
    private double total;
    private double paidAmount;
    private boolean isPaid;
    private Date orderDate;

    public Order(int orderId, Customer customer) {
        this.orderId = orderId;
        this.customer = customer;
        this.items = new ArrayList<>();
        this.total = 0.0;
        this.paidAmount = 0.0;
        this.isPaid = false;
        this.orderDate = new Date();
    }

    public int getOrderId() {
        return orderId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public List<MenuItem> getItems() {
        return items;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void addItem(MenuItem item) {
        items.add(item);
        total += item.getPrice();
    }

    public void removeItem(MenuItem item) {
        if (items.remove(item)) {
            total -= item.getPrice();
        }
    }

    public double getTotal() {
        return total;
    }

    public double getPaidAmount() {
        return paidAmount;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaidAmount(double paidAmount) {
        this.paidAmount = paidAmount;
    }

    public void setPaidStatus(boolean isPaid) {
        this.isPaid = isPaid;
    }

    @Override
    public void displayDetails() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("Order ID: " + orderId);
        System.out.println("Date: " + sdf.format(orderDate));
        customer.displayDetails();
        for (MenuItem item : items) {
            System.out.println("Item: " + item);
        }
        System.out.println("Total: " + total + " TK");
        System.out.println("Status: " + (isPaid ? "Paid" : "Not Paid"));
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return "Order ID: " + orderId + ", Date: " + sdf.format(orderDate) + ", Customer: " + customer.getName()
                + ", Total: " + total + " TK, Status: " + (isPaid ? "Paid" : "Not Paid");
    }
}