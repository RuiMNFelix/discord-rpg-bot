package org.example.rpg;

public class GameSession {
    private final Player player;
    private final Monster monster;
    private final long channelId;
    private boolean battleActive;

    public GameSession(Player player, Monster monster, long channelId) {
        this.player = player;
        this.monster = monster;
        this.channelId = channelId;
        this.battleActive = true;
    }

    public String startBattle() {
        StringBuilder sb = new StringBuilder();
        sb.append("A Battle has started between **").append(player.getUsername()).append("** and **").append(monster.getName()).append("**!\n");
        sb.append("----------------------------------\n");
        sb.append("`").append(player.getUsername())
                .append("`: ❤️ ").append(player.getHp()).append("/").append(player.getRealMaxHp())
                .append(" | ⚔️ ").append(player.getRealAttack())
                .append(" | \uD83D\uDEE1️ ").append(player.getRealDefense()).append("\n");
        sb.append("`").append(monster.getName())
                .append("`: ❤️ ").append(monster.getHp()).append("/").append(monster.getMaxHp())
                .append(" | ⚔️ ").append(monster.getAttack())
                .append(" | \uD83D\uDEE1️ ").append(monster.getDefense()).append("\n");;
        sb.append("----------------------------------\n");
        return sb.toString();
    }

    public String playerAttack() {
        StringBuilder sb = new StringBuilder();

        if (!battleActive) {
            return "Battle is not active.";
        }
        if (!player.isAlive() || !monster.isAlive()) {
            return "Battle has ended!";
        }

        int playerDamage = player.calculateDamage();
        int actualPlayerDamage = playerDamage * playerDamage / (playerDamage + monster.getDefense());
        monster.takeDamage(playerDamage);
        sb.append("`").append(player.getUsername()).append("` attacks `").append(monster.getName()).append("` causing **").append(actualPlayerDamage).append("** of damage!\n");
        sb.append("`").append(monster.getName()).append("`: ❤️ ").append(monster.getHp()).append("/").append(monster.getMaxHp()).append("\n");

        if (!monster.isAlive()) {
            sb.append("\n** Victory! ** The `").append(monster.getName()).append("` has been defeated by `").append(player.getUsername()).append("`!\n");
            int levelBeforeBattle = player.getLevel();
            player.addExperience(monster.getExperienceReward());
            player.addCoins(monster.getCoinReward());
            sb.append("Won **").append(monster.getExperienceReward()).append("** of XP and **").append(monster.getCoinReward()).append("** \uD83D\uDCB0.\n");
            if (levelBeforeBattle < player.getLevel()) {
                sb.append("`").append(player.getUsername()).append("` leveled up **").append(player.getLevel()).append("**!\n");
            }
            battleActive = false;
            return sb.toString();
        }

        sb.append("\n");
        int monsterDamage = monster.calculateDamage();
        int actualMonsterDamage = monsterDamage * monsterDamage / (monsterDamage + player.getRealDefense());
        player.takeDamage(monsterDamage);
        sb.append("`").append(monster.getName()).append("` attacks `").append(player.getUsername()).append("` causing **").append(actualMonsterDamage).append("** of damage!\n");
        sb.append("`").append(player.getUsername()).append("`: ❤️ ").append(player.getHp()).append("/").append(player.getRealMaxHp()).append("\n");

        if (!player.isAlive()) {
            sb.append("\n** Defeated! ** `").append(player.getUsername()).append("` has been defeated by `").append(monster.getName()).append("`!\n");
            battleActive = false;
            return sb.toString();
        }

        return sb.toString();
    }

    public String playerRunAway(){
        StringBuilder sb = new StringBuilder();
        if (!battleActive) {
            return "Battle is not active.";
        }
        sb.append("\n** Pussy! ** `").append(player.getUsername()).append("` is running away from a  `").append(monster.getName()).append("`!\n");
        return sb.toString();
    }

    public boolean isBattleActive() {
        return battleActive;
    }

    public Player getPlayer() {
        return player;
    }

    public Monster getMonster() {
        return monster;
    }

    public long getChannelId() {
        return channelId;
    }
}
