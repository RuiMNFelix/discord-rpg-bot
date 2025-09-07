package org.example.rpg;

import java.util.Random;

public class Monster {
    private final String name;
    private int hp;
    private final int maxHp;
    private final int attack;
    private final int defense;
    private final int experienceReward;
    private final int coinReward;

    private static final Random random = new Random();

    public Monster(String name, int hp, int attack, int defense, int experienceReward, int coinReward) {
        this.name = name;
        this.hp = hp;
        this.maxHp = hp;
        this.attack = attack;
        this.defense = defense;
        this.experienceReward = experienceReward;
        this.coinReward = coinReward;
    }

    public void takeDamage(int damage) {
        int actualDamage = damage * damage / (damage+defense);
        this.hp -= actualDamage;
        if(this.hp < 0){
            this.hp = 0;
        }
    }

    public int calculateDamage(){
        return attack + random.nextInt(3);
    }

    public boolean isAlive(){
        return hp > 0;
    }

    public String getHpBar(){
        int totalBlocks = 10;
        int filledBlocks = (int) Math.round(((double) hp / maxHp) * totalBlocks);
        int emptyBlocks = totalBlocks - filledBlocks;

        String filled = "■".repeat(filledBlocks);
        String empty = "□".repeat(emptyBlocks);

        return "❤️ [" + filled + empty + "] " + hp + "/" + maxHp;
    }

    public String getName() { return name; }
    public int getHp() { return hp; }
    public int getMaxHp() { return maxHp; }
    public int getAttack() { return attack; }
    public int getDefense() { return defense; }
    public int getExperienceReward() { return experienceReward; }
    public int getCoinReward() { return coinReward; }

    @Override
    public String toString() {
        return String.format("%s (❤️ %d/%d, ⚔️ %d, \uD83D\uDEE1️ %d)", name, hp, maxHp, attack, defense);
    }
}
