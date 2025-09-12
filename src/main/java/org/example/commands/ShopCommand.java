package org.example.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.selections.StringSelectInteraction;
import net.dv8tion.jda.api.interactions.components.selections.StringSelectMenu;
import org.example.rpg.Items.Item;
import org.example.rpg.Player;
import org.example.rpg.PlayerManager;
import org.example.rpg.ShopManager;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

        String userId = event.getUser().getId();
        String userName = event.getUser().getName();
        Player player = PlayerManager.getPlayer(userId, userName);


        int itemsPerPage = 5;
        List<Map.Entry<String, Item>> shopItems = new ArrayList<>(ShopManager.getShopItems().entrySet());
        int totalPages = (int) Math.ceil((double) shopItems.size() / itemsPerPage);

        EmbedBuilder embed = buildShopPage(1, shopItems, itemsPerPage, totalPages, player);

        StringSelectMenu.Builder menuBuilder = StringSelectMenu.create("shopMenu:id")
                .setMaxValues(1)
                .setPlaceholder("Buy an Item from Page 1");

        int end = Math.min(itemsPerPage, shopItems.size());
        for (int i = 0; i < end; i++) {
            Map.Entry<String, Item> entry = shopItems.get(i);
            menuBuilder.addOption(entry.getValue().getName(), entry.getKey());
        }
        StringSelectMenu selectMenu = menuBuilder.build();

        event.replyEmbeds(embed.build())
                .addActionRow(
                        Button.secondary("shop_prev:1", "⬅️").asDisabled(),
                        Button.secondary("shop_next:1", "➡️")
                ).addActionRow(selectMenu).setEphemeral(true)
                .queue();
    }

    private EmbedBuilder buildShopPage(int page, List<Map.Entry<String, Item>> shopItems,
                                       int itemsPerPage, int totalPages, Player player) {
        EmbedBuilder eb = new EmbedBuilder()
                .setColor(Color.WHITE)
                .setTitle("🏪 **Item Shop**");

        StringBuilder sb = new StringBuilder();
        sb.append("\n").append(player.getUsername()).append(" Coins: ").append(player.getCoins()).append("\n\n");
        eb.setDescription(sb.toString());

        int start = (page - 1) * itemsPerPage;
        int end = Math.min(start + itemsPerPage, shopItems.size());

        for (int i = start; i < end; i++) {
            Map.Entry<String, Item> entry = shopItems.get(i);
            eb.addField(entry.getKey(), entry.getValue().toString(), false);
        }

        eb.setFooter("Page " + page + "/" + totalPages);
        return eb;
    }
}
