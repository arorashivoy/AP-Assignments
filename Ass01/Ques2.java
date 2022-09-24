// 
// Ques2.java
// By - Shivoy Arora
// 

import java.util.*;

public class Ques2 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String s = new String();

        char prevChar = ' ';
        String data = input.nextLine();

        int freq = 0;
        for (int i = 0; i < data.length(); i++) {
            char c = data.charAt(i);
            if (prevChar == ' ') {
                prevChar = c;
                s += c;
                freq = 1;
            } else if (c == prevChar) {
                freq++;
            } else {
                s += freq;
                prevChar = c;
                s += prevChar;
                freq = 1;
            }
        }
        s += freq;

        System.out.println(s);

        input.close();
    }
}
