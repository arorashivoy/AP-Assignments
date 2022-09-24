public class SinglyLinkedList {
    static Node head = null;

    static class Node {
        int value;
        Node next;

        Node(int value) {
            this.value = value;
            this.next = null;
        }
    }

    /**
     * Add a node to the linked list
     *
     * @param node node to be added to the linked list
     */
    SinglyLinkedList(Node node) {
        if (SinglyLinkedList.head == null) {
            SinglyLinkedList.head = node;
        } else {
            Node current = SinglyLinkedList.head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = node;
        }
    }

    /**
     * Add a node with the value to the linked list
     *
     * @param value value of the node to be added in the linked list
     */
    SinglyLinkedList(int value) {
        new SinglyLinkedList(new Node(value));
    }

    static void sort() {
        Node current = SinglyLinkedList.head;
        while (current != null) {
            Node next = current.next;
            while (next != null) {
                if (current.value > next.value) {
                    int temp = current.value;
                    current.value = next.value;
                    next.value = temp;
                }
                next = next.next;
            }
            current = current.next;
        }
    }

    /**
     * Printing the list
     */
    static void display() {
        SinglyLinkedList.sort();
        Node current = SinglyLinkedList.head;
        while (current != null) {
            System.out.print(current.value + " ");
            current = current.next;
        }
        System.out.println();
    }

    /**
     * Printing the first and last element in the list
     */
    static void find() {
        SinglyLinkedList.sort();
        Node current = SinglyLinkedList.head;
        if (current != null) {
            System.out.print("First element: " + current.value + "\t");

            while (current.next != null) {
                current = current.next;
            }
            System.out.println("Last element: " + current.value);
        }
    }
}