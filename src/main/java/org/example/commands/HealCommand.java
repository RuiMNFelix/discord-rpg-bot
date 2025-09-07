package org.example.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.example.rpg.Items.Armor;
import org.example.rpg.Items.Consumable;
import org.example.rpg.Items.Item;
import org.example.rpg.Items.Weapon;
import org.example.rpg.Player;
import org.example.rpg.PlayerManager;
import org.example.rpg.ShopManager;

import java.awt.*;
import java.util.Objects;

public class HealCommand implements Command {
    @Override
    public String name() {
        return "heal";
    }

    @Override
    public String description() {
        return "Heals using a Potion";
    }

    @Override
    public void executeSlash(SlashCommandInteractionEvent event) {
        String userId = event.getUser().getId();
        String userName = event.getUser().getName();
        Player player = PlayerManager.getPlayer(userId, userName);

        String itemName = Objects.requireNonNull(event.getOption("item")).getAsString();
        Item item = ShopManager.getItem(itemName);

        EmbedBuilder embedBuilder = new EmbedBuilder().setColor(Color.red);

        if (item == null) {
            embedBuilder.setDescription("❌ That item does not exist!");
            event.replyEmbeds(embedBuilder.build()).setEphemeral(true).queue();
            return;
        }

        if(item instanceof Weapon || item instanceof Armor){
            embedBuilder.setDescription("❌ The item needs to be a Life Potion!");
            event.replyEmbeds(embedBuilder.build()).setEphemeral(true).queue();
            return;
        }

        if(player.getHp() == player.getRealMaxHp()) {
            embedBuilder.setDescription("You already have a full life!");
            event.replyEmbeds(embedBuilder.build()).setEphemeral(true).queue();
            return;
        }

        Consumable potion = (Consumable) item;
        player.heals(potion.getRestoreHealth(), item);
        embedBuilder.setDescription("Glug glug! You chugged a **" + potion.getName() + "** and feel healthier! +" + potion.getRestoreHealth() + " HP");
        event.replyEmbeds(embedBuilder.build())
                .setEphemeral(true).queue();
    }
}
