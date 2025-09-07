package org.example.rpg;

import org.example.rpg.Items.Item;

public class LootDrop {
    private final Item item;
    private final double chance;

    public LootDrop(Item item, double chance) {
        this.item = item;
        this.chance = chance;
    }

    public Item getItem() {
        return item;
    }
    public double getChance() {
        return chance;
    }
}
