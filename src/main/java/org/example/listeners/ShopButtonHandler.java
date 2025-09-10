package org.example.listeners;

import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.selections.StringSelectMenu;
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
        if (parts[0].equals("shop_prev") || parts[0].equals("shop_next")) {

            int currentPage = Integer.parseInt(parts[1]);

            List<Map.Entry<String, Item>> shopItems = new ArrayList<>(ShopManager.getShopItems().entrySet());
            int itemsPerPage = 5;
            int totalPages = (int) Math.ceil((double) shopItems.size() / itemsPerPage);

            int newPage = parts[0].equals("shop_next") ? currentPage + 1 : currentPage - 1;

            String userId = event.getUser().getId();
            String userName = event.getUser().getName();
            Player player = PlayerManager.getPlayer(userId, userName);

            EmbedBuilder embed = buildShopPage(newPage, shopItems, itemsPerPage, totalPages, player);

            StringSelectMenu.Builder menuBuilder = StringSelectMenu.create("shopMenu:" + newPage)
                    .setMaxValues(1)
                    .setPlaceholder("Buy an Item from Page " + newPage);

            int start = (newPage - 1) * itemsPerPage;
            int end = Math.min(start + itemsPerPage, shopItems.size());
            for (int i = start; i < end; i++) {
                Map.Entry<String, Item> entry = shopItems.get(i);
                menuBuilder.addOption(entry.getValue().getName(), entry.getKey());
            }
            StringSelectMenu selectMenu = menuBuilder.build();

            event.editMessageEmbeds(embed.build())
                    .setComponents(
                            ActionRow.of(
                                    Button.secondary("shop_prev:" + newPage, "⬅️").withDisabled(newPage == 1),
                                    Button.secondary("shop_next:" + newPage, "➡️").withDisabled(newPage == totalPages)
                            ),
                            ActionRow.of(selectMenu)
                    ).queue();
            return;
        }

        if (parts[0].equals("shop_open")) {
            int page = Integer.parseInt(parts[1]);

            List<Map.Entry<String, Item>> shopItems = new ArrayList<>(ShopManager.getShopItems().entrySet());
            int itemsPerPage = 5;
            int totalPages = (int) Math.ceil((double) shopItems.size() / itemsPerPage);

            String userId = event.getUser().getId();
            String userName = event.getUser().getName();
            Player player = PlayerManager.getPlayer(userId, userName);

            EmbedBuilder embed = buildShopPage(page, shopItems, itemsPerPage, totalPages, player);

            StringSelectMenu.Builder menuBuilder = StringSelectMenu.create("shopMenu:" + page)
                    .setMaxValues(1)
                    .setPlaceholder("Buy an Item from Page " + page);

            int start = (page - 1) * itemsPerPage;
            int end = Math.min(start + itemsPerPage, shopItems.size());
            for (int i = start; i < end; i++) {
                Map.Entry<String, Item> entry = shopItems.get(i);
                menuBuilder.addOption(entry.getValue().getName(), entry.getKey());
            }
            StringSelectMenu selectMenu = menuBuilder.build();

            event.editMessageEmbeds(embed.build())
                    .setComponents(
                            ActionRow.of(
                                    Button.secondary("shop_prev:" + page, "⬅️").withDisabled(page == 1),
                                    Button.secondary("shop_next:" + page, "➡️").withDisabled(page == totalPages)
                            ),
                            ActionRow.of(selectMenu)
                    ).queue();
        }
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

    @Override
    public void onStringSelectInteraction(StringSelectInteractionEvent event) {
        String[] parts = event.getComponentId().split(":");
        if (!parts[0].equals("shopMenu")) return;

        String userId = event.getUser().getId();
        String userName = event.getUser().getName();
        Player player = PlayerManager.getPlayer(userId, userName);

        String selectedId = event.getValues().getFirst();
        Item item = ShopManager.getShopItems().get(selectedId);

        EmbedBuilder denied = new EmbedBuilder().setColor(Color.RED);
        if (item == null) {
            denied.setDescription("❌ This item is not in the shop!");
            event.replyEmbeds(denied.build()).addActionRow(Button.primary("shop_open:1", "🏪 Back to Shop"))
                    .setEphemeral(true).queue();
            return;
        }

        if (player.getCoins() < item.getPrice()) {
            denied.setDescription("❌ You don't have enough coins to buy **" + item.getName() + "**!");
            event.replyEmbeds(denied.build()).addActionRow(Button.primary("shop_open:1", "🏪 Back to Shop"))
                    .setEphemeral(true).queue();
            return;
        }

        player.addCoins(-item.getPrice());
        player.getInventory().add(item);

        EmbedBuilder confirmation = new EmbedBuilder()
                .setColor(Color.GREEN)
                .setDescription("✅ You bought **" + item.getName() + "** for 💰 " + item.getPrice() + " coins!");

        event.replyEmbeds(confirmation.build())
                .addActionRow(Button.primary("shop_open:1", "🏪 Back to Shop"))
                .setEphemeral(true)
                .queue();
    }
}