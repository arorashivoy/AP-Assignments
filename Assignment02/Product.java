import java.util.*;

@SuppressWarnings("unused")
public class Product {
    private String ID;
    private String name;
    private float price;
    private String desc;
    private float discount;

    Product(String ID, String name, float price, String desc) {
        this.ID = ID;
        this.name = name;
        this.price = price;
        this.desc = desc;
    }

    ///////////////////////////// Getters Setters //////////////////////////////
    public String getID() {
        return ID;
    }

    public float getPrice() {
        // TODO Add discount to the item
        return price;
    }
}