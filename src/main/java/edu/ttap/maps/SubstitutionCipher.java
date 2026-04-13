package edu.ttap.maps;
import java.util.Map;
import java.util.Scanner;
import java.util.HashSet;
import java.util.Set;
import java.io.File;
import java.io.IOException;


/**
 * A substitution cipher is a simple encryption scheme that associates each
 * letter of the alphabet with a different letter.
 */
public class SubstitutionCipher {
    /**
     * Creates a substitution cipher by reading a mapping of characters from the given
     * file. Each mapping of the file should be of the form "a b", where 'a' is mapped to
     * 'b' in the cipher. We require 
     * @param filename the name of the file containing the mapping
     * @return the cipher as a mapping between characters
     */
    public static Map<Character, Character> createCipher(String filename) throws IOException {
        // TODO: implement me!
        Map<Character, Character> characters = new AssociationList<>();
        Scanner scanner = new Scanner(new File(filename));
        while (scanner.hasNextLine()) {
           String line = scanner.nextLine().trim();
           if (!line.isEmpty()) {
                // Each line is "a b" — split on whitespace
                String[] parts = line.split("\\s+");
                char key = parts[0].charAt(0);
                char value = parts[1].charAt(0);
                characters.put(key, value);
           }
        }
        scanner.close();
        return characters;
    }

    /**
     * Determines whether the given mapping is a valid substitution cipher. A cipher is
     * valid if (a) it maps every letter of the alphabet (aâ€“z) and (b) it is a bijection,
     * i.e., no two letters map to the same letter (so that we can roundtrip encode/decode
     * a message without loss of fidelity).
     * @param cipher
     * @return true iff the given mapping is a valid substitution cipher
     */
    public static boolean isValidCipher(Map<Character, Character> cipher) {
        // TODO: implement me!
        for(char c = 'a'; c <= 'z'; c++) {
            if(!cipher.containsKey(c)) {
                return false;
            }
        }
        Set<Character> seen = new HashSet<>();
        for(char c = 'a'; c <= 'z'; c++){
            char mapped = cipher.get(c);
            if(seen.contains(mapped)) {
                return false;
            }
            seen.add(mapped);

        }
        return true;
        
    }

    /**
     * Given a valid substitution cipher, produces the inverse mapping, which
     * can be used to decode the encoded massage. For example, if the cipher
     * maps 'a' to 'b', then the inverse mapping should map 'b' to 'a'.
     * @param cipher the cipher to invert
     * @return the inverse mapping of the given cipher
     */
    public static Map<Character, Character> invertCipher(Map<Character, Character> cipher) {
        // TODO: implement me!
        Map<Character, Character> inverse = new AssociationList<>();
        for(char c = 'a'; c <= 'z'; c++){
            char mapped = cipher.get(c);
            inverse.put(mapped, c);
        }
        return inverse; 
    }

    /**
     * Translates the given string using the provided mapping.
     * @param s the string to translate
     * @param mapping the mapping to use
     * @return the translated string
     */
    public static String translate(String s, Map<Character, Character> mapping) {
        // TODO: implement me!
        StringBuilder sb = new StringBuilder();
        for(char c: s.toCharArray()){
            if(mapping.containsKey(c)) {
                sb.append(mapping.get(c));
            } else {
                //space and unknow characters, pass without change
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * The main driver for the substitution cipher program.
     * @param args the driver's command-line arguments
     */
    public static void main(String[] args) {
        if (args.length != 3) {
            System.err.println(
                "Usage: java SubstitutionCipher <encrypt|decrypt> <cipherfile> <filename>");
            System.exit(1);
        }
        // TODO: finish implementing me!

        String mode = args[0];
        String cipherFile = args[1];
        String textFile = args[2];

        try {// read cipher
            Map<Character, Character> cipher = createCipher(cipherFile);
            // is valid
            if(!isValidCipher(cipher)){
                System.err.println("ERROR: Invalid cipher - must map all letters");
            System.exit(1);
            } 

            // find which mapping to use
            Map<Character, Character> mapping;
            if (mode.equals("decrypt")){
                mapping = invertCipher(cipher);
            } else if (mode.equals("encrypt")) {
                mapping = cipher;
            }else {
                System.err.println("ERROR: first argument must be 'encrypt' or 'decrypt' .");
                System.exit(1);
                return; 
            }

            Scanner scanner = new Scanner(new File(textFile));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                System.out.println(translate(line, mapping));
            }
            scanner.close();
    
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            System.exit(1);
        }
    }
}