package org.example.rpg;

import java.util.Random;

public class MonsterFactory {
    private static final Random random = new Random();

    private static final String[] MONSTER_NAMES_1_TO_5 = {
            "\uD83D\uDC3E Wild Dog", "\uD83E\uDDF8 Brown Bear", "\uD83D\uDC2F\uD83C\uDF7C Baby Tiger",
            "\uD83D\uDFE2 Slime Jr.", "\uD83E\uDD87 Bat"
    };

    private static final String[] MONSTER_NAMES_6_TO_10 = {
            "\uD83D\uDFE2 Green Slime", "\uD83E\uDDB4 Skeleton", "\uD83E\uDDCC Green Goblin",
            "\uD83D\uDC7B Ghost", "\uD83D\uDC41️ Cyclops"
    };

    private static final String[] MONSTER_NAMES_11_TO_ABOVE = {
            "Dragon", "Minotaur", "Kraken", "Griffin", "Loch Ness Monster"
    };

    public static Monster createRandomMonster(int playerLevel) {
        String name;
        int hp, attack, defense, experienceReward, coinsReward;
        if(playerLevel <= 5) {
            name = MONSTER_NAMES_1_TO_5[random.nextInt(MONSTER_NAMES_1_TO_5.length)];
            hp = playerLevel * 15 + random.nextInt(26);
            attack = playerLevel * 2 + random.nextInt(6);
            defense = random.nextInt(11);
            experienceReward = 20 + random.nextInt(81);
            coinsReward = 10 + random.nextInt(11);
        }else if(playerLevel <= 10) {
            name = MONSTER_NAMES_6_TO_10[random.nextInt(MONSTER_NAMES_6_TO_10.length)];
            hp = playerLevel * 15 + random.nextInt(26);
            attack = playerLevel * 2 + random.nextInt(6);
            defense = random.nextInt(11);
            experienceReward = 50 + random.nextInt(81);
            coinsReward = 30 + random.nextInt(11);
        }else{
            name = MONSTER_NAMES_11_TO_ABOVE[random.nextInt(MONSTER_NAMES_11_TO_ABOVE.length)];
            hp = playerLevel * 15 + random.nextInt(26);
            attack = playerLevel * 2 + random.nextInt(6);
            defense = random.nextInt(11);
            experienceReward = 100 + random.nextInt(101);
            coinsReward = 50 + random.nextInt(11);
        }
        return new Monster(name, hp, attack, defense, experienceReward, coinsReward);
    }
}
