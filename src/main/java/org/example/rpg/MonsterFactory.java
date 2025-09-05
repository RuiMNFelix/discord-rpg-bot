package org.example.rpg;

import java.util.Random;

public class MonsterFactory {
    private static final Random random = new Random();

    private static final String[] MONSTER_NAMES = {
            "\uD83D\uDC14 Angry Chicken", "🧻 Toilet Paper Golem", "\uD83D\uDC0C Lazy Snail", "\uD83E\uDD54 Potato Warrior",
            "🪦 Tiny Ghost", "\uD83D\uDFE2 Slime Jr.", "\uD83D\uDC7A Minor Goblin"
    };

    public static Monster createRandomMonster() {
        String name =  MONSTER_NAMES[random.nextInt(MONSTER_NAMES.length)];

        int hp =  10 + random.nextInt(51);
        int attack = 5 + random.nextInt(21);
        int defense = random.nextInt(21);
        int experienceReward = 20 + random.nextInt(81);
        int coinsReward = 10 + random.nextInt(91);

        return new Monster(name, hp, attack, defense, experienceReward, coinsReward);
    }
}
