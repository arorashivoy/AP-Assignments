import java.util.*;

// TODO Change texts for each option

@SuppressWarnings("unused")
public class FutureBuilder {
    public static HashMap<String, Company> companies = new HashMap<>();
    public static HashMap<String, Student> students = new HashMap<>();

    static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        // Placement Cell
        PlacementCell placementCell = new PlacementCell();
        // Menu Screens
        Boolean futureBuilder = true;
        Boolean insideApp = false;
        Boolean studentMode = false;
        Boolean companyMode = false;
        Boolean placementCellMode = false;

        // Main Menu
        while (futureBuilder) {
            System.out.println("\n\nWelcome to the Future Builder:");
            System.out.println("\tPRESS 1: To Enter the Application");
            System.out.println("\tPRESS 2: To Exit the Application");
            System.out.print("Enter your choice: ");
            int a = input.nextInt();

            if (a == 1) {
                insideApp = true;
            } else if (a == 2) {
                break;
            } else {
                System.out.println("Enter correct option\nTry Again!!!");
                continue;
            }

            // Mode entering menu
            while (insideApp) {
                System.out.println("\n\nChoose the mode you want to Enter in");
                System.out.println("PRESS 1:\tTo Enter as Student Mode");
                System.out.println("PRESS 2:\tTo Enter as Company Mode");
                System.out.println("PRESS 3:\tTo Enter as Placement Cell Mode");
                System.out.println("PRESS 4:\tTo Return to the Main Menu");
                System.out.print("Enter your choice: ");
                a = input.nextInt();
                if (a == 1) {
                    studentMode = true;
                } else if (a == 2) {
                    companyMode = true;
                    while (companyMode) {
                        companyMode = companyModeMenu(placementCell);
                    }
                } else if (a == 3) {
                    placementCellMode = true;
                    while (placementCellMode) {
                        placementCellMode = placementCell.placementCellModeMenu();
                    }
                } else if (a == 4) {
                    break;
                } else {
                    System.out.println("Enter correct option\nTry Again!!!");
                    continue;
                }
            }
        }
    }

    ////////////////////////////// MENU OPTIONS ////////////////////////////////
    /**
     * Company related menu for all the companies collectively
     * 
     * @param placementCell placement cell object
     * @return true, to show the menu again else, false
     */
    private static Boolean companyModeMenu(PlacementCell placementCell) {
        Boolean insideCompany = false;
        System.out.println("\n\nChoose the Company Query to perform");
        System.out.println("\tPRESS 1: To add Company and Details");
        System.out.println("\tPRESS 2: To choose Company");
        System.out.println("\tPRESS 3: To get Available Companies");
        System.out.println("\tPRESS 4: To go back");
        System.out.print("Enter your choice: ");
        int a = input.nextInt();
        switch (a) {
            case 1:
                System.out.print("Enter the name of the Company: ");
                String name = input.nextLine().strip();
                System.out.print("Enter the role of the Company: ");
                String role = input.nextLine().strip();
                System.out.print("Enter the CTC(in LPA) of the Company: ");
                float ctc = input.nextFloat();
                System.out.print("Enter the Require CGPA to apply for the Company: ");
                float cgpa = input.nextFloat();

                if (FutureBuilder.companies.get(name) != null) {
                    System.out.println("The company already exists");
                    return true;
                }

                FutureBuilder.companies.put(name, new Company(name, role, ctc, cgpa, placementCell));
                return true;

            case 2:
                System.out.print("Choose to Enter into the mode of ");
                ArrayList<Company> companyObjects = getAvailableCompany();

                Company selectedCompany = null;
                int b = input.nextInt();
                try {
                    selectedCompany = companyObjects.get(b - 1);
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Enter correct option\nTry Again!!!");
                    return true;
                }
                insideCompany = true;
                while (insideCompany) {
                    insideCompany = selectedCompany.companyMenu();
                }
                return true;

            case 3:
                getAvailableCompany();
                return true;
            case 4:
                return false;
            default:
                System.out.println("Enter correct option\nTry Again!!!");
                return true;
        }
    }

    private static Boolean studentModeMenu(PlacementCell placementCell) {
        System.out.println("\n\nChoose the Student Query you want to perform:");
        System.out.println("\tPRESS 1: To Enter as a Student");
        System.out.println("\tPRESS 2: To Add Students");
        System.out.println("\tPRESS 3: To go Back");
        System.out.print("Enter your Choice: ");

        int a = input.nextInt();
        switch (a) {
            case 1:
            case 2:
            case 3:
                return false;
            default:
                System.out.println("Enter correct option\nTry Again!!!");
                return true;
        }
    }

    ///////////////////////////////// HELPERS //////////////////////////////////
    /**
     * Get all the available company that can or have registered for Placement drive
     * 
     * @return The Array List of the companies
     */
    private static ArrayList<Company> getAvailableCompany() {
        System.out.println("Available Companies:");
        int i = 1;
        ArrayList<Company> companyObjects = new ArrayList<>(companies.values());
        for (Company c : companyObjects) {
            System.out.println(i + ")\t" + c.getName());
            i += 1;
        }

        return companyObjects;
    }
}