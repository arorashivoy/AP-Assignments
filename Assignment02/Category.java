import java.util.*;

@SuppressWarnings("unused")
public class Category {
    // Key - Product ID; Value - Product Object
    private HashMap<String, Product> products = new HashMap<>();

    private String ID;
    private String name;

    private Admin admin;

    Category(String name, String ID, Product product, Admin admin) {
        this.name = name;
        this.ID = ID;
        this.admin = admin;
        this.products.put(product.getID(), product);
    }

    ///////////////////////////// Getters Setters //////////////////////////////
    public void addProduct(Product product) {
        products.put(product.getID(), product);
    }

    /**
     * Delete a product
     * 
     * @param id product's id
     * @return true, if successful, else false
     */
    public Boolean delProduct(String id) {
        if (products.remove(id) == null) {
            return false;
        }
        return true;
    }

}