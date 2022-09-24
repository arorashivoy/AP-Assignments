// 
// Ques1.java
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
            sb.append(' ');
        }
    }
}

public class Ques1 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        int t = input.nextInt();

        for (int i = 0; i < t; i++) {
            int n = input.nextInt();
            LinkedLst<Integer> linkedLst = new LinkedLst<>();
            for (int j = 0; j < n; j++) {
                int x = input.nextInt();
                linkedLst.add(x);
            }

            int x = input.nextInt();
            if (x <= n && x > 0) {
                System.out.println("True");
                System.out.println(n - x + 1);
                linkedLst.remove(x - 1);
                System.out.println(linkedLst);
            } else {
                System.out.println("False");
                System.out.println(0);
                System.out.println(linkedLst);
            }

        }

        input.close();
    }
}