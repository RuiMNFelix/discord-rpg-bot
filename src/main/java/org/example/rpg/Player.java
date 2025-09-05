package org.example.rpg;

import java.util.Random;

public class Player {
    private String id;
    private String username;
    private int level;
    private int coins;
    private int experience;
    private int hp;
    private int maxHp;
    private int attack;
    private int defense;

    private static final Random random = new Random();

    public Player(String id, String username) {
        this.id = id;
        this.username = username;
        this.level = 1;
        this.coins = 0;
        this.experience = 0;
        this.maxHp = 100;
        this.hp = this.maxHp;
        this.attack = 10;
        this.defense = 5;
    }

    public void addCoins(int amount) {
        coins += amount;
    }

    public void addExperience(int amount) {
        experience += amount;
        if(experience >= getExpToNextLevel()){
            levelUp();
        }
    }

    private int  getExpToNextLevel() {
        return level * 100;
    }

    public void levelUp() {
        level++;
        maxHp += 5;
        hp = maxHp;
        attack += 3;
        defense += 1;
        experience -= ((level - 1) * 100);
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

    public String getId() { return id; }
    public String getUsername() { return username; }
    public int getLevel() { return level; }
    public int getCoins() { return coins; }
    public int getExperience() { return experience; }
    public int getHp() { return hp; }
    public int getMaxHp() { return maxHp; }
    public int getAttack() { return attack; }
    public int getDefense() { return defense; }

    @Override
    public String toString() {
        return String.format("%s (Level: %d, ❤\uFE0F %d/%d, ⚔\uFE0F %d, \uD83D\uDEE1\uFE0F %d, XP: %d/%d, \uD83D\uDCB0 %d)",
                username, level, hp, maxHp, attack, defense, experience, getExpToNextLevel(), coins);
    }
}
