package org.example.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.example.rpg.Player;
import org.example.rpg.PlayerManager;
import org.example.rpg.quests.Quest;

import java.awt.*;

public class QuestCommand implements Command {

    @Override
    public String name() {
        return "quest";
    }

    @Override
    public String description() {
        return "View your current quest.";
    }

    @Override
    public void executeSlash(SlashCommandInteractionEvent event) {
        String userId = event.getUser().getId();
        String userName = event.getUser().getName();
        Player player = PlayerManager.getPlayer(userId, userName);

        EmbedBuilder embed = new EmbedBuilder()
                .setColor(Color.ORANGE);

        Quest quest = player.getActiveQuest();
        if (quest == null) {
            embed.setTitle("📜 Quests Completed!")
                    .setDescription("You have completed all quests in this zone. 🎉");
            event.replyEmbeds(embed.build()).setEphemeral(true).queue();
            return;
        }

        embed.setTitle("📜 Active Quest: " + quest.getTitle());

        embed.addField("👤 Giver", quest.getGiver(), false);
        embed.addField("📖 Context", quest.getDescription(), false);
        embed.addField("🎯 Objective", quest.getObjective(), false);
        embed.addField("📊 Progress", quest.getStatus(), false);

        embed.setFooter("Zone: " + player.getCurrentZone().name());

        event.replyEmbeds(embed.build()).setEphemeral(true).queue();
    }
}