package org.example.rpg.Items;

public class Consumable extends Item {
    private final String effect;
    private final int restoreHealth;

    public Consumable(String name, String description, Rarity rarity, int price, String effect,  int restoreHealth) {
        super(name, description, rarity, price);
        this.effect = effect;
        this.restoreHealth = restoreHealth;
    }

    public String getEffect() {
        return effect;
    }
    public int getRestoreHealth() { return  restoreHealth; }

    @Override
    public String getInventoryInfo() {
        return "🧪 [" + getRarity() + "] " + getName() + " - " + getDescription() +
                "\n✨ " + effect +
                " | ❤️ +" + restoreHealth + " HP";
    }

    @Override
    public String toString() {
        return "🧪 [" + getRarity() + "] " + getDescription() +
                "\n   💰 " + getPrice() +
                " | ✨ " + effect +
                " | ❤️ +" + restoreHealth + " HP";
    }
}
