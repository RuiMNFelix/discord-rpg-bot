package org.example.rpg;

import org.example.rpg.Items.Armor;
import org.example.rpg.Items.Item;
import org.example.rpg.Items.Weapon;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Player {
    private final String id;
    private final String username;
    private int level;
    private int coins;
    private int experience;
    private int hp;
    private int maxHp;
    private int attack;
    private int defense;
    private final List<Item> inventory;
    private Weapon equippedWeapon;
    private Armor equippedArmor;
    private Zone currentZone;
    private int questIndex;
    private Quest activeQuest;

    private static final Random random = new Random();
    Zone startZone = Zone.getStartZone();

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
        this.inventory = new ArrayList<>();
        this.currentZone = startZone;
        this.questIndex = 0;
        this.activeQuest = startZone.getMainQuests().getFirst();
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
        return level * 100 - 50 * (level-1);
    }

    public void levelUp() {
        level++;
        maxHp += 5;
        hp = getRealMaxHp();
        attack += 3;
        defense += 1;
        experience -= ((level - 1) * 100);
    }

    public void takeDamage(int damage) {
        int actualDamage;
        if(equippedArmor != null){
            actualDamage = damage * damage / (damage + defense + equippedArmor.getDefense());
        }else {
            actualDamage = damage * damage / (damage + defense);
        }
        this.hp -= actualDamage;
        if(this.hp < 0){
            this.hp = 0;
        }
    }

    public int calculateDamage(){
        if(equippedWeapon == null){
            return attack + random.nextInt(3);
        }
        return attack  + random.nextInt(3) + equippedWeapon.getAttack();
    }

    public int getRealAttack(){
        if(equippedWeapon == null){
            return attack;
        }
        return attack + equippedWeapon.getAttack();
    }

    public int getRealDefense(){
        if(equippedArmor == null){
            return defense;
        }
        return defense + equippedArmor.getDefense();
    }

    public int getRealMaxHp(){
        if(equippedArmor == null){
            return maxHp;
        }
        return  maxHp + equippedArmor.getExtraHp();
    }

    public boolean isAlive(){
        return hp > 0;
    }

    public void heals(int value, Item item){
        if(hp+value > maxHp){
            hp = maxHp;
        }
        hp += value;
        getInventory().remove(item);
    }

    public boolean equipItem(String itemName){
        for(Item item : inventory){
            if(item.getName().equals(itemName)){
                if(item instanceof Armor){
                    this.equippedArmor = (Armor) item;
                    return true;
                }else if(item instanceof Weapon){
                    this.equippedWeapon = (Weapon) item;
                    return true;
                }else{
                    return false;
                }
            }
        }
        return false;
    }

    public String getHpBar(){
        int totalBlocks = 10;
        int filledBlocks = (int) Math.round(((double) hp / getRealMaxHp()) * totalBlocks);
        int emptyBlocks = totalBlocks - filledBlocks;

        String filled = "■".repeat(filledBlocks);
        String empty = "□".repeat(emptyBlocks);

        return "❤️ [" + filled + empty + "] " + hp + "/" + getRealMaxHp();
    }

    public Quest getActiveQuest() { return activeQuest; }

    public void completeQuest() {
        if (activeQuest.isCompleted()) {
            questIndex++;
            if (questIndex < currentZone.getMainQuests().size()) {
                activeQuest = currentZone.getMainQuests().get(questIndex);
            } else {
                activeQuest = null;
            }
        }
    }

    public boolean hasActiveQuest() {
        return activeQuest != null && !activeQuest.isCompleted();
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public int getLevel() {
        return level;
    }

    public int getCoins() {
        return coins;
    }

    public int getExperience() {
        return experience;
    }

    public int getHp() {
        return hp;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public int getAttack() {
        return attack;
    }

    public int getDefense() {
        return defense;
    }

    public List<Item> getInventory() {
        return inventory;
    }

    public Weapon getEquippedWeapon() {
        return equippedWeapon;
    }

    public Armor getEquippedArmor() {
        return equippedArmor;
    }

    public Zone getCurrentZone() {
        return currentZone;
    }

    public int getQuestIndex() {
        return questIndex;
    }

    public Zone getStartZone() {
        return startZone;
    }

    @Override
    public String toString() {
        return String.format("%s (Level: %d, %s, ⚔️ %d, \uD83D\uDEE1️ %d, XP: %d/%d, \uD83D\uDCB0 %d)",
                username, level, getHpBar(), getRealAttack(), getRealDefense(), experience, getExpToNextLevel(), coins);
    }
}