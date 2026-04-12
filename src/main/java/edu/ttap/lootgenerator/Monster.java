package edu.ttap.lootgenerator;

public class Monster {
    private String name;
    private String type;
    private int level;
    private String treasureClass;

    public Monster(String name, String type, int level, String treasureClass) {
        this.name = name;
        this.type = type;
        this.level = level;
        this.treasureClass = treasureClass;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getLevel() {
        return level;
    }

    public String getTreasureClass() {
        return treasureClass;
    }
}