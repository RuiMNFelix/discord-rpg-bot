package org.example.rpg;

import org.example.rpg.loots.LootDrop;
import org.example.rpg.loots.LootTable;

import java.util.List;
import java.util.Random;

public class MonsterFactory {
    private static final Random random = new Random();

    private static final String[] MONSTER_NAMES_ZONE_1 = {
            "\uD83D\uDC3A Dire Wolf", "\uD83D\uDFE2 Slime", "\uD83C\uDF32 Treant",
            "\uD83E\uDDCC Goblin", "\uD83D\uDC17 Wild Boar"
    };


    public static Monster createRandomMonster(Zone zone, int playerLevel) {
        String name;
        int hp, attack, defense, experienceReward, coinsReward;
        List<LootDrop> drops;
        //if(Objects.equals(zone.getName(), "Whispering Forest")){
            name = MONSTER_NAMES_ZONE_1[random.nextInt(MONSTER_NAMES_ZONE_1.length)];
            hp = playerLevel * 15 + random.nextInt(26);
            attack = playerLevel * 2 + random.nextInt(6);
            defense = random.nextInt(11);
            experienceReward = 20 + random.nextInt(31);
            coinsReward = 10 + random.nextInt(11);
            drops = LootTable.getZoneOneLootDrops();
        //}

        return new Monster(name, hp, attack, defense, experienceReward, coinsReward, drops);
    }
}
