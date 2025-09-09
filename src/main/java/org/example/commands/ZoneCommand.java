package org.example.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.example.rpg.Player;
import org.example.rpg.PlayerManager;
import org.example.rpg.quests.Quest;

import java.awt.*;

public class ZoneCommand implements  Command {
    @Override
    public String name() {
        return "zone";
    }

    @Override
    public String description() {
        return "Shows your zone";
    }

    @Override
    public void executeSlash(SlashCommandInteractionEvent event) {
        String userId = event.getUser().getId();
        String userName = event.getUser().getName();
        Player player = PlayerManager.getPlayer(userId, userName);

        EmbedBuilder embedBuilder = new EmbedBuilder().setColor(Color.DARK_GRAY);

        embedBuilder.setTitle(player.getCurrentZone().getName());
        embedBuilder.setDescription(" - " + player.getCurrentZone().getDescription() + "\n\n**Main Quests**");
        for(Quest quest : player.getCurrentZone().getMainQuests()){
            embedBuilder.addField("📜  " + quest.getTitle(), quest.getDescription(), false);
        }

        event.replyEmbeds(embedBuilder.build()).setEphemeral(true).queue();
    }
}
