package org.example.rpg;

public class GameSession {
    private Player player;
    private Monster monster;
    private long channelId;
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
                .append("`: ❤\uFE0F ").append(player.getHp()).append("/").append(player.getMaxHp())
                .append(" | ⚔\uFE0F ").append(player.getAttack())
                .append(" | \uD83D\uDEE1\uFE0F ").append(player.getDefense()).append("\n");
        sb.append("`").append(monster.getName())
                .append("`: ❤\uFE0F ").append(monster.getHp()).append("/").append(monster.getMaxHp())
                .append(" | ⚔\uFE0F ").append(monster.getAttack())
                .append(" | \uD83D\uDEE1\uFE0F ").append(monster.getDefense()).append("\n");;
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
        sb.append("`").append(monster.getName()).append("`: ❤\uFE0F ").append(monster.getHp()).append("/").append(monster.getMaxHp()).append("\n");

        if (!monster.isAlive()) {
            sb.append("\n** Victory! ** The `").append(monster.getName()).append("` has been defeated by `").append(player.getUsername()).append("`!\n");
            player.addExperience(monster.getExperienceReward());
            player.addCoins(monster.getCoinReward());
            sb.append("You won **").append(monster.getExperienceReward()).append("** of XP and **").append(monster.getCoinReward()).append("** \uD83D\uDCB0.\n");
            if (player.getExperience() == 0 && player.getLevel() > 1) {
                sb.append("`").append(player.getUsername()).append("` leveled up **").append(player.getLevel()).append("**!\n");
            }
            battleActive = false;
            return sb.toString();
        }

        sb.append("\n");
        int monsterDamage = monster.calculateDamage();
        int actualMonsterDamage = monsterDamage * monsterDamage / (monsterDamage + player.getDefense());
        player.takeDamage(monsterDamage);
        sb.append("`").append(monster.getName()).append("` attacks `").append(player.getUsername()).append("` causing **").append(actualMonsterDamage).append("** of damage!\n");
        sb.append("`").append(player.getUsername()).append("`: ❤\uFE0F ").append(player.getHp()).append("/").append(player.getMaxHp()).append("\n");

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
        sb.append("\n** Pussy! ** `").append(player.getUsername()).append("` is running away from the  `").append(monster.getName()).append("`!\n");
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
