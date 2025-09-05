package org.example.rpg;

import java.util.HashMap;
import java.util.Map;

public class PlayerManager {
    private static final Map<String, Player> players = new HashMap<>();
    private static final Map<Long, GameSession> activeSessions = new HashMap<>();

    public static Player getPlayer(String userId, String userName) {
        return players.computeIfAbsent(userId, id -> new Player(id, userName));
    }

    public static void savePlayer(Player player) {
        players.put(player.getId(), player);
    }

    public static GameSession getGameSession(long channelId) {
        return activeSessions.get(channelId);
    }

    public static void startGameSession(long channelId, Player player, Monster monster) {
        activeSessions.put(channelId, new GameSession(player, monster, channelId));
    }

    public static void removeGameSession(long channelId) {
        activeSessions.remove(channelId);
    }

    public static boolean hasActiveGameSession(long channelId) {
        return activeSessions.containsKey(channelId) && activeSessions.get(channelId).isBattleActive();
    }
}
