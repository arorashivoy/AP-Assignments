import java.util.*;
import java.util.Map.Entry;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Student {
    // Student Info
    private String name;
    private String rollNo;
    private String branch;
    private float CGPA;
    private Date registeringDate = null;

    private PlacementCell placementCell;

    // Objects
    Scanner input = new Scanner(System.in);
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy HH:mm");

    // Student placement status
    HashMap<Company, CompanyStatus> companies = new HashMap<>();
    private boolean registeredForPlacement = false;
    private boolean offered = false;
    private boolean placed = false;
    private boolean blocked = false;
    private float highestCTC = 0;
    private Company highestCTCCompany = null;

    /**
     * Constructor for Student class
     * 
     * @param name          Name of the student
     * @param rollNo        Roll number of the student
     * @param branch        Branch of the student
     * @param CGPA          CGPA of the student
     * @param placementCell the placement cell of the college
     */
    public Student(String name, String rollNo, String branch, float CGPA, PlacementCell placementCell) {
        this.name = name;
        this.rollNo = rollNo;
        this.branch = branch;
        this.CGPA = CGPA;
        this.placementCell = placementCell;

        updateCompaniesStatus();
    }

    /**
     * Method to register a student for placement
     */
    public void registerForPlacement() {
        if (registeringDate == null) {
            while (true) {
                System.out.print("Enter the registering Date and time (DD/MM/YY HH:MM): ");
                String in = input.nextLine();

                try {
                    registeringDate = formatter.parse(in);
                } catch (ParseException p) {
                    System.out.println("Enter a valid date format\nTry Again!!!");
                    continue;
                }

                // Start date is not after end date
                if (registeringDate.compareTo(placementCell.getStudentEnd()) > 0) {
                    System.out.println("Registration date has been passed");
                    break;
                }

                // If student registration start before company
                if (registeringDate.compareTo(placementCell.getStudentStart()) < 0) {
                    System.out.println(
                            "Registrations has not started yet\nTry Again!!!");
                    continue;
                }

                this.registeredForPlacement = true;
                // PlacementCell.students.put(this.rollNo, this);
                placementCell.addStudent(this);

                break;
            }
            System.out.println("Successfully Registered for the Placement Drive");
        } else {
            System.out.println("Student is already registered for Placements");
        }
    }

    public void registerForCompany(Company company) {
        if (this.companies.get(company) != null && this.companies.get(company) == CompanyStatus.NOT_APPLIED
                && company.getCTC() >= 3 * this.highestCTC && !this.placed) {
            this.companies.put(company, CompanyStatus.APPLIED);
            company.addAppliedStudent(this);
            System.out.println("You have successfully registered for the company " + company.getName());
        } else {
            System.out.println("You cannot apply to the company " + company.getName());
        }
    }

    /**
     * Get a list of all the available companies
     * 
     * @return LinkedList of all the available companies
     */
    public ArrayList<Company> getAvailableCompany() {
        boolean flag = false;
        System.out.println("Available Companies:");
        int i = 1;
        ArrayList<Company> compObjects = new ArrayList<>();
        for (Entry<Company, CompanyStatus> e : this.companies.entrySet()) {
            try {
                if (e.getValue() == CompanyStatus.NOT_APPLIED) {
                    flag = true;
                    System.out.println("\t" + i + ")\tName:\t\t" + e.getKey().getName());
                    System.out.println("\t\tRole:\t\t" + e.getKey().getRole());
                    System.out.println("\t\tCTC:\t\t" + e.getKey().getCTC());
                    System.out.println("\t\tCGPA Requirement:\t" + e.getKey().getCGPA_Req());
                    compObjects.add(e.getKey());
                    i += 1;

                }
            } catch (IllegalStateException error) {
                continue;
            }

        }

        if (!flag) {
            System.out.println("No Companies Available");
        }

        return compObjects;
    }

    /**
     * Get the current status of a student
     * 
     * @param thirdPerson whether to show the result in third person or not
     */
    public void getCurrentStatus(boolean thirdPerson) {
        placementCell.getCompaniesResult();
        if (this.blocked) {
            System.out.println((thirdPerson ? (this.name + "is ") : "You are ") + "blocked from placement");
        } else if (placed) {
            if (highestCTCCompany != null) {
                System.out.println(
                        (thirdPerson ? (this.name + "is") : "You are ") + "placed in " + highestCTCCompany.getName());
            } else {
                System.out.println((thirdPerson ? (this.name + "is ") : "You are ") + "placed");
            }
        } else if (offered) {
            if (highestCTCCompany != null) {
                System.out.println(
                        (thirdPerson ? (this.name + "is ") : "You are ") + "offered in " + highestCTCCompany.getName());
            } else {
                System.out.println((thirdPerson ? (this.name + "is ") : "You are ") + "offered from a company");
            }
        } else if (registeredForPlacement) {
            System.out.println((thirdPerson ? (this.name + "is ") : "You are ") + "registered for placement");
        } else {
            System.out.println((thirdPerson ? (this.name + "is ") : "You are ") + "not registered for placement");
        }
    }

    /**
     * Accept the offer
     */
    public void acceptOffer() {
        this.placed = true;
        PlacementCell.numPlaced += 1;
        companies.put(highestCTCCompany, CompanyStatus.PLACED);
        highestCTCCompany.offerAccepted(this);

        System.out.println("You have accepted the offer by " + highestCTCCompany.getName());
    }

    /**
     * Reject the offer
     */
    public void rejectOffer() {
        companies.put(highestCTCCompany, CompanyStatus.REJECTED);
        highestCTCCompany.offerRejected(this);

        System.out.println("You have rejected the offer by " + highestCTCCompany.getName());
        updateOffer();

        // If rejected all the offers
        if (this.highestCTCCompany == null) {
            this.blocked = true;
            PlacementCell.numBlocked += 1;

            for (Company company : placementCell.getCompanies().values()) {
                companies.put(company, CompanyStatus.NOT_ELIGIBLE);
            }

            System.out.println("You are BLOCKED from Placement Drive");
        }

    }

    ////////////////////////////// MENU OPTIONS ////////////////////////////////
    public Boolean studentMenu() {
        System.out.println("\n\nWelcome " + this.name);
        System.out.println("\tPRESS 1:\tTo Register for Placement Drive");
        System.out.println("\tPRESS 2:\tTo Register for Company");
        System.out.println("\tPRESS 3:\tTo get all available Companies");
        System.out.println("\tPRESS 4:\tTo get Current Status");
        System.out.println("\tPRESS 5:\tTo Update CGPA");
        System.out.println("\tPRESS 6:\tTo Accept offer");
        System.out.println("\tPRESS 7:\tTo Reject offer");
        System.out.println("\tPRESS 8:\tTo go Back");
        System.out.print("Enter your choice: ");
        int a = input.nextInt();
        input.nextLine();

        switch (a) {
            case 1:
                this.registerForPlacement();
                return true;
            case 2:
                System.out.print("Choose to register for the ");
                ArrayList<Company> compObject = this.getAvailableCompany();

                System.out.print("Enter your choice: ");
                Company selectedCompany = null;
                int b = input.nextInt();
                input.nextLine();

                try {
                    selectedCompany = compObject.get(b - 1);
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Enter correct option\nTry Again!!!");
                    return true;
                }

                this.registerForCompany(selectedCompany);
                return true;
            case 3:
                this.getAvailableCompany();
                return true;
            case 4:
                this.getCurrentStatus(false);
                return true;
            case 5:
                System.out.print("Enter the new CGPA: ");
                float newCGPA = input.nextFloat();
                input.nextLine();
                placementCell.updateStudentCGPA(this, CGPA, newCGPA);
            case 6:
                this.acceptOffer();
                return true;
            case 7:
                this.rejectOffer();
                return true;
            case 8:
                return false;
            default:
                System.out.println("Enter correct option\nTry Again!!!");
                return true;
        }
    }

    ////////////////////////// Getters and Setters /////////////////////////////
    public String getName() {
        return this.name;
    }

    public String getRollNo() {
        return this.rollNo;
    }

    public void setOffered(Company company) {
        this.offered = true;
        companies.put(company, CompanyStatus.OFFERED);

        this.updateOffer();
    }

    public float getCGPA() {
        return this.CGPA;
    }

    public void setCGPA(float newCGPA) {
        this.CGPA = newCGPA;
    }

    public String getBranch() {
        return this.branch;
    }

    ///////////////////////////////// HELPERS //////////////////////////////////
    /**
     * Status of a company for a student
     */
    private enum CompanyStatus {
        OFFERED, APPLIED, NOT_APPLIED, PLACED, NOT_ELIGIBLE, REJECTED
    }

    /**
     * Update the status of each company based on CGPA
     */
    public void updateCompaniesStatus() {
        for (Company company : placementCell.getCompanies().values()) {
            if (this.CGPA < company.getCGPA_Req()) {
                companies.put(company, CompanyStatus.NOT_ELIGIBLE);
            } else {
                if (companies.getOrDefault(company, CompanyStatus.NOT_ELIGIBLE) != CompanyStatus.OFFERED
                        && companies.getOrDefault(company, CompanyStatus.NOT_ELIGIBLE) != CompanyStatus.PLACED
                        && companies.getOrDefault(company, CompanyStatus.NOT_ELIGIBLE) != CompanyStatus.REJECTED
                        && companies.getOrDefault(company, CompanyStatus.NOT_ELIGIBLE) != CompanyStatus.APPLIED)
                    companies.put(company, CompanyStatus.NOT_APPLIED);
            }
        }

        updateOffer();
    }

    /**
     * Update the highest offer
     */
    private void updateOffer() {
        this.highestCTC = 0;
        this.highestCTCCompany = null;
        for (Entry<Company, CompanyStatus> e : this.companies.entrySet()) {
            if (e.getValue() == CompanyStatus.OFFERED && e.getKey().getCTC() >= this.highestCTC) {
                this.highestCTC = e.getKey().getCTC();
                this.highestCTCCompany = e.getKey();
            }
        }

        if (highestCTCCompany == null) {
            this.offered = false;
        } else {
            this.offered = true;
        }
    }

}
