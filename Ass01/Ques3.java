// 
// Ques3.java
// By - Shivoy Arora
// 

import java.util.*;

/**
 * Extension of LinkedList to modify the toString() method to print the elements
 * in the list in the format specified in the question.
 * 
 * @author Shivoy Arora
 *
 */
class LinkedLst<E> extends LinkedList<E> {
    LinkedLst() {
        super();
    }

    /**
     * Overrides the toString() method of the LinkedList class to print the elements
     * in the list in the format specified in the question.
     */
    @Override
    public String toString() {
        Iterator<E> it = iterator();
        if (!it.hasNext())
            return "[]";

        StringBuilder sb = new StringBuilder();
        for (;;) {
            E e = it.next();
            sb.append(e == this ? "(this Collection)" : e);
            if (!it.hasNext())
                return sb.toString();
            sb.append('*');
        }
    }
}

public class Ques3 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        String s = input.nextLine();
        int len = s.length();

        LinkedLst<Integer> linkedLst = new LinkedLst<>();
        while (len % 2 == 0) {
            linkedLst.add(2);
            len /= 2;
        }

        for (int i = 3; i < Math.sqrt(len); i += 2) {
            while (len % i == 0) {
                linkedLst.add(i);
                len /= i;
            }
        }

        // If n is a prime number
        if (len > 2) {
            linkedLst.add(len);
        }

        System.out.println(linkedLst);

        input.close();
    }
}
