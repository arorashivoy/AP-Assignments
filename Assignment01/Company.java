import java.util.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.lang.IndexOutOfBoundsException;

public class Company {
    // Company Info
    private String name;
    private String role;
    private float ctc;
    private float CGPA_Req;
    private Date registeringDate = null;

    private Boolean selectedStudents = false;

    private PlacementCell placementCell;

    // Students Array List
    private ArrayList<Student> appliedStudents = new ArrayList<>();
    private HashSet<Student> offeredStudents = new HashSet<>();
    private HashSet<Student> acceptedStudents = new HashSet<>();

    // Objects
    Scanner input = new Scanner(System.in);
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy HH:mm");
    Random rend = new Random();

    /**
     * Company constructor
     * 
     * @param name          Name of the company
     * @param role          role that the company is offering
     * @param ctc           ctc offered by the company
     * @param CGPA_Req      required CGPA to apply for the company
     * @param placementCell placement cell under which the company comes
     */
    Company(String name, String role, float ctc, float CGPA_Req, PlacementCell placementCell) {
        this.name = name;
        this.role = role;
        this.ctc = ctc;
        this.CGPA_Req = CGPA_Req;
        this.placementCell = placementCell;
    }

    /**
     * Input the registration date and register the company for placement drive
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
                if (registeringDate.compareTo(placementCell.getCompanyEnd()) > 0) {
                    System.out.println("Registration date has been passed");
                    break;
                }

                // If student registration start before company
                if (registeringDate.compareTo(placementCell.getCompanyStart()) < 0) {
                    System.out.println(
                            "Registrations has not started yet\nTry Again!!!");
                    continue;
                }

                placementCell.addCompany(this);

                break;
            }
            System.out.println("Successfully Registered for the Placement Drive");
        } else {
            System.out.println("Company is already registered for Placements");
        }
    }

    /**
     * Get a list of selected students
     */
    public void getSelectedStudent() {
        if (this.acceptedStudents.size() == 0) {
            System.out.println("No student has been offered");
            return;
        } else {
            System.out.println("The following students have been selected");
            int i = 1;
            for (Student s : this.acceptedStudents) {
                System.out.println("\t" + i + ") Name:\t" + s.getName());
                System.out.println("\t" + i + ") Roll No.:\t" + s.getRollNo());
                System.out.println("\t" + i + ") CGPA:\t" + s.getCGPA());
                System.out.println("\t" + i + ") Branch:\t" + s.getBranch());

                i += 1;
            }
        }
    }

    public void updateRole(String role) {
        this.role = role;
    }

    public void updateCTC(float ctc) {
        this.ctc = ctc;
    }

    public void updateCGPA_Req(float CGPA_Req) {
        this.CGPA_Req = CGPA_Req;
    }

    /**
     * Select some random students
     */
    public void selectStudents() {
        if (selectedStudents) {
            return;
        }
        selectedStudents = true;

        for (int i = 0; i < this.appliedStudents.size(); i++) {
            if (rend.nextInt(3) == 1) {
                this.offeredStudents.add(this.appliedStudents.get(i));
                this.appliedStudents.get(i).setOffered(this);
                this.appliedStudents.remove(i);
            }
        }

        if (this.offeredStudents.size() == 0) {
            try {
                this.offeredStudents.add(this.appliedStudents.get(0));
                this.appliedStudents.get(0).setOffered(this);
                this.appliedStudents.remove(0);
            } catch (IndexOutOfBoundsException e) {
                System.out.println("No student has applied");
                selectedStudents = false;
            }
        }
    }

    public void printSelectedStudents() {
        if (this.offeredStudents.size() > 0 || this.acceptedStudents.size() > 0) {
            int i = 1;
            System.out.println("The company has selected the following student:- ");
            for (Student s : this.acceptedStudents) {
                System.out.println("\t" + i + ") " + s.getName() + " (" + s.getRollNo() + ") has been placed");
                i += 1;
            }
            for (Student s : this.offeredStudents) {
                System.out.println("\t" + i + ") " + s.getName() + " (" + s.getRollNo() + ") has been offered");
                i += 1;
            }
        }
    }

    public void printCompanyDetails() {
        System.out.println("The Role offered by the company is " + this.role);
        System.out.println("The CTC offered by the company is (in LPA) " + this.ctc);
        System.out.println("The Required CGPA to apply for the company is " + this.CGPA_Req);
        if (this.offeredStudents.size() > 0) {
            int i = 1;
            System.out.println("The company has given offers to the following student:- ");
            for (Student s : this.offeredStudents) {
                System.out.println("\t" + i + ") " + s.getName() + " (" + s.getRollNo() + ")");
                i += 1;
            }
        }
    }

    ////////////////////////////// MENU OPTIONS ////////////////////////////////
    /**
     * Company specific menu where you select options for a particular company only
     * 
     * @return true, to show the menu again else, false
     */
    public Boolean companyMenu() {
        System.out.println("\n\nWelcome " + this.name);
        System.out.println("\tPRESS 1:\tTo update Role");
        System.out.println("\tPRESS 2:\tTo update Package");
        System.out.println("\tPRESS 3:\tTo update CGPA criteria");
        System.out.println("\tPRESS 4:\tTo Register for Institute Placement Drive");
        System.out.println("\tPRESS 5:\tTo go Back");
        System.out.print("Enter your choice: ");
        int a = input.nextInt();
        input.nextLine();

        switch (a) {
            case 1:
                System.out.print("Enter the new Role: ");
                String role = input.nextLine().strip();
                this.updateRole(role);
                System.out.println("Role has been updated to " + role);
                return true;
            case 2:
                System.out.print("Enter the new Package(in LPA): ");
                float _package = input.nextFloat();
                input.nextLine();
                this.updateCTC(_package);
                System.out.println("Package  has been updated to " + _package);
                return true;
            case 3:
                System.out.print("Enter the new CGPA Criterial: ");
                float _cgpa = input.nextFloat();
                input.nextLine();
                this.updateCGPA_Req(_cgpa);
                System.out.println("CGPA criteria has been updated to " + _cgpa);
            case 4:
                this.registerForPlacement();
                return true;
            case 5:
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

    public float getCTC() {
        return this.ctc;
    }

    public float getCGPA_Req() {
        return this.CGPA_Req;
    }

    public Boolean hasSelectedStudents() {
        return this.selectedStudents;
    }

    public void addAppliedStudent(Student s) {
        this.appliedStudents.add(s);
    }

    public void offerAccepted(Student s) {
        this.offeredStudents.remove(s);
        this.acceptedStudents.add(s);
    }

    public void offerRejected(Student s) {
        this.offeredStudents.remove(s);
    }

    public String getRole() {
        return this.role;
    }
}
