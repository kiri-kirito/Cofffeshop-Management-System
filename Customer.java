// Customer class
class Customer extends Person {
    public Customer(String name, String contact) {
        super(name, contact);
    }

    @Override
    public void displayDetails() {
        System.out.println("Customer Name: " + getName() + ", Contact: " + getContact());
    }
}