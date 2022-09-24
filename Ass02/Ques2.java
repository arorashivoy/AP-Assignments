import java.util.*;

public class Ques2 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        // Inputting data using Type A constructor
        for (int i = 0; i < 5; i++) {
            System.out.print("Enter the value of the node: ");
            int value = input.nextInt();
            new SinglyLinkedList(value);
        }

        // Inputting data using Type B constructor
        for (int i = 5; i < 10; i++) {
            System.out.print("Enter the value of the node: ");
            int value = input.nextInt();
            new SinglyLinkedList(new SinglyLinkedList.Node(value));
        }

        SinglyLinkedList.display();
        SinglyLinkedList.find();

        input.close();
    }
}
