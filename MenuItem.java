// MenuItem class
class MenuItem implements Displayable {
    private String name;
    private double price;

    public MenuItem(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public void displayDetails() {
        System.out.println(name + " (" + price + " TK)");
    }

    @Override
    public String toString() {
        return name + " (" + price + " TK)";
    }
}