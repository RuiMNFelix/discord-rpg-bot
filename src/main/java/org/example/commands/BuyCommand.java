package org.example.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.example.rpg.Items.Item;
import org.example.rpg.Player;
import org.example.rpg.PlayerManager;
import org.example.rpg.ShopManager;

import java.util.Objects;

public class BuyCommand implements Command {

    @Override
    public String name() {
        return "buy";
    }

    @Override
    public String description() {
        return "Buy an Item";
    }

    @Override
    public void executeSlash(SlashCommandInteractionEvent event) {
        String userId = event.getUser().getId();
        String userName = event.getUser().getName();
        Player player = PlayerManager.getPlayer(userId, userName);

        String itemName = Objects.requireNonNull(event.getOption("item")).getAsString();
        Item item = ShopManager.getItem(itemName);

        if (item == null) {
            event.reply("❌ This item is not in the shop! Use `/shop` to see the list.").setEphemeral(true).queue();
            return;
        }
        if(player.getCoins() < item.getPrice()) {
            event.reply("❌ You don't have enough coins to buy " + itemName + "!").setEphemeral(true).queue();
            return;
        }

        player.addCoins(-item.getPrice());
        player.getInventory().add(item);

        event.reply("✅ You bought **" + item.getName() + "** for \uD83D\uDCB0 " + item.getPrice() + " coins!").setEphemeral(true).queue();
    }
}
