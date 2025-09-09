package org.example.rpg.Items;

import java.util.Objects;

public class Item {
    private final String name;
    private final String description;
    private final Rarity rarity;
    private final int price;

    public Item(String name, String description, Rarity rarity, int price){
        this.name = name;
        this.description = description;
        this.rarity = rarity;
        this.price = price;
    }

    public String getName() { return name; }
    public String getDescription() { return description; }
    public Rarity getRarity() { return rarity; }
    public int getPrice() { return price; }

    public String getInventoryInfo() {
        return "[" + rarity + "] " + name + " - " + description;
    }

    @Override
    public String toString() {
        return "[" + rarity + "] " + name + " - " + description + " (💰 " + price + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item item)) return false;
        return name.equals(item.name) &&
                rarity.equals(item.rarity) &&
                description.equals(item.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, rarity, description);
    }
}
