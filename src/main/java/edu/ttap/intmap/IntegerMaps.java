package edu.ttap.intmap;

import java.io.IOException;
import java.util.Scanner;
import java.io.File;
import java.util.Set;
import java.util.TreeSet;
import java.util.ArrayList;

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
 * 
 * Part C:
 * time complexity: is 0(n + r), 0(n) to count frequencies and o(r) to remake sorted array
 * space complexity: o(r), for array size of r
 * pros:
 * faster then mergsort/quicksort, when r is smaller (o(n log n))
 * 
 * cons:
 * one works on ints
 * if r is big, frequencies array wates memory
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

    static class LetterCounter{
        // backing array size - 256
        private static final int SIZE = 256;
        // key-value
        private static class Pair {
            char key;
            int value;


            Pair(char key, int value) {
                this.key = key;
                this.value = value;
            }
        }
        // backing array, each slot has a list of pairs
        private ArrayList<Pair>[] bucket;
        // surppress maybe ? 
        public LetterCounter() {
            bucket = new ArrayList[SIZE];
            for (int i = 0; i < SIZE; i++) {
                bucket[i] = new ArrayList<>();
            }
        }

        // maps a charachter to an index in the backing array
        // making large charachter values fit
        private int hash(char ch) {
            return (int) ch % SIZE;
        }

        // return true iff this maps has an entry for ch
        public boolean hasKey(char ch) {
            int index = hash(ch);
            for(Pair p: bucket[index]) {
                if (p.key == ch) {
                    return true; 
                }
            }
            return false; 
        }
        
        // ch with the right v, overwirte prior entry
        public void put(char ch, int v) {
            int index = hash(ch);
            //searc 
            for(Pair p: bucket[index]) {
                if (p.key == ch) {
                    p.value = v;
                    return;
                }
            }
            //what if no entry found
            // if no existing entry found, we add a new pair
            bucket[index].add(new Pair(ch, v));
        }

        //return value for ch or throw
        public int get(char ch) {
            int index = hash(ch);
            for(Pair p: bucket[index]) {
                if (p.key == ch) {
                    return p.value;
                }
            }
            throw new IllegalArgumentException("Key not found: " + ch);
        }
    }

    public static void reportCountsAllChars(String path) throws IOException{
        LetterCounter counter = new LetterCounter();

        Scanner scanner = new Scanner(new File(path));
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            for (char c : line.toCharArray()) {
                if(counter.hasKey(c)) {
                    counter.put(c, counter.get(c) + 1);
                } else {
                    // first time seeing this charchter so start at 1 (count)
                    counter.put(c, 1);
                }
            }
        }
        scanner.close();

        // print all 26 letters and their counts
        for(char c = 'a'; c<= 'z'; c++) {
            if(counter.hasKey(c)) {
                System.out.println(c + ": " + counter.get(c));
            }
        }
    }

    public static void countingSort(int range, int [] arr) {
        // we need to count frequencies first 

        int[] frequencies = new int[range];
        for(int num : arr) {
            frequencies[num]++;
        }

        // remake the sorted array from freqencies 
        int i = 0;
        for(int num = 0; num < range; num++) {
            for(int count = 0; count < frequencies[num]; count++) {
                arr[i] = num;
                i++;
            }
        }
    }






    public static void main(String[] args) throws IOException{
        // testing counting sort
        int[] arrA = {0, 3, 6, 7, 8};
        int[] arrB = {0, 0, 0, 2, 2, 2, 3, 3, 3, 8, 8, 9};
        int[] arrC = {1, 1, 4, 4, 7, 7, 9, 9};

        countingSort(10, arrA);
        countingSort(10, arrB);
        countingSort(10, arrC);

        System.out.println("A: " + java.util.Arrays.toString(arrA));
        System.out.println("B: " + java.util.Arrays.toString(arrB));
        System.out.println("C: " + java.util.Arrays.toString(arrC));

        //reportCountsAllChars(args[0]);
        
        


    }   
}
