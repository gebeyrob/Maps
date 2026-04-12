package edu.ttap.lootgenerator;

import java.util.List;
import java.util.Random;

public class TreasureClass {
    private String name;
    private List<String> drops;

    public TreasureClass(String name, List<String> drops) {
        this.name = name;
        this.drops = drops;
    }

    public String getName() {
        return name;
    }

    public String getRandomDrop(Random r) {
        return drops.get(r.nextInt(3));
    }

   
}