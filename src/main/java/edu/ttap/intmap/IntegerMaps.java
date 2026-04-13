package edu.ttap.intmap;

import java.io.IOException;
import java.util.Scanner;
import java.io.File;

public class IntegerMaps {

    public static void reportCounts(String path) throws IOException{
        int [] counts = new int[26];

        Scanner scanner = new Scanner(new File(path));
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            for (char c : line.toCharArray()) {
                c = Character.toLowerCase(c);
                if(c >= 'a' && c <='z'){
                    counts[c - 'a']++;
                }
            }
        }
        scanner.close();

        // print result 

        for(int i = 0; i < 26; i++) {
            char letter = (char) ('a' + i);
            System.out.println(letter + ": " + counts[i]);
        }
    }
    public static void main(String[] args) throws IOException{
        reportCounts(args[0]);
    }   
}
