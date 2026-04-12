package edu.ttap.lootgenerator;

public class Affix {
    private String name;
    private String modCode;
    private int modMin;
    private int modMax;

    public Affix(String name, String modCode, int modMin, int modMax) {
        this.name = name;
        this.modCode = modCode;
        this.modMin = modMin;
        this.modMax = modMax;
    }

    public String getName() {
        return name;
    }

    public String getModCode() {
        return modCode;
    }

    public int getModMin() {
        return modMin;
    }

    public int getModMax() {
        return modMax;
    }
}