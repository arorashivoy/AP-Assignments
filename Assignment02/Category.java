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

    public void showAllProducts() {
        System.out.println("Thw products under the category " + this.name + " with ID: " + this.ID + "are:-");
        for (Product p : products.values()) {
            p.showDetails();
        }
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

    /**
     * Get the array of all products that are associated with this category
     * 
     * @return Object[] array of the products ID
     */
    public Object[] getAllProducts() {
        return products.keySet().toArray();
    }

}