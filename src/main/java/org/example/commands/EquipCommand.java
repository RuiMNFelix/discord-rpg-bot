package org.example.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.example.rpg.Player;
import org.example.rpg.PlayerManager;

import java.awt.*;
import java.util.Objects;

public class EquipCommand implements Command {

    @Override
    public String name() {
        return "equip";
    }

    @Override
    public String description() {
        return "Equip a Weapon or Armor.";
    }

    @Override
    public void executeSlash(SlashCommandInteractionEvent event) {
        String userId = event.getUser().getId();
        String userName = event.getUser().getName();
        Player player = PlayerManager.getPlayer(userId, userName);

        String itemName = Objects.requireNonNull(event.getOption("item")).getAsString();

        boolean equipped = player.equipItem(itemName);

        EmbedBuilder embedBuilder = new EmbedBuilder().setColor(Color.ORANGE);

        if (equipped) {
            embedBuilder.setDescription("✅ Equipped **" + itemName + "**!");
            event.replyEmbeds(embedBuilder.build()).setEphemeral(true).queue();
        } else {
            embedBuilder.setDescription("❌ Couldn't equip `" + itemName + "`. \n" + "Check if you have it in your inventory and if it can be equipped.");
            event.replyEmbeds(embedBuilder.build())
                    .setEphemeral(true)
                    .queue();
        }
    }
}
