import java.util.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class PlacementCell {
    private HashMap<String, Company> companies = new HashMap<>();
    private HashMap<String, Student> students = new HashMap<>();

    // Objects
    Scanner input = new Scanner(System.in);
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy HH:mm");

    // Registration dates
    private Date studentStart = null;
    private Date studentEnd = null;
    private Date companyStart = null;
    private Date companyEnd = null;

    // Number of student
    static int numPlaced = 0;
    static int numBlocked = 0;

    /**
     * Open registration for student
     */
    public void openStudentRegistration() {
        // If registration date already exist
        if (studentStart != null && studentEnd != null) {
            System.out.println("Registration dates has already been put");
            return;
        }
        if (companyEnd != null && companyStart != null) {
            while (true) {
                System.out.print("Enter the opening time (DD/MM/YY HH:MM): ");
                String in = input.nextLine();

                try {
                    studentStart = formatter.parse(in);
                } catch (ParseException p) {
                    System.out.println("Enter a valid date format\nTry Again!!!");
                    continue;
                }

                System.out.print("Enter the closing time (DD/MM/YY HH:MM): ");
                in = input.nextLine();

                try {
                    studentEnd = formatter.parse(in);
                } catch (ParseException p) {
                    System.out.println("Enter a valid date format\nTry Again!!!");
                    continue;
                }

                // Start date is not after end date
                if (studentStart.compareTo(studentEnd) > 0) {
                    System.out.println("Student registration start should be before the end date\nTry Again!!!");
                    continue;
                }

                // If student registration start before company
                if (studentStart.compareTo(companyEnd) < 0) {
                    System.out.println(
                            "Registration for student should start after the company registration\nTry Again!!!");
                    continue;
                }

                break;
            }
        }
        // If company registration dates are not entered
        else {
            System.out.println("Enter the registration date of company first");
        }

    }

    /**
     * Open registration for company
     */
    public void openCompanyRegistration() {
        if (companyEnd == null || companyStart == null) {
            while (true) {
                System.out.print("Enter the opening time (DD/MM/YY HH:MM): ");
                String in = input.nextLine();

                try {
                    companyStart = formatter.parse(in);
                } catch (ParseException p) {
                    System.out.println("Enter a valid date format\nTry Again!!!");
                    continue;
                }

                System.out.print("Enter the closing time (DD/MM/YY HH:MM): ");
                in = input.nextLine();

                try {
                    companyEnd = formatter.parse(in);
                } catch (ParseException p) {
                    System.out.println("Enter a valid date format\nTry Again!!!");
                    continue;
                }

                // Start date is not after end date
                if (companyStart.compareTo(companyEnd) > 0) {
                    System.out.println("Student registration start should be before the end date\nTry Again!!!");
                    continue;
                }

                break;
            }
        }
        // If the registration date already exist
        else {
            System.out.println("Registration dates has already been put");
        }
    }

    public void getStudentNumber() {
        System.out.println(students.size());
    }

    public void getCompanyNumber() {
        System.out.println(companies.size());
    }

    /**
     * Get the number of students who are offered/unoffered/blocked
     */
    public void getStudentsInfo() {
        System.out.println("Number of Placed Students: " + numPlaced);
        System.out.println("Number of Unplaced Students: " + (students.size() - numPlaced - numBlocked));
        System.out.println("Number of Blocked Students: " + numBlocked);
    }

    public void getStudentDetail(Student student) {
        student.getCurrentStatus(true);
    }

    public void getAvgPackage() {
        float sum = 0;
        for (Company company : companies.values()) {
            sum += company.getCTC();
        }

        System.out.println("The average package is " + sum / companies.size());
    }

    public void updateStudentCGPA(Student s, float oldCGPA, float newCGPA) {
        if (oldCGPA == s.getCGPA()) {
            s.setCGPA(newCGPA);
            s.updateCompaniesStatus();
        } else {
            System.out.println("Wrong old CGPA inputted by the student");
        }
    }

    /**
     * Select some students from all the companies
     */
    public void getCompaniesResult() {
        for (Company c : companies.values()) {
            c.selectStudents();
        }
    }

    ////////////////////////////// MENU OPTIONS ////////////////////////////////
    /**
     * Placement cell options
     * 
     * @return true, to show the menu again else, false
     */
    public Boolean placementCellModeMenu() {
        System.out.println("\n\nWelcome to IIITD Placement Cell");
        System.out.println("\tPRESS 1:\tTo open Student Registration");
        System.out.println("\tPRESS 2:\tTo open Company Registration");
        System.out.println("\tPRESS 3:\tTo get the Number of Student Registration");
        System.out.println("\tPRESS 4:\tTo get the Number of Company Registration");
        System.out.println("\tPRESS 5:\tTo get the Number of Offered/Unoffered/Blocked Students");
        System.out.println("\tPRESS 6:\tTo get Student Details");
        System.out.println("\tPRESS 7:\tTo get Company Details");
        System.out.println("\tPRESS 8:\tTo get Average Package");
        System.out.println("\tPRESS 9:\tTo get Company Process Result");
        System.out.println("\tPRESS 10:\tTo go Back");
        System.out.print("Enter your choice: ");
        int a = input.nextInt();
        input.nextLine();
        switch (a) {
            case 1:
                this.openStudentRegistration();
                return true;
            case 2:
                this.openCompanyRegistration();
                return true;
            case 3:
                this.getStudentNumber();
                return true;
            case 4:
                this.getCompanyNumber();
                return true;
            case 5:
                this.getStudentsInfo();
                return true;
            case 6:
                System.out.print("\nEnter the Roll Number of the Student whose detail you want to see: ");
                String b = input.nextLine();
                Student s = students.get(b.strip());
                if (s == null) {
                    System.out.println("Record does not exist");
                    return true;
                }
                this.getStudentDetail(s);

                return true;

            case 7:
                System.out.print("\nEnter the Name of the Company whose detail you want to see: ");
                String d = input.nextLine();
                Company c = companies.get(d.strip());
                if (c == null) {
                    System.out.println("Record does not exist");
                    return true;
                }
                c.printCompanyDetails();
                return true;

            case 8:
                this.getAvgPackage();
                return true;
            case 9:
                System.out.print("\nEnter the Number of the Company whose result you want to see: ");
                String e = input.nextLine();
                Company comp = companies.get(e.strip());
                if (comp == null) {
                    System.out.println("Record does not exist");
                    return true;
                }
                comp.selectStudents();
                if (comp.hasSelectedStudents()) {
                    comp.printSelectedStudents();
                }
                return true;
            case 10:
                return false;
            default:
                System.out.println("Enter correct option\nTry Again!!!");
                return true;
        }
    }

    ////////////////////////// Getters and Setters /////////////////////////////
    public HashMap<String, Company> getCompanies() {
        return companies;
    }

    public HashMap<String, Student> getStudents() {
        return students;
    }

    public void addStudent(Student student) {
        this.students.put(student.getRollNo(), student);
    }

    public void addCompany(Company company) {
        this.companies.put(company.getName(), company);
    }

    public Date getStudentStart() {
        return this.studentStart;
    }

    public Date getStudentEnd() {
        return this.studentEnd;
    }

    public Date getCompanyStart() {
        return this.companyStart;
    }

    public Date getCompanyEnd() {
        return this.companyEnd;
    }
}
