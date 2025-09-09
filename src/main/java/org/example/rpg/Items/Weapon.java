package org.example.rpg.Items;

public class Weapon extends Item {
    private final int attack;

    public Weapon(String name, String description, Rarity rarity, int price, int attack) {
        super(name, description, rarity, price);
        this.attack = attack;
    }

    public int getAttack(){
        return attack;
    }

    public String getShortInfo() {
        return getName() + " | ⚔️ +" + getAttack();
    }

    @Override
    public String getInventoryInfo() {
        return "⚔️ [" + getRarity() + "] " + getName() + " - " + getDescription() +
                "\n     🗡️ Attack +" + getAttack();
    }

    @Override
    public String toString() {
        return "⚔️ [" + getRarity() + "] " + getDescription() +
                "\n   💰 " + getPrice() +
                " | 🗡️ Attack +" + getAttack();
    }
}
