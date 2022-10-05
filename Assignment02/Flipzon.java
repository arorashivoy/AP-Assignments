import java.util.*;
import java.io.*;
import java.lang.*;

// TODO add success and failure msg

@SuppressWarnings("unused")
public class Flipzon {
    static Scanner input = new Scanner(System.in);
    static Console cnsl = System.console();

    public static void main(String[] args) {
        Admin beff = new Admin("Beff", "2021420");
        Admin gill = new Admin("Gill", "2021420");

        // Menu Screens
        Boolean mainMenu = true;

        while (mainMenu) {
            mainMenu = MainMenu(beff, gill);
        }

    }

    /////////////////////////////// Menu Options ///////////////////////////////
    /**
     * Main Menu for the Application
     * 
     * @param beff object of the admin beff
     * @param gill object of the admin gill
     * @return true, to show the menu again else, false
     */
    private static Boolean MainMenu(Admin beff, Admin gill) {
        // Menu Screens
        Boolean adminMenu = false;

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
                        return true;
                    }
                    adminMenu = true;
                    while (adminMenu) {
                        adminMenu = beff.AdminMenu();
                    }
                } else if (_user.equals("Gill")) {
                    if (!gill.checkPassword(_pass)) {
                        System.out.println("Incorrect Password");
                        return true;
                    }
                    adminMenu = true;
                    while (adminMenu) {
                        adminMenu = gill.AdminMenu();
                    }
                } else {
                    System.out.println("User does not exist...Try Again!!!");
                }
                return true;
            case 2:
                return true;
            case 3:
                return true;
            case 4:
                return true;
            case 5:
                System.out.println("Thanks for visiting!!!");
                return false;
            default:
                System.out.println("Enter correct option\nTry Again!!!");
                return true;
        }
    }
}