package org.example.rpg;

import org.example.rpg.quests.Quest;
import org.example.rpg.quests.QuestType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameWorld {
    public static final Map<String, Zone> zones = new HashMap<>();

    static {
        zones.put("Whispering Forest", new Zone("\uD83C\uDF32 Whispering Forest",
                "The Whispering Forest was once a peaceful, if slightly eerie, woodland. Recently, a subtle corruption has taken root. A low, constant whispering, audible to some, has begun to drive the wildlife mad with aggression and fill the villagers with paranoia and a mind-fever. The forest is sick, and the player arrives right as things are about to boil over.",
                 1,
                List.of(
                        new Quest("forest_1", "Herbs for the Fevered Mind",
                                "Elara, the Village Healer", "Elara doesn't just need herbs for physical wounds. She needs them to brew a calming draught for villagers suffering from intense anxiety and what she calls mind-fever. She explains, 'It started with the whispers in the woods. Now, man and beast alike are losing their sanity.'\n\n",
                                QuestType.GATHER, "Collect 3 special herbs from the forest. Monsters may drop some.","Herb", 3, 20),

                        new Quest("forest_2", "The Dissonant Howl",
                                "Bryn, the Village Hunter", "After delivering the herbs, Elara points you to Bryn. He reports that the wolves are no longer hunting for food—they're attacking travelers on sight, their howls are strange and off-key, and their eyes glow with a sickly purple haze. Something is enraging them.\n\n",
                                QuestType.KILL, "Defeat 5 Dire Wolves to make the paths safer.","\uD83D\uDC3A Dire Wolf", 5, 30)
                )
                )
        );
    }
}
