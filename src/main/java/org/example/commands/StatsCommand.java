package org.example.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.example.rpg.Player;
import org.example.rpg.PlayerManager;

import java.awt.*;

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
        EmbedBuilder  embedBuilder = new EmbedBuilder()
                .setTitle("Your statistics:")
                .setDescription(player.toString())
                .setColor(Color.MAGENTA);
        event.replyEmbeds(embedBuilder.build()).setEphemeral(true).queue();
    }
}
