package org.example.rpg.quests;

public class Quest {
    private final String id;
    private final String title;
    private final String giver;
    private final String description;
    private final QuestType type;
    private final String objective;
    private final String target;
    private final int requiredAmount;
    private int progress;
    private boolean completed;
    private final int coinsReward;

    public Quest(String id, String title, String giver, String description, QuestType type, String objective, String target, int requiredAmount, int coinsReward) {
        this.id = id;
        this.title = title;
        this.giver = giver;
        this.description = description;
        this.type = type;
        this.objective = objective;
        this.target = target;
        this.requiredAmount = requiredAmount;
        this.progress = 0;
        this.completed = false;
        this.coinsReward = coinsReward;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getGiver() {
        return giver;
    }

    public String getDescription() {
        return description;
    }

    public QuestType getType() {
        return type;
    }

    public String getObjective() { return objective; }

    public String getTarget() {
        return target;
    }

    public int getRequiredAmount() {
        return requiredAmount;
    }

    public int getProgress() {
        return progress;
    }

    public boolean isCompleted() {
        return completed;
    }

    public int getCoinsReward() {
        return coinsReward;
    }

    public void addProgress(String targetName) {
        if (completed) return;
        if (target.equalsIgnoreCase(targetName)) {
            progress++;
            if (progress >= requiredAmount) {
                completed = true;
            }
        }
    }

    public String getStatus() {
        int totalBlocks = 10;
        int filledBlocks = (int) Math.round(((double) progress / getRequiredAmount()) * totalBlocks);
        int emptyBlocks = totalBlocks - filledBlocks;

        String filled = "■".repeat(filledBlocks);
        String empty = "□".repeat(emptyBlocks);

        return "[" + filled + empty + "] " + progress + "/" + getRequiredAmount();
    }

    @Override
    public String toString() {
        return "**" + title + "**\n"
                + "👤 *Giver*: " + getGiver() + "\n"
                + "📖 *Context*: " + description + "\n"
                + "🎯 *Objective*: " + objective + "\n"
                + "📊 *Progress*: " + getStatus() + "\n";
    }
}
