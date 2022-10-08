import java.util.*;
import java.io.*;

// TODO add success and failure msg
public class Flipzon {
    static Scanner input = new Scanner(System.in);
    static Console cnsl = System.console();

    /** Key - email; Value - object */
    private static HashMap<String, Customer> customers = new HashMap<>();
    private static Admin beff = new Admin("Beff", "2021420");
    private static Admin gill = new Admin("Gill", "2021420");

    public static void main(String[] args) {
        // Menu Screens
        Boolean mainMenu = true;

        while (mainMenu) {
            mainMenu = MainMenu();
        }

    }


    public static void upgradeCustomerStatus(Customer current, int newStatus) {
        
    }

    ///////////////////////////// Menu Methods /////////////////////////////////
    private static void adminLogin() {
        Boolean adminMenu = false;
        System.out.print("Enter admin username: ");
        String _user = input.nextLine();
        String _pass;

        if (cnsl == null) {
            System.out.print("Enter admin password: ");
            _pass = input.nextLine();
        } else {
            char[] _passArr = cnsl.readPassword("Enter admin password: ");
            _pass = String.valueOf(_passArr);
        }

        if (_user.equals("Beff")) {
            if (!beff.checkPassword(_pass)) {
                System.out.println("Incorrect Password");
                return;
            }
            adminMenu = true;
            while (adminMenu) {
                adminMenu = beff.AdminMenu();
            }
        } else if (_user.equals("Gill")) {
            if (!gill.checkPassword(_pass)) {
                System.out.println("Incorrect Password");
                return;
            }
            adminMenu = true;
            while (adminMenu) {
                adminMenu = gill.AdminMenu();
            }
        } else {
            System.out.println("User does not exist...Try Again!!!");
        }
    }

    private static void customerSignUp() {
        System.out.print("Enter your name: ");
        String _name = input.nextLine();
        System.out.print("Enter your age: ");
        int _age = input.nextInt();
        input.nextLine();
        System.out.print("Enter your phone number: ");
        String _phNo = input.nextLine();
        System.out.print("Enter your email id: ");
        String _email = input.nextLine();
        String _pass;

        if (cnsl == null) {
            System.out.print("Enter password: ");
            _pass = input.nextLine();
        } else {
            char[] _passArr = cnsl.readPassword("Enter password: ");
            _pass = String.valueOf(_passArr);
        }

        customers.put(_email, new Normal(_name, _age, _phNo, _email, _pass));
    }

    private static void customerLogin() {
        Boolean customerMenu = false;

        System.out.print("Enter your email: ");
        String _email1 = input.nextLine();
        if (customers.get(_email1) == null) {
            System.out.println("Customer doesn't exist!!!");
            return;
        }
        String _pass1;
        if (cnsl == null) {
            System.out.print("Enter password: ");
            _pass1 = input.nextLine();
        } else {
            char[] _passArr = cnsl.readPassword("Enter password: ");
            _pass1 = String.valueOf(_passArr);
        }

        if (customers.get(_email1).login(_pass1)) {
            customerMenu = true;
            while (customerMenu) {
                customerMenu = customers.get(_email1).customerMenu();
            }
        } else {
            System.out.println("Wrong Password entered!!!");
        }
    }

    /////////////////////////////// Menu Options ///////////////////////////////
    /**
     * Main Menu for the Application
     * 
     * @return true, to show the menu again else, false
     */
    private static Boolean MainMenu() {
        // Menu Screens
        Boolean customerMenu = false;

        System.out.println("\n\nWelcome to FLIPZON!!!");
        System.out.println("\tPRESS 1: To Enter as Admin");
        System.out.println("\tPRESS 2: To Explore the Product Catalog");
        System.out.println("\tPRESS 3: To Show Available Deals");
        System.out.println("\tPRESS 4: To Enter as Customer");
        System.out.println("\tPRESS 5: To Exit the Application");
        System.out.print("Enter your choice: ");
        int a = input.nextInt();
        input.nextLine();

        switch (a) {
            case 1:
                adminLogin();
                return true;
            case 2:
                Admin.productCatalogue();
                return true;
            case 3:
                Admin.showDeal();
                return true;
            case 4:
                customerMenu = true;
                while (customerMenu) {
                    customerMenu = customerMenuMode();
                }
                return true;
            case 5:
                System.out.println("Thanks for visiting!!!");
                return false;
            default:
                System.out.println("Enter correct option\nTry Again!!!");
                return true;
        }
    }

    /**
     * Login, Sign-up menu for the customer
     * 
     * @return true, to show the menu again else, false
     */
    private static Boolean customerMenuMode() {
        System.out.println("Welcome to Customer Dashboard");
        System.out.println("\tPRESS 1: To Sign Up");
        System.out.println("\tPRESS 2: To Login");
        System.out.println("\tPRESS 3: To go Back");
        System.out.print("Enter your choice: ");

        int a = input.nextInt();
        input.nextLine();

        switch (a) {
            case 1:
                customerSignUp();
                return true;

            case 2:
                customerLogin();
                return true;
            case 3:
                return false;
            default:
                System.out.println("Enter correct option\nTry Again!!!");
                return true;
        }
    }
}