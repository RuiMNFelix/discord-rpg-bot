package org.example.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.example.rpg.Items.Item;
import org.example.rpg.Player;
import org.example.rpg.PlayerManager;
import org.example.rpg.ShopManager;

import java.awt.*;
import java.util.Objects;

public class SellCommand implements Command {
    @Override
    public String name() {
        return "sell";
    }

    @Override
    public String description() {
        return "Sell an Item";
    }

    @Override
    public void executeSlash(SlashCommandInteractionEvent event) {
        String userId = event.getUser().getId();
        String userName = event.getUser().getName();
        Player player = PlayerManager.getPlayer(userId, userName);

        String itemName = Objects.requireNonNull(event.getOption("item")).getAsString();
        Item item = null;
        for(Item entry : player.getInventory()){
            if(entry.getName().equals(itemName)){
                item = entry;
            }
        }

        EmbedBuilder embedBuilder = new EmbedBuilder().setColor(Color.WHITE);

        if(item == null) {
            embedBuilder.setDescription("❌ This item is not in your inventory!");
            event.replyEmbeds(embedBuilder.build()).setEphemeral(true).queue();
            return;
        }

        player.addCoins(item.getPrice());
        player.getInventory().remove(item);

        embedBuilder.setDescription("✅ You sold **" + item.getName() + "** for \uD83D\uDCB0 " + item.getPrice() + " coins!");

        event.replyEmbeds(embedBuilder.build()).setEphemeral(true).queue();
    }
}
