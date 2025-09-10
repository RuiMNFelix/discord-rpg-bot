package org.example.rpg.RpgWorld;

import org.example.rpg.quests.Quest;

import java.util.List;

public record Zone(String name, String description, int minLevel, List<Quest> mainQuests) {

    public static Zone getStartZone() {
        return GameWorld.zones.get("Whispering Forest");
    }
}