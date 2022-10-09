import java.util.*;

public class Admin {
    private String username;
    private String pass;

    Scanner input = new Scanner(System.in);

    /** Key - ID; Value - Category Object */
    private static HashMap<String, Category> categories = new HashMap<>();
    /** Key - ID; Value - Product Object */
    private static HashMap<String, Product> products = new HashMap<>();
    /** Key - 'prod1ID prod2ID'; Value - Deal */
    private static HashMap<String, Deal> deals = new HashMap<>();

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

        if (categories.get(_id) == null) {
            System.out.println("The Category ID does not exist");
        } else {
            Object[] _catProds = categories.get(_id).getAllProducts();

            for (Object s : _catProds) {
                products.remove(s);
            }
        }
    }

    public void addProduct() {
        System.out.print("Enter the ID of the Category to which you would like to add the Product: ");
        String _id = input.nextLine();
        if (categories.get(_id) == null) {
            System.out.println("Category does not exist!!!");
            return;
        }
        Product _prod = createProduct();
        categories.get(_id).addProduct(_prod);
    }

    public void delProduct() {
        String _id;
        System.out.print("Enter the ID of the Category to whose Product you want to delete: ");
        _id = input.nextLine();
        if (categories.get(_id) == null) {
            System.out.println("Category does not exist!!!");
            return;
        }
        System.out.print("Enter the ID of the product which you want to delete: ");
        String _prodID = input.nextLine();
        products.remove(_prodID);
        if (!categories.get(_id).delProduct(_prodID)) {
            System.out.println("Product does not exist!!!");
        }
    }

    public void addDiscProd() {
        System.out.print("Enter the ID of the product on which you want to add the discount: ");
        String _prodID = input.nextLine();

        if (products.get(_prodID) == null) {
            System.out.println("Product does not exist!!!");
        } else {
            System.out.print("Enter the discount percentage for Normal customer: ");
            float _discNormal = input.nextFloat();
            input.nextLine();
            System.out.print("Enter the discount percentage for Prime customer: ");
            float _discPrime = input.nextFloat();
            input.nextLine();
            System.out.print("Enter the discount percentage for Elite customer: ");
            float _discElite = input.nextFloat();
            input.nextLine();

            products.get(_prodID).setDiscount(_discNormal, _discPrime, _discElite);
        }
    }

    public void addDeal() {
        System.out.print("Enter the first Product's ID: ");
        String _id1 = input.nextLine();
        if (products.get(_id1) == null) {
            System.out.println("The product doesn't exist!!!");
            return;
        }
        System.out.print("Enter the second Product's ID: ");
        String _id2 = input.nextLine();
        if (products.get(_id2) == null) {
            System.out.println("The product doesn't exist!!!");
            return;
        }

        System.out.print("Enter the combined price: ");
        float _price = input.nextFloat();
        input.nextLine();

        deals.put(_id1 + " " + _id2, new Deal(_id1 + " " + _id2, products.get(_id1), products.get(_id2), _price));
    }

    public static void productCatalogue() {
        System.out.println("There are special offers for the following products");
        showDeal();

        System.out.println("\nAll the products are as follows:-");
        for (Category c : categories.values()) {
            c.showAllProducts();
        }
    }

    public static LinkedList<Deal> showDeal() {
        Boolean flag = false;
        int i = 1;
        LinkedList<Deal> _deals = new LinkedList<>(deals.values());
        for (Deal deal : _deals) {
            flag = true;
            System.out.println(i + ") Deal: " + deal.getID());

            deal.getDetail();

            i += 1;
        }

        if (!flag) {
            System.out.println("No deal available");
        }

        return _deals;
    }

    /////////////////////////////// Menu Options ///////////////////////////////
    /**
     * Admin Menu for the Application
     * 
     * @return true, to show the menu again else, false
     */
    public Boolean AdminMenu() {
        System.out.println("\n\nWelcome " + username + "!!!");
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

        switch (a) {
            case 1:
                addCategory();
                return true;
            case 2:
                delCategory();
                return true;
            case 3:
                addProduct();
                return true;
            case 4:
                delProduct();
                return true;
            case 5:
                addDiscProd();
                return true;
            case 6:
                addDeal();
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

    public static Product getProduct(String id) {
        return products.get(id);
    }

    ///////////////////////////////// Helpers //////////////////////////////////
    private Product createProduct() {
        System.out.println("Enter Product's details");

        System.out.print("\tEnter Product ID: ");
        String ID = input.nextLine();
        if (products.get(ID) != null) {
            System.out.println("Product ID already exist...Try Again\n");
            return createProduct();
        }

        System.out.print("\tEnter the name: ");
        String name = input.nextLine();

        System.out.print("\tEnter Price: ");
        float price = input.nextFloat();
        input.nextLine();

        System.out.print("\tEnter Product quantity: ");
        int quantity = input.nextInt();
        input.nextLine();

        System.out.print("\tEnter Product Description: ");
        String desc = input.nextLine();

        // Adding the product to the admin list
        Product prod = new Product(ID, name, price, quantity, desc);
        products.put(ID, prod);

        return prod;
    }
}