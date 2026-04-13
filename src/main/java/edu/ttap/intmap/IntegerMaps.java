package edu.ttap.intmap;

import java.io.IOException;
import java.util.Scanner;
import java.io.File;
import java.util.Set;
import java.util.TreeSet;

/**
 * Books tested:
 * 1. War and Peace by Leo Tolstoy - http://www.gutenberg.org/ebooks/2600
 *    Observation: The letter frequencies are consistent with known English frequency patterns.
 *    'e' is the most common letter, followed by 't' and 'a', which matches.
 * Using reportcounts.
 * 
 * countChars tested on:
 * 2. Plato's Republic: https://www.gutenberg.org/ebooks/39493 
 *    Found 154 unique charcters 
 *  The techinqure from part 1 (using c - 'a') as an index) would not work for this
 *  book because it doesnt contain non-English characters with larger character val.
 *  (greek letters up to value 974) with would cause array index out of bounds.  
 * 
 */


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

    /**
     * Counts and reports the number of unique character in a text file,
     * its case-sensitive and includes all character(spaces, punctiuation and etc..)
     * @param path the file path to the text file
     * @return the number of unque characters found 
     */

    public static int countChars(String path) throws IOException {
        // use tree set instead, to store the unique characters 
        Set<Character> uniqueChars = new TreeSet<>();

        Scanner scanner = new Scanner(new File(path));
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            for( char c: line.toCharArray()) {
                uniqueChars.add(c); // so we already handeled dups
            }
            
        }
        scanner.close();

        // print all on one line 
        for(char c : uniqueChars) {
            System.out.print(c + ": " + (int) c + " ");
        }
        System.out.println();

        return uniqueChars.size();
    }





    public static void main(String[] args) throws IOException{
        reportCounts(args[0]);

        System.out.println("Unique chars: " + countChars(args[0]));
    }   
}
