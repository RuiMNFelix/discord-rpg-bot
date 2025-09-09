package org.example.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.example.rpg.Items.Item;
import org.example.rpg.Player;
import org.example.rpg.PlayerManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class InventoryCommand implements Command {
    private static final Logger logger = LoggerFactory.getLogger(InventoryCommand.class);

    @Override
    public String name() {
        return "inventory";
    }

    @Override
    public String description() {
        return "Opens player's inventory.";
    }

    @Override
    public void executeSlash(SlashCommandInteractionEvent event) {
        String userId = event.getUser().getId();
        String userName = event.getUser().getName();
        Player player = PlayerManager.getPlayer(userId, userName);

        Map<Item, Integer> inventory = new HashMap<>();

        EmbedBuilder embedBuilder = new EmbedBuilder()
                .setColor(Color.ORANGE);

        if (player.getInventory().isEmpty()) {
            embedBuilder.setDescription("📦 Your Inventory is Empty!");
            event.replyEmbeds(embedBuilder.build()).setEphemeral(true).queue();
            return;
        }

        embedBuilder.setTitle("📦 Inventory of " + player.getUsername());
        StringBuilder sb = new StringBuilder();

        for (Item item : player.getInventory()) {
            inventory.merge(item, 1, Integer::sum);
        }

        int i = 1;
        for(Map.Entry<Item, Integer> entry : inventory.entrySet()) {
            Item item = entry.getKey();
            int value = entry.getValue();
            sb.append(i++).append(". ").append(value).append("x ")
                            .append(entry.getKey().getInventoryInfo()).append("\n");
        }

        embedBuilder.setDescription(sb.toString());

        embedBuilder.addField("⚔️ Equipped Weapon: ",
                player.getEquippedWeapon() != null ? player.getEquippedWeapon().getShortInfo() : "None", true);

        embedBuilder.addField("🛡️ Equipped Armor: ",
                player.getEquippedArmor() != null ? player.getEquippedArmor().getShortInfo() : "None", true);

        event.replyEmbeds(embedBuilder.build()).setEphemeral(true).queue();
    }
}