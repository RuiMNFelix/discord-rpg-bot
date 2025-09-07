package org.example.rpg;

import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;

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

    public EmbedBuilder startBattle() {
        EmbedBuilder embedBuilder = new EmbedBuilder()
                .setColor(Color.RED);
        StringBuilder sb = new StringBuilder();

        embedBuilder.setTitle("A Battle has started between **" + player.getUsername() + "** and **" + monster.getName() + "**!");
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

        embedBuilder.setDescription(sb.toString());
        return embedBuilder;
    }

    public EmbedBuilder playerAttack() {
        EmbedBuilder embedBuilder = new EmbedBuilder()
                .setColor(Color.RED);
        StringBuilder sb = new StringBuilder();

        if (!battleActive) {
            embedBuilder.setDescription("Battle is not active!");
            return embedBuilder;
        }
        if (!player.isAlive() || !monster.isAlive()) {
            embedBuilder.setDescription("Battle has ended!");
            return embedBuilder;
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
            embedBuilder.setDescription(sb.toString());
            return embedBuilder;
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
            embedBuilder.setDescription(sb.toString());
            return embedBuilder;
        }
        embedBuilder.setDescription(sb.toString());
        return embedBuilder;
    }

    public EmbedBuilder playerRunAway(){
        EmbedBuilder embedBuilder = new EmbedBuilder()
                .setColor(Color.RED);
        StringBuilder sb = new StringBuilder();
        if (!battleActive) {
            embedBuilder.setDescription("Battle is not active!");
            return embedBuilder;
        }
        sb.append("\n** Pussy! ** `").append(player.getUsername()).append("` is running away from a  `").append(monster.getName()).append("`!\n");
        embedBuilder.setDescription(sb.toString());
        return embedBuilder;
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
