import java.util.*;

public abstract class Customer {
    protected String name;
    protected int age;
    protected String phNo;
    protected String email;
    protected String password;
    protected CustomerStatus status;

    static final float normal = 0f;
    static final float prime = 200f;
    static final float elite = 300f;

    static Scanner input = new Scanner(System.in);

    protected PriorityQueue<Float> coupons;

    protected Cart cart;
    protected Wallet wallet;

    public Boolean login(String pass) {
        return password.equals(pass);
    }

    ///////////////////////////// Menu Methods /////////////////////////////////
    abstract public float checkBalance();

    public void checkOut() {
        Iterator<Deal> iterator = cart.getDealIterator();
        HashMap<Product, Integer> items = cart.getItems();

        // Checking quantity
        while (iterator.hasNext()) {
            Deal deal = iterator.next();
            if (items.get(deal.getProd1()) != null) {
                if (items.get(deal.getProd1()) + 1 > deal.getProd1().getQuantity()) {
                    System.out.println("Item " + deal.getProd1().getID() + " is Out of Stock");
                    return;
                }
            } else if (items.get(deal.getProd1()) > deal.getProd1().getQuantity()) {
                System.out.println("Item " + deal.getProd1().getID() + " is Out of Stock");
                return;
            }

            if (items.get(deal.getProd2()) != null) {
                if (items.get(deal.getProd2()) + 1 > deal.getProd2().getQuantity()) {
                    System.out.println("Item " + deal.getProd2().getID() + " is Out of Stock");
                    return;
                }
            } else if (items.get(deal.getProd2()) > deal.getProd2().getQuantity()) {
                System.out.println("Item " + deal.getProd2().getID() + " is Out of Stock");
                return;
            }
        }

        for (Map.Entry<Product, Integer> e : items.entrySet()) {
            if (e.getKey().getQuantity() < e.getValue()) {
                System.out.println("Item " + e.getKey().getID() + " is Out of Stock");
                return;
            }
        }
        // Checking enough balance
        if (wallet.checkBalance() < this.checkBalance()) {
            System.out.println("Not enough money in the wallet");
            return;
        }

        wallet.pay(this.checkBalance());

        // Reducing the stock
        Iterator<Deal> iterator1 = cart.getDealIterator();

        while (iterator1.hasNext()) {
            Deal deal = iterator1.next();
            deal.getProd1().reduceQuantity(1);
            deal.getProd2().reduceQuantity(1);
        }

        for (Map.Entry<Product, Integer> e : items.entrySet()) {
            e.getKey().reduceQuantity(e.getValue());
        }
    }

    private void addProdCart() {
        System.out.print("Enter Product ID: ");
        String _id = input.nextLine();
        if (Admin.getProduct(_id) == null) {
            System.out.println("The entered product ID doesn't exist\nTry Again!!!");
        }

        System.out.print("Enter the quantity: ");
        int _quantity = input.nextInt();
        input.nextLine();

        cart.addItem(Admin.getProduct(_id), _quantity);
    }

    private void addDealCart() {
        LinkedList<Deal> deals = Admin.showDeal();

        System.out.print("Enter the deal number which you want to add: ");
        int _dealNo = input.nextInt();
        input.nextLine();

        Deal _deal = deals.get(_dealNo - 1);

        if (_deal == null) {
            System.out.println("Enter the correct deal Number");
            return;
        }

        cart.addDeal(_deal);
    }

    private void viewCoupon() {
        Iterator<Float> iterator = coupons.iterator();
        int i = 1;
        while (iterator.hasNext()) {
            System.out.println(i + ") " + iterator.next() + "%");
        }
    }

    private void changeCustomerStatus() {
        System.out.println("Tiers of subscriptions are:- ");
        System.out.println("\tPRESS 1: For normal");
        System.out.println("\tPRESS 2: For prime (₹200pm)");
        System.out.println("\tPRESS 3: For elite (₹300pm)");
        System.out.print("Enter your choice: ");
        int _sub = input.nextInt();
        input.nextLine();

        Flipzon.upgradeCustomerStatus(this, _sub);
    }

    /////////////////////////////// Menu Options ///////////////////////////////
    /**
     * Customer menu
     * 
     * @return true, to show the menu again else, false
     */
    public Boolean customerMenu() {
        System.out.println("Welcome " + this.name);
        System.out.println("\tPRESS 1:  To browse Products");
        System.out.println("\tPRESS 2:  To browse Deals");
        System.out.println("\tPRESS 3:  To Add a product to cart");
        System.out.println("\tPRESS 4:  To Add a deal to cart");
        System.out.println("\tPRESS 5:  To view coupons");
        System.out.println("\tPRESS 6:  To check Account balance");
        System.out.println("\tPRESS 7:  To view cart");
        System.out.println("\tPRESS 8:  To empty cart");
        System.out.println("\tPRESS 9:  To checkout cart");
        System.out.println("\tPRESS 10: To Upgrade customer status");
        System.out.println("\tPRESS 11: To add money to wallet");
        System.out.println("\tPRESS 12: To go back");
        System.out.print("Enter your choice: ");
        int a = input.nextInt();
        input.nextLine();

        switch (a) {
            case 1:
                Admin.productCatalogue();
                return true;
            case 2:
                Admin.showDeal();
                return true;
            case 3:
                addProdCart();
                return true;
            case 4:
                addDealCart();
                return true;
            case 5:
                viewCoupon();
                return true;
            case 6:
                System.out.println("The balance in your account is " + wallet.checkBalance());
                return true;
            case 7:
                cart.viewCart(this);
                return true;
            case 8:
                cart = new Cart();
                System.out.println("Cart successfully emptied");
                return true;
            case 9:
                this.checkOut();
                return true;
            case 10:
                changeCustomerStatus();
                return true;
            case 11:
                System.out.print("Enter the amount you want to add to the wallet: ");
                int _amo = input.nextInt();
                input.nextLine();
                wallet.addMoney(_amo);
                return true;
            case 12:
                System.out.println("Successfully signed out");
                return false;
            default:
                System.out.println("Enter correct option\nTry Again!!!");
                return true;
        }

    }

    ///////////////////////////// Getters Setters //////////////////////////////
    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getPhNo() {
        return phNo;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Cart getCart() {
        return cart;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public PriorityQueue<Float> getCoupons() {
        return coupons;
    }

    abstract public float getCurrentSubscription();

    ///////////////////////////////// Helpers //////////////////////////////////
    protected enum CustomerStatus {
        NORMAL, PRIME, ELITE
    }
}

/////////////////////////////////// Classes ////////////////////////////////////
class Cart {
    // private float amoOfItems = 0;
    /** Key - Product; Value - Quantity */
    private HashMap<Product, Integer> items = new HashMap<>();
    private LinkedList<Deal> deals = new LinkedList<>();

    public void addItem(Product product, int quantity) {
        if (items.get(product) != null) {
            quantity += items.get(product);
        }

        if (product.getQuantity() < quantity) {
            System.out.println("The product is out of stock!!!");
        }
        items.put(product, quantity);
        // amoOfItems += product.getPrice() * quantity;
    }

    public void removeItem(Product product) {
        // amoOfItems -= product.getPrice() * items.get(product);
        items.remove(product);
    }

    public void addDeal(Deal deal) {
        deals.add(deal);
        // amoOfItems += deal.getPrice();
    }

    public Iterator<Deal> getDealIterator() {
        return deals.iterator();
    }

    public HashMap<Product, Integer> getItems() {
        return items;
    }

    // public float totalAmount() {
    // return amoOfItems;
    // }

    public void viewCart(Customer customer) {
        Boolean flag = false;
        if (!deals.isEmpty()) {
            flag = true;
            System.out.println("The deals in your cart are:-");

            for (Deal deal : deals) {
                deal.getDetailCart();
            }
        }

        if (!items.isEmpty()) {
            flag = true;
            System.out.println("\nThe items in your cart are:-");
            for (Map.Entry<Product, Integer> item : items.entrySet()) {
                item.getKey().showDetails(item.getValue());
            }
        }

        if (!flag) {
            System.out.println("Your cart is empty");
        }

        System.out.println("Your total is ₹" + customer.checkBalance());
    }
}

class Wallet {
    private float amount = 1000f;

    /**
     * Pay using the wallet (remove money from wallet)
     * 
     * @param amo amount to be paid(removed)
     * @return true, if successful, else false
     */
    public Boolean pay(float amo) {
        if (amount < amo) {
            return false;
        }
        amount -= amo;
        return true;
    }

    public void addMoney(float amo) {
        amount += amo;
    }

    public float checkBalance() {
        return amount;
    }

}

class ReverseComparator implements Comparator<Float> {

    @Override
    public int compare(Float num1, Float num2) {
        int value = num1.compareTo(num2);
        if (value > 0) {
            return -1;
        } else if (value < 0) {
            return 1;
        } else {
            return 0;
        }
    }
}