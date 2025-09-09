package org.example.listeners;

import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import org.example.rpg.Items.Item;
import org.example.rpg.Player;
import org.example.rpg.PlayerManager;
import org.example.rpg.ShopManager;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ShopButtonHandler extends ListenerAdapter {
    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        String[] parts = event.getComponentId().split(":");
        if (!parts[0].equals("shop_prev") && !parts[0].equals("shop_next")) return;

        int currentPage = Integer.parseInt(parts[1]);

        List<Map.Entry<String, Item>> shopItems = new ArrayList<>(ShopManager.getShopItems().entrySet());
        int itemsPerPage = 5;
        int totalPages = (int) Math.ceil((double) shopItems.size() / itemsPerPage);

        int newPage = parts[0].equals("shop_next") ? currentPage + 1 : currentPage - 1;

        String userId = event.getUser().getId();
        String userName = event.getUser().getName();
        Player player = PlayerManager.getPlayer(userId, userName);

        EmbedBuilder embed = buildShopPage(newPage, shopItems, itemsPerPage, totalPages, player);

        event.editMessageEmbeds(embed.build())
                .setActionRow(
                        Button.secondary("shop_prev:" + newPage, "⬅️").withDisabled(newPage == 1),
                        Button.secondary("shop_next:" + newPage, "➡️").withDisabled(newPage == totalPages)
                )
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
