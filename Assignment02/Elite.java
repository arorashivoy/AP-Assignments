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

    public void checkOut() {

    }

    public float getCurrentSubscription() {
        return 300f;
    }
}
