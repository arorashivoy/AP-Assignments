import java.util.*;

@SuppressWarnings("unused")
public class Admin {
    private String username;
    private String pass;

    Scanner input = new Scanner(System.in);

    /** Key - ID; Value - Category Object */
    private static HashMap<String, Category> categories = new HashMap<>();
    /** Key - ID; Value - Product Object */
    private static HashMap<String, Product> products = new HashMap<>();

    Admin(String username, String password) {
        this.username = username;
        this.pass = password;
    }

    ///////////////////////////// Menu Methods /////////////////////////////////
    public void addCategory() {
        System.out.print("Enter the Category ID: ");
        String _id = input.nextLine();
        if (categories.get(_id) != null) {
            System.out.println("The Category ID already exist!!!");
            return;
        }

        System.out.print("Enter the Category Name: ");
        String _name = input.nextLine();
        Product _prod = createProduct();

        categories.put(_id, new Category(_name, _id, _prod, this));
    }

    public void delCategory() {
        System.out.print("Enter the Category ID: ");
        String _id = input.nextLine();

        if (categories.remove(_id) == null) {
            System.out.println("The Category ID does not exist");
        }
    }

    public void addProduct(String catID, Product product) {
        categories.get(catID).addProduct(product);
    }

    public void delProduct(String catID, String prodID) {
        products.remove(prodID);
        if (!categories.get(catID).delProduct(prodID)) {
            System.out.println("Product does not exist!!!");
        }
    }

    public void addDiscProd() {
        // TODO complete
    }

    public void addGiveaway() {
        // TODO complete
    }

    /////////////////////////////// Menu Options ///////////////////////////////
    /**
     * Admin Menu for the Application
     * 
     * @return true, to show the menu again else, false
     */
    public Boolean AdminMenu() {
        System.out.println("Welcome " + username + "!!!");
        System.out.println("\tPRESS 1: To Add Category");
        System.out.println("\tPRESS 2: To Delete Category");
        System.out.println("\tPRESS 3: To Add Product");
        System.out.println("\tPRESS 4: To Delete Product");
        System.out.println("\tPRESS 5: To Set discount on Product");
        System.out.println("\tPRESS 6: To add Giveaway deal");
        System.out.println("\tPRESS 7: To go Back");
        System.out.print("Enter your choice: ");
        int a = input.nextInt();
        input.nextLine();

        String _id;
        switch (a) {
            case 1:
                addCategory();
                return true;
            case 2:
                delCategory();
                return true;
            case 3:
                System.out.print("Enter the ID of the Category to which you would like to add the Product: ");
                _id = input.nextLine();
                if (categories.get(_id) == null) {
                    System.out.println("Category does not exist!!!");
                    return true;
                }
                Product _prod = createProduct();
                addProduct(_id, _prod);
                return true;
            case 4:
                System.out.print("Enter the ID of the Category to whose Product you want to delete: ");
                _id = input.nextLine();
                if (categories.get(_id) == null) {
                    System.out.println("Category does not exist!!!");
                    return true;
                }
                System.out.print("Enter the ID of the product which you want to delete: ");
                String _prodID = input.nextLine();

                delProduct(_id, _prodID);
                return true;
            case 5:
                return true;
            case 6:
                return true;
            case 7:
                return false;
            default:
                System.out.println("Enter correct option\nTry Again!!!");
                return true;
        }
    }

    ///////////////////////////// Getters Setters //////////////////////////////
    /**
     * Check the password entered by the user
     * 
     * @param pass Password entered by the user
     * @return true, if correct, else false
     */
    public Boolean checkPassword(String pass) {
        return this.pass.equals(pass);
    }

    ///////////////////////////////// Helpers //////////////////////////////////
    private Product createProduct() {
        System.out.println("Enter Product's details");

        System.out.print("\nEnter Product ID: ");
        String ID = input.nextLine();
        if (products.get(ID) != null) {
            System.out.println("Product ID already exist...Try Again\n");
            return createProduct();
        }

        System.out.print("\nEnter the name: ");
        String name = input.nextLine();

        System.out.print("\nEnter Price: ");
        float price = input.nextFloat();
        input.nextLine();

        System.out.print("\nEnter Product Description: ");
        String desc = input.nextLine();

        // Adding the product to the admin list
        Product prod = new Product(ID, name, price, desc);
        products.put(ID, prod);

        return prod;
    }
}