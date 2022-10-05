import java.io.PipedInputStream;
import java.util.*;

@SuppressWarnings("unused")
public abstract class Customer {
    private String name;
    private int age;
    private String phNo;
    private String email;
    private String password;
    private int discount;

    PriorityQueue<Integer> coupons = new PriorityQueue<>(new ReverseComparator());

    Cart cast = new Cart();
    Wallet wallet = new Wallet();

    public Boolean login(String pass) {
        return password.equals(pass);
    }
}

@SuppressWarnings("unused")
class Cart {
    private int amoOfItems = 0;
    /** Key - Product; Value - Quantity */
    private HashMap<Product, Integer> items = new HashMap<>();

    public void addItem(Product product, int quantity) {
        if (items.get(product) != null) {
            quantity += items.get(product);
        }
        items.put(product, quantity);
        amoOfItems += product.getPrice() * quantity;
    }

    public void removeItem(Product product) {
        amoOfItems -= product.getPrice() * items.get(product);
        items.remove(product);
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

class ReverseComparator implements Comparator<Integer> {

    @Override
    public int compare(Integer num1, Integer num2) {
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