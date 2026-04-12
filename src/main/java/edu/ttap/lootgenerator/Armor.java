package edu.ttap.lootgenerator;

public class Armor {
    private String name;
    private int minAc;
    private int maxAc;

    public Armor(String name, int minAc, int maxAc) {
        this.name = name;
        this.minAc = minAc;
        this.maxAc = maxAc;
    }

    public String getName() {
        return name;
    }

    public int getMinAc() {
        return minAc;
    }

    public int getMaxAc() {
        return maxAc;
    }
}