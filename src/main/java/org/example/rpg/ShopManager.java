package org.example.rpg;

import org.example.rpg.Items.*;

import java.util.LinkedHashMap;
import java.util.Map;

public class ShopManager {
    private static final Map<String, Item> shopItems = new LinkedHashMap<>();

    static {
        shopItems.put("Wooden Sword", new Weapon("Wooden Sword",
                "A sturdy wooden sword, light and safe for training or beginners.", Rarity.COMMON,
                30, 3));
        shopItems.put("Stone Sword", new Weapon("Stone Sword",
                "A crude sword carved from stone, heavy but strong.", Rarity.COMMON,
                50, 5));
        shopItems.put("Iron Sword", new Weapon("Iron Sword",
                "A solid iron blade, reliable and sharp.", Rarity.UNCOMMON,
                80, 8));


        shopItems.put("Leather Armor", new Armor("Leather Armor",
                "Light armor made from toughened leather, offering basic protection.",
                Rarity.COMMON,
                30, 3, 10));

        shopItems.put("Chainmail Armor", new Armor("Chainmail Armor",
                "Interlocking metal rings that balance defense and mobility.",
                Rarity.COMMON,
                50, 5, 15));

        shopItems.put("Iron Armor", new Armor("Iron Armor",
                "Heavy iron plates that provide strong protection but reduce agility.",
                Rarity.UNCOMMON,
                80, 8, 20));


        shopItems.put("Small Life Potion", new Consumable("Small Life Potion",
                "A tiny vial of glowing liquid that restores a bit of health.",
                Rarity.COMMON, 20, "Restores 20 hp", 20));
    }

    public static Map<String, Item> getShopItems() {
        return shopItems;
    }
    public static Item getItem(String key) {
        return shopItems.get(key);
    }
}
