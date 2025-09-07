package org.example.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.example.rpg.ShopManager;

import java.awt.*;

public class ShopCommand implements Command {
    @Override
    public String name() {
        return "shop";
    }

    @Override
    public String description() {
        return "Lists all shop items";
    }

    @Override
    public void executeSlash(SlashCommandInteractionEvent event) {
        if (!event.getName().equals("shop")) return;

        EmbedBuilder embedBuilder = new EmbedBuilder()
                .setColor(Color.WHITE);
        StringBuilder sb = new StringBuilder();

        embedBuilder.setTitle("🏪 **Item Shop**");

        ShopManager.getShopItems().forEach((key, item) -> {
            sb.append(item.toString()).append("\n\n");
        });
        embedBuilder.setDescription(sb.toString());
        event.replyEmbeds(embedBuilder.build()).queue();
    }
}
