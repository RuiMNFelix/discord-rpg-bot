package org.example.rpg.loots;

import org.example.rpg.Items.Item;
import org.example.rpg.Items.Rarity;
import org.example.rpg.Items.Weapon;

import java.util.Arrays;
import java.util.List;

public class LootTable {
    public static List<LootDrop> getZoneOneLootDrops() {
        return Arrays.asList(
                new LootDrop(new Item("Herb", "A small, fragrant plant with faint green leaves. It’s often used by healers to brew remedies that calm the mind or restore health.", Rarity.COMMON, 5), 0.2),
                new LootDrop(new Weapon("Rusty Dagger", "A weak dagger", Rarity.COMMON, 15, 2), 0.15),
                new LootDrop(new Weapon("Iron Dagger", "A dagger made of iron", Rarity.UNCOMMON, 30, 5), 0.05),
                new LootDrop(null, 0.6)
        );
    }
}
