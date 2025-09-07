package org.example.rpg.Items;

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

    @Override
    public String toString() {
        return "[" + rarity + "] " + name + " - " + description + " (💰 " + price + ")";
    }
}
