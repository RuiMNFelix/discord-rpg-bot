package org.example.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.example.rpg.Items.Item;
import org.example.rpg.Player;
import org.example.rpg.PlayerManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;

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

        EmbedBuilder embedBuilder = new EmbedBuilder()
                .setColor(Color.ORANGE);

        if (player.getInventory().isEmpty()) {
            embedBuilder.setDescription("📦 Your Inventory is Empty!");
            event.replyEmbeds(embedBuilder.build()).setEphemeral(true).queue();
            return;
        }

        embedBuilder.setTitle("📦 Inventory of " + player.getUsername());
        StringBuilder sb = new StringBuilder();

        int i = 1;
        for (Item item : player.getInventory()) {
            sb.append(i++).append(". ").append(item.toString()).append("\n");
        }
        embedBuilder.setDescription(sb.toString());

        embedBuilder.addField("⚔️ Equipped Weapon: ",
                player.getEquippedWeapon() != null ? player.getEquippedWeapon().getShortInfo() : "None", false);

        embedBuilder.addField("🛡️ Equipped Armor: ",
                player.getEquippedArmor() != null ? player.getEquippedArmor().getShortInfo() : "None", false);

        event.replyEmbeds(embedBuilder.build()).setEphemeral(true).queue();
    }
}