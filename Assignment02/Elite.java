import java.util.*;

public class Elite extends Customer {

    /**
     * Creating a new customer
     */
    Elite(String name, int age, String phNo, String email, String password) {
        this.name = name;
        this.age = age;
        this.phNo = phNo;
        this.email = email;
        this.password = password;
        this.status = CustomerStatus.ELITE;
        this.coupons = new PriorityQueue<>(new ReverseComparator());

        this.cart = new Cart();
        this.wallet = new Wallet();
    }

    /**
     * For customers status change
     */
    Elite(String name, int age, String phNo, String email, String password, Cart cart, Wallet wallet,
            PriorityQueue<Float> coupons) {
        this.name = name;
        this.age = age;
        this.phNo = phNo;
        this.email = email;
        this.password = password;
        this.status = CustomerStatus.ELITE;
        this.coupons = coupons;

        this.cart = cart;
        this.wallet = wallet;
    }

    public float checkBalance() {
        float amount = 0;
        Iterator<Deal> iterator = cart.getDealIterator();
        while (iterator.hasNext()) {
            amount += iterator.next().getPrice();
        }

        HashMap<Product, Integer> items = cart.getItems();

        for (Map.Entry<Product, Integer> e : items.entrySet()) {
            float _price = e.getKey().getPrice();

            if (coupons.peek() != null && coupons.peek().compareTo(e.getKey().getDiscElite()) > 0
                    && coupons.peek().compareTo(10f) > 0) {
                _price -= _price * coupons.poll() / 100;
            } else if (e.getKey().getDiscElite() > 10f) {
                _price -= _price * e.getKey().getDiscElite() / 100;
            } else {
                _price -= _price * 10 / 100;
            }

            amount += _price * e.getValue();
        }

        return amount;
    }

    public float getCurrentSubscription() {
        return 300f;
    }
}
