import java.util.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@SuppressWarnings("unused")
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
        if (registeringDate != null) {
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
        }
    }

    public void registerForCompany(Company company) {
        // TODO change in company's list of students
        if (this.companies.get(company) != null && this.companies.get(company) == CompanyStatus.NOT_APPLIED
                && company.getCTC() >= 3 * this.highestCTC && !this.placed) {
            this.companies.put(company, CompanyStatus.APPLIED);
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
        for (Company company : this.companies.keySet()) {
            if (this.companies.get(company) == CompanyStatus.NOT_APPLIED) {
                flag = true;
                System.out.println("\t" + i + ") " + company.getName());
                compObjects.add(company);
                i += 1;
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
     * Update the status of each company based on CGPA
     */
    private void updateCompaniesStatus() {
        for (Company company : placementCell.getCompanies().values()) {
            // TODO add student to company's list of students
            if (this.CGPA < company.getCGPA_Req()) {
                companies.put(company, CompanyStatus.NOT_ELIGIBLE);
            } else {
                if (companies.getOrDefault(company, CompanyStatus.NOT_ELIGIBLE) != CompanyStatus.OFFERED
                        || companies.getOrDefault(company, CompanyStatus.NOT_ELIGIBLE) != CompanyStatus.PLACED
                        || companies.getOrDefault(company, CompanyStatus.NOT_ELIGIBLE) != CompanyStatus.REJECTED
                        || companies.getOrDefault(company, CompanyStatus.NOT_ELIGIBLE) != CompanyStatus.APPLIED)
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
        for (Company company : companies.keySet()) {
            if (companies.get(company) == CompanyStatus.OFFERED && company.getCTC() >= this.highestCTC) {
                this.highestCTC = company.getCTC();
                this.highestCTCCompany = company;
            }
        }
    }

    /**
     * Accept the offer
     */
    public void acceptOffer() {
        this.placed = true;
        PlacementCell.numPlaced += 1;
        // TODO Do for company
        companies.put(highestCTCCompany, CompanyStatus.PLACED);
    }

    /**
     * Reject the offer
     */
    public void rejectOffer() {
        // TODO Do for company
        companies.put(highestCTCCompany, CompanyStatus.REJECTED);
        updateOffer();

        // If rejected all the offers
        if (this.highestCTCCompany == null) {
            this.blocked = true;
            PlacementCell.numBlocked += 1;

            for (Company company : placementCell.getCompanies().values()) {
                companies.put(company, CompanyStatus.NOT_ELIGIBLE);
            }

            System.out.println("BLOCKED");
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
        int a = input.nextInt();

        switch (a) {
            case 1:
                this.registerForPlacement();
                return true;
            case 2:
                System.out.print("Choose to register for the ");
                ArrayList<Company> compObject = this.getAvailableCompany();

                Company selectedCompany = null;
                int b = input.nextInt();

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

    public void setOffered(Boolean offered) {
        this.offered = offered;
    }

    public void setCompanyStatus(Company company, int status) {
        switch (status) {
            case 0:
                companies.put(company, CompanyStatus.OFFERED);
                break;
            case 1:
                companies.put(company, CompanyStatus.APPLIED);
                break;
            case 2:
                companies.put(company, CompanyStatus.NOT_APPLIED);
                break;
            case 3:
                companies.put(company, CompanyStatus.PLACED);
                break;
            case 4:
                companies.put(company, CompanyStatus.NOT_ELIGIBLE);
                break;
            case 5:
                companies.put(company, CompanyStatus.REJECTED);
                break;
            default:
        }
    }

    ///////////////////////////////// HELPERS //////////////////////////////////
    /**
     * Status of a company for a student
     */
    private enum CompanyStatus {
        OFFERED, APPLIED, NOT_APPLIED, PLACED, NOT_ELIGIBLE, REJECTED
    }
}