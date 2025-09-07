package org.example.rpg;

import org.example.rpg.Items.Consumable;
import org.example.rpg.Items.Rarity;
import org.example.rpg.Items.Weapon;

import java.util.Arrays;
import java.util.List;

public class LootTable {
    public static List<LootDrop> getLowLevelLoot() {
        return Arrays.asList(
                new LootDrop(new Consumable("Small Life Potion", "A tiny vial of glowing liquid that restores a bit of health.", Rarity.COMMON, 20, "Restores 20 HP", 20), 0.2 ),
                new LootDrop(new Weapon("Rusty Dagger", "A weak dagger", Rarity.COMMON, 15, 2), 0.1),
                new LootDrop(null, 0.7)
        );
    }

    public static List<LootDrop> getMidLevelLoot() {
        return Arrays.asList(
                new LootDrop(new Consumable("Medium Life Potion", "A medium vial of glowing liquid that restores a fair amount of health.", Rarity.UNCOMMON, 50, "Restores 50 HP", 50), 0.25),
                new LootDrop(new Weapon("Orc Axe", "A brutal axe forged by orc blacksmiths, heavy and crude but devastating in battle.", Rarity.UNCOMMON, 40, 5), 0.05),
                new LootDrop(null, 0.7)
        );
    }

    public static List<LootDrop> getHighLevelLoot() {
        return Arrays.asList(
                new LootDrop(new Consumable("Large Life Potion", "A large vial of glowing liquid that restores a significant amount of health.", Rarity.RARE, 100, "Restores 100 HP", 100), 0.3),
                new LootDrop(new Weapon("Dragon Slayer", "A sword forged to pierce the scales of dragons.", Rarity.RARE, 200, 15), 0.05),
                new LootDrop(null, 0.65)
        );
    }
}
