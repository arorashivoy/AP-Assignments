import java.util.*;

public class Prime extends Customer {

    /**
     * Creating a new customer
     */
    Prime(String name, int age, String phNo, String email, String password) {
        this.name = name;
        this.age = age;
        this.phNo = phNo;
        this.email = email;
        this.password = password;
        this.status = CustomerStatus.PRIME;
        this.coupons = new PriorityQueue<>(new ReverseComparator());

        this.cart = new Cart();
        this.wallet = new Wallet();
    }

    /**
     * For customers status change
     */
    Prime(String name, int age, String phNo, String email, String password, Cart cart, Wallet wallet,
            PriorityQueue<Float> coupons) {
        this.name = name;
        this.age = age;
        this.phNo = phNo;
        this.email = email;
        this.password = password;
        this.status = CustomerStatus.PRIME;
        this.coupons = coupons;

        this.cart = cart;
        this.wallet = wallet;
    }

    public void checkOut() {

    }

    public float getCurrentSubscription() {
        return 200f;
    }
}
