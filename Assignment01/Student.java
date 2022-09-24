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
    HashMap<Company, companyStatus> companies = new HashMap<>();
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
        if (this.companies.get(company) != null && this.companies.get(company) == companyStatus.NOT_APPLIED
                && company.getCTC() >= 3 * this.highestCTC && !this.placed) {
            this.companies.put(company, companyStatus.APPLIED);
        }
    }

    /**
     * Get a list of all the available companies
     */
    public void getAvailableCompany() {
        boolean flag = false;
        for (Company company : this.companies.keySet()) {
            if (this.companies.get(company) == companyStatus.NOT_APPLIED) {
                flag = true;
                System.out.println(company.getName());
            }
        }

        if (!flag) {
            System.out.println("unavailable");
        }
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
                companies.put(company, companyStatus.NOT_ELIGIBLE);
            } else {
                if (companies.getOrDefault(company, companyStatus.NOT_ELIGIBLE) != companyStatus.OFFERED
                        || companies.getOrDefault(company, companyStatus.NOT_ELIGIBLE) != companyStatus.PLACED
                        || companies.getOrDefault(company, companyStatus.NOT_ELIGIBLE) != companyStatus.REJECTED
                        || companies.getOrDefault(company, companyStatus.NOT_ELIGIBLE) != companyStatus.APPLIED)
                    companies.put(company, companyStatus.NOT_APPLIED);
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
            if (companies.get(company) == companyStatus.OFFERED && company.getCTC() >= this.highestCTC) {
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
        companies.put(highestCTCCompany, companyStatus.PLACED);
    }

    /**
     * Reject the offer
     */
    public void rejectOffer() {
        // TODO Do for company
        companies.put(highestCTCCompany, companyStatus.REJECTED);
        updateOffer();

        // If rejected all the offers
        if (this.highestCTCCompany == null) {
            this.blocked = true;
            PlacementCell.numBlocked += 1;

            for (Company company : placementCell.getCompanies().values()) {
                companies.put(company, companyStatus.NOT_ELIGIBLE);
            }

            System.out.println("BLOCKED");
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
                companies.put(company, companyStatus.OFFERED);
                break;
            case 1:
                companies.put(company, companyStatus.APPLIED);
                break;
            case 2:
                companies.put(company, companyStatus.NOT_APPLIED);
                break;
            case 3:
                companies.put(company, companyStatus.PLACED);
                break;
            case 4:
                companies.put(company, companyStatus.NOT_ELIGIBLE);
                break;
            case 5:
                companies.put(company, companyStatus.REJECTED);
                break;
            default:
        }
    }

    ///////////////////////////////// HELPERS //////////////////////////////////
    /**
     * Status of a company for a student
     */
    private enum companyStatus {
        OFFERED, APPLIED, NOT_APPLIED, PLACED, NOT_ELIGIBLE, REJECTED
    }
}
