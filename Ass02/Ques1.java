import java.util.*;

class Student {
    String name;
    int age;
    int RollNumber;
    String branch;
    static int LatestRollNumber = 0;

    /**
     * Default constructor
     */
    Student() {
        this.name = "Def Name";
        this.age = 0;
        this.RollNumber = ++LatestRollNumber;
        this.branch = "Def Branch";
    }

    /**
     * Constructor with fields
     * 
     * @param name   name of the student
     * @param age    age of the student
     * @param branch branch of the student
     */
    Student(String name, int age, String branch) {
        this.name = name;
        this.age = age;
        this.RollNumber = ++LatestRollNumber;
        this.branch = branch;
    }

    /**
     * Display the students details
     */
    void display() {
        System.out.println("Student Information is as follows: ");
        System.out.println("\tRoll Number: " + this.RollNumber);
        System.out.println("\tName: " + this.name);
        System.out.println("\tAge: " + this.age);
        System.out.println("\tBranch: " + this.branch);
        System.out.println();
    }
}

public class Ques1 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        Student[] students = new Student[10];

        students[0] = new Student();
        students[1] = new Student();
        students[2] = new Student();
        students[3] = new Student();
        students[4] = new Student();

        for (int i = 5; i < students.length; i++) {

            // Inputting the information of the students
            System.out.print("Enter the name of the Student: ");
            String name = input.nextLine();
            System.out.print("Enter the age of the Student: ");
            int age = input.nextInt();
            input.nextLine(); // To remove the garbage \n from the System.in
            System.out.print("Enter the branch of the Student: ");
            String branch = input.nextLine();
            System.out.println();

            students[i] = new Student(name, age, branch);
        }

        System.out.println();
        for (Student student : students) {
            student.display();
        }
        System.out.println("Latest Roll Number: " + Student.LatestRollNumber);

        input.close();
    }
}