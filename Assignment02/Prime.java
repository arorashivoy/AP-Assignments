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

    public float checkBalance() {
        float amount = 0;
        Iterator<Deal> iterator = cart.getDealIterator();
        while (iterator.hasNext()) {
            amount += iterator.next().getPrice();
        }

        HashMap<Product, Integer> items = cart.getItems();

        for (Map.Entry<Product, Integer> e : items.entrySet()) {
            float _price = e.getKey().getPrice();

            if (coupons.peek() != null && coupons.peek().compareTo(e.getKey().getDiscPrime()) > 0
                    && coupons.peek().compareTo(5f) > 0) {
                _price -= _price * coupons.poll() / 100;
            } else if (e.getKey().getDiscPrime() > 5f) {
                _price -= _price * e.getKey().getDiscPrime() / 100;
            } else {
                _price -= _price * 5 / 100;
            }

            amount += _price * e.getValue();
        }

        return amount;
    }

    public float getCurrentSubscription() {
        return 200f;
    }

    public float deliveryCharge(float amount) {
        return 100f + ((2 / 100) * amount);
    }

    public void printDeliveryMsg() {
        System.out.println("It will be delivered in 3-6 days");
    }

    public void addCoupons() {
        Random random = new Random();

        int noCoupons = random.nextInt(1) + 1;

        for (int i = 0; i < noCoupons; i++) {
            float _coupon = random.nextInt(10) + 5f;
            coupons.add(_coupon);
            System.out.println("Received coupon of " + _coupon + "%");
        }
    }
}
