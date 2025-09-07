package org.example.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.example.rpg.Player;
import org.example.rpg.PlayerManager;

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

        if (equipped) {
            event.reply("✅ Equipped **" + itemName + "**!").queue();
        } else {
            event.reply("❌ Couldn't equip `" + itemName + "`. \n" + "Check if you have it in your inventory and if it can be equipped.")
                    .setEphemeral(true)
                    .queue();
        }
    }
}
