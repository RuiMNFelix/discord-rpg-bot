package org.example.rpg;

import java.util.Random;

public class Monster {
    private String name;
    private int hp;
    private int maxHp;
    private int attack;
    private int defense;
    private int experienceReward;
    private int coinReward;

    private static final Random random = new Random();

    public Monster(String name, int hp, int attack, int defense, int experienceReward, int coinReward) {
        this.name = name;
        this.hp = hp;
        this.maxHp = hp;
        this.attack = attack;
        this.defense = defense;
        this.experienceReward = experienceReward;
        this.coinReward = coinReward;;
    }

    public void takeDamage(int damage) {
        int actualDamage = Math.max(0, damage - defense);
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

    public String getName() { return name; }
    public int getHp() { return hp; }
    public int getMaxHp() { return maxHp; }
    public int getAttack() { return attack; }
    public int getDefense() { return defense; }
    public int getExperienceReward() { return experienceReward; }
    public int getCoinReward() { return coinReward; }

    @Override
    public String toString() {
        return String.format("%s (❤\uFE0F %d/%d, ⚔\uFE0F %d, \uD83D\uDEE1\uFE0F %d)", name, hp, maxHp, attack, defense);
    }
}
