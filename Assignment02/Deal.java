public class Deal {
    /** ID - 'prod1ID prod2ID' */
    private String id;
    private Product prod1;
    private Product prod2;
    private float price;

    Deal(String id, Product prod1, Product prod2, float price) {
        this.id = id;
        this.prod1 = prod1;
        this.prod2 = prod2;
        this.price = price;
    }

    public void getDetail() {
        System.out.println("\nYou could get the deal");
        System.out.println("\tID: " + prod1.getID() + "\tName: " + prod1.getName() + "\tPrice: " + prod1.getPrice());
        System.out.println("\tID: " + prod2.getID() + "\tName: " + prod2.getName() + "\tPrice: " + prod2.getPrice());
        System.out.println("For just " + price);
    }

    public void getDetailCart() {
        System.out.println("\nYou are getting the deal");
        System.out.println("\tID: " + prod1.getID() + "\tName: " + prod1.getName() + "\tPrice: " + prod1.getPrice());
        System.out.println("\tID: " + prod2.getID() + "\tName: " + prod2.getName() + "\tPrice: " + prod2.getPrice());
        System.out.println("For just " + price);
    }

    public String getID() {
        return id;
    }

    public float getPrice() {
        return price;
    }

    public Product getProd1() {
        return prod1;
    }

    public Product getProd2() {
        return prod2;
    }
}
