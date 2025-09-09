package org.example.rpg;

import java.util.List;

public class Zone {
    private final String name;
    private final String description;
    private final int minLevel;
    private final List<Quest> mainQuests;

    public Zone(String name, String description, int minLevel, List<Quest> mainQuests) {
        this.name = name;
        this.description = description;
        this.minLevel = minLevel;
        this.mainQuests = mainQuests;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getMinLevel() {
        return minLevel;
    }

    public List<Quest> getMainQuests() {
        return mainQuests;
    }

    public static Zone getStartZone() {
        return GameWorld.zones.get("Whispering Forest");
    }
}
