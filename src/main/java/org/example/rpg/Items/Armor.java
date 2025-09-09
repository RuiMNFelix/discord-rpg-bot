package org.example.rpg.Items;

public class Armor extends Item {
    private final int defense;
    private final int extraHp;


    public Armor(String name, String description, Rarity rarity, int price, int defense, int extraHp) {
        super(name, description, rarity, price);
        this.defense = defense;
        this.extraHp = extraHp;
    }

    public int getDefense() {
        return defense;
    }
    public int getExtraHp() {
        return extraHp;
    }

    public String getShortInfo() {
        return getName() + " | 🛡️ +" + getDefense() + " | ❤️ +" + getExtraHp() + " Max HP";
    }

    @Override
    public String getInventoryInfo() {
        return "🛡️ [" + getRarity() + "] " + getName() + " - " + getDescription() +
                "\n     🛡️ Defense +" + getDefense() +
                " | ❤️ +" + getExtraHp() + " Max HP";
    }

    @Override
    public String toString() {
        return "🛡️ [" + getRarity() + "] " + getDescription() +
                "\n   💰 " + getPrice() +
                " | 🛡️ Defense +" + getDefense() +
                " | ❤️ +" + getExtraHp() + " Max HP";
    }
}
