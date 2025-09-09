package org.example.rpg;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameWorld {
    public static final Map<String, Zone> zones = new HashMap<>();

    static {
        zones.put("Whispering Forest", new Zone("Whispering Forest",
                "The Whispering Forest was once a peaceful, if slightly eerie, woodland. Recently, a subtle corruption has taken root. A low, constant whispering, audible to some, has begun to drive the wildlife mad with aggression and fill the villagers with paranoia and a mind-fever. The forest is sick, and the player arrives right as things are about to boil over.",
                 1,
                List.of(
                        new Quest("forest_1", "Herbs for the Fevered Mind",
                                "Elara, the Village Healer", "Elara doesn't just need herbs for physical wounds. She needs them to brew a calming draught for villagers suffering from intense anxiety and what she calls mind-fever. She explains, 'It started with the whispers in the woods. Now, man and beast alike are losing their sanity.'\n\n" +
                                "Objective: Collect 3 special herbs from the forest.",
                                QuestType.GATHER, "Herb", 3),

                        new Quest("forest_2", "The Dissonant Howl",
                                "Bryn, the Village Hunter", "After delivering the herbs, Elara points you to Bryn. He reports that the wolves are no longer hunting for food—they're attacking travelers on sight, their howls are strange and off-key, and their eyes glow with a sickly purple haze. Something is enraging them.\n\n" +
                                "Objective: Defeat 10 Dire Wolves to make the paths safer. This serves as the combat tutorial. Upon defeating the last wolf, the player finds a clue—a tattered piece of cloth or a dropped tool belonging to a villager who recently went missing near an old goblin cave.",
                                QuestType.KILL, "Dire Wolf", 10)
                )
                )
        );
    }
}
