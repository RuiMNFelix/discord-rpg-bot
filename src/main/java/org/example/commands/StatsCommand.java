package org.example.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.example.rpg.Player;
import org.example.rpg.PlayerManager;

public class StatsCommand implements Command {
    @Override
    public String name() { return "stats"; }
    @Override
    public String description() { return "Displays your character's stats."; }
    @Override
    public void executeSlash(SlashCommandInteractionEvent event) {
        String userId = event.getUser().getId();
        String userName = event.getUser().getName();
        Player player = PlayerManager.getPlayer(userId, userName);
        event.reply("Your statistics:\n" + player.toString()).setEphemeral(true).queue();
    }
}
