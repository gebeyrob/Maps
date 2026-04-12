package edu.ttap.lootgenerator;

import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Random;
import java.util.HashMap;

public class LootGenerator {
    /** The path to the dataset (either the small or large set). */
    private static final String DATA_SET = "data/small";

    public static Map<String, Armor> parseArmor(String armorFile) throws FileNotFoundException {
        Map<String, Armor> armorData = new HashMap<>();
        Scanner input = new Scanner(new File(armorFile));

        while (input.hasNextLine()) {
            String line = input.nextLine();
            if (line.isEmpty()) {
                continue;
            } // in case people throw in empty lines for fun

            String[] parts = line.split("\t"); // split string into components for a parts array
            String name = parts[0]; // name is first part
            int minAc = Integer.parseInt(parts[1]); // minAc is second part
            int maxAc = Integer.parseInt(parts[2]); // maxAc is third part

            Armor armor = new Armor(name, minAc, maxAc); // make a new armor item
            armorData.put(name, armor); // put it into the map
        }

        input.close();
        return armorData;

    }

    public static List<Monster> parseMonsters(String filename) throws FileNotFoundException {
        List<Monster> monsters = new ArrayList<>();
        Scanner input = new Scanner(new File(filename));

        // similar logic to parseArmor
        while (input.hasNextLine()) {
            String line = input.nextLine();
            String[] parts = line.split("\t");
            String name = parts[0];
            String type = parts[1];
            int level = Integer.parseInt(parts[2]);
            String treasureClass = parts[3];
            monsters.add(new Monster(name, type, level, treasureClass));
        }

        input.close();
        return monsters;

    }

    public static Map<String, TreasureClass> parseTreasureClasses(String treasureFile) throws FileNotFoundException {
        Map<String, TreasureClass> treasures = new HashMap<>();
        Scanner input = new Scanner(new File(treasureFile));

        while (input.hasNextLine()) {
            String line = input.nextLine();
            if (line.isEmpty()) {
                continue;
            }

            String[] parts = line.split("\t"); // same structure as before
            String name = parts[0];
            List<String> drops = new ArrayList<>();
            drops.add(parts[1]);
            drops.add(parts[2]);
            drops.add(parts[3]);

            TreasureClass treasure = new TreasureClass(name, drops);
            treasures.put(name, treasure); // put it into the map
        }

        input.close();
        return treasures;

    }

    public static List<Affix> parseAffixes(String filename) throws FileNotFoundException {
        List<Affix> affixes = new ArrayList<>();
        Scanner input = new Scanner(new File(filename));

        // similar logic to parseArmor
        while (input.hasNextLine()) {
            String line = input.nextLine();
            String[] parts = line.split("\t");
            String name = parts[0];
            String mod1code = parts[1];
            int mod1min = Integer.parseInt(parts[2]);
            int mod1max = Integer.parseInt(parts[3]);
            affixes.add(new Affix(name, mod1code, mod1min, mod1max));
        }

        input.close();
        return affixes;

    }

    public static Monster pickMonster(List<Monster> monsters, Random generator) {
        int index = generator.nextInt(monsters.size());
        return monsters.get(index);
    }

    public static String generateBaseItem(String tc, Map<String, TreasureClass> treasureData, Random generator) {
        TreasureClass treasure = treasureData.get(tc);

        if (treasure == null) {
            throw new IllegalArgumentException("Unknown treasure class: " + tc);
        }

        String drop = treasure.getRandomDrop(generator);

        if (treasureData.containsKey(drop)) {
            return generateBaseItem(drop, treasureData, generator);
        }

        return drop;
    }

    public static String[] generateAffix(List<Affix> affixes, Random rand) {
        Affix randAff = affixes.get(rand.nextInt(affixes.size()));
        int randValue = rand.nextInt(randAff.getModMax() - randAff.getModMin() + 1) + randAff.getModMin();
        return new String[] { randAff.getName(), randValue + " " + randAff.getModCode() };
    }

    public static String generateBaseStats(Armor armor, Random rand) {
        int defense = rand.nextInt(armor.getMaxAc() - armor.getMinAc() + 1) + armor.getMinAc();
        return "Defense: " + defense;
    }

    public static boolean askFightAgain(Scanner console) {
        while (true) {
            System.out.print("Fight again [y/n]? ");
            String input = console.nextLine();

            if (input.equalsIgnoreCase("y")) {
                return true;
            } else if (input.equalsIgnoreCase("n")) {
                return false;
            }
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("This program kills monsters and generates loot!");
        Scanner console = new Scanner(System.in);
        Random generator = new Random();
        Map<String, Armor> armorData = parseArmor(DATA_SET + "/armor.txt");
        List<Monster> monsters = parseMonsters(DATA_SET + "/monstats.txt");
        Map<String, TreasureClass> treasureData = parseTreasureClasses(DATA_SET + "/TreasureClassEx.txt");
        List<Affix> prefixes = parseAffixes(DATA_SET + "/MagicPrefix.txt");
        List<Affix> suffixes = parseAffixes(DATA_SET + "/MagicSuffix.txt");

        while (true) {
            Monster monster = pickMonster(monsters, generator);
            System.out.println("Fighting " + monster.getName() + "...");
            System.out.println("You have slain " + monster.getName() + "!");
            System.out.println(monster.getName() + " dropped:");
            System.out.println();

            String baseItem = generateBaseItem(monster.getTreasureClass(), treasureData, generator);
            Armor armor = armorData.get(baseItem);
            String baseStats = generateBaseStats(armor, generator);
            String affixStats = "";

            if (generator.nextInt(2) == 0) {
                String[] prefix = generateAffix(prefixes, generator);
                baseItem = prefix[0] + " " + baseItem;
                affixStats += prefix[1] + "\n";
            }

            if (generator.nextInt(2) == 0) {
                String[] suffix = generateAffix(suffixes, generator);
                baseItem = baseItem + " " + suffix[0];
                affixStats += suffix[1] + "\n";
            }

            System.out.println(baseItem);
            System.out.println(baseStats);
            System.out.print(affixStats);

            if (!askFightAgain(console)) {
                break;
            }
        }

        console.close();
    }
}
