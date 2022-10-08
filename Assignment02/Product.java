public class Product {
    private String ID;
    private String name;
    private float price;
    private String desc;
    private int quantity;
    private float discNormal;
    private float discPrime;
    private float discElite;

    Product(String ID, String name, float price, int quantity, String desc) {
        this.ID = ID;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.desc = desc;
    }

    public void showDetails() {
        System.out.println("\nProduct " + this.name + ":-");
        System.out.println("\tID: " + this.ID);
        System.out.println("\tName: " + this.name);
        System.out.println("\tPrice: " + this.price);
        System.out.println("\tQuantity: " + this.quantity);
        System.out.println("\tDescription: " + this.desc);
    }

    public void showDetails(int quantity) {
        System.out.println("\nProduct " + this.name + ":-");
        System.out.println("\tID: " + this.ID);
        System.out.println("\tName: " + this.name);
        System.out.println("\tPrice: " + this.price);
        System.out.println("\tQuantity: " + quantity);
        System.out.println("\tDescription: " + this.desc);
    }

    ///////////////////////////// Getters Setters //////////////////////////////
    public String getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public float getPrice() {
        return price;
    }

    public void setDiscount(float discNormal, float discPrime, float discElite) {
        this.discNormal = discNormal;
        this.discPrime = discPrime;
        this.discElite = discElite;
    }

    public int getQuantity() {
        return this.quantity;
    }
}