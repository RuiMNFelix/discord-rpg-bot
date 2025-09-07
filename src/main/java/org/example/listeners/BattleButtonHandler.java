package org.example.listeners;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.example.rpg.GameSession;
import org.example.rpg.PlayerManager;

public class BattleButtonHandler extends ListenerAdapter {
    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        String[] parts = event.getComponentId().split(":");
        String action = parts[0];
        String userId = parts[1];

        if (!event.getUser().getId().equals(userId)) {
            event.reply("❌ This is not your battle!").setEphemeral(true).queue();
            return;
        }

        long channelId = event.getChannel().getIdLong();
        GameSession session = PlayerManager.getGameSession(channelId);

        if (session == null) {
            event.reply("⚠️ No active battle in this channel.").setEphemeral(true).queue();
            return;
        }

        switch (action) {
            case "attack":
                EmbedBuilder attackResultEmbed = session.playerAttack();
                if (!session.getMonster().isAlive()) {
                    event.editMessageEmbeds(attackResultEmbed.build())
                            .setComponents()
                            .queue();
                    PlayerManager.removeGameSession(channelId);
                } else if (!session.getPlayer().isAlive()) {
                    event.editMessageEmbeds(attackResultEmbed.build())
                            .setComponents()
                            .queue();
                    PlayerManager.removeGameSession(channelId);
                } else {
                    event.editMessageEmbeds(attackResultEmbed.build()).queue();
                }
                break;

            case "run":
                EmbedBuilder playerRunAway = session.playerRunAway();
                event.editMessageEmbeds(playerRunAway.build())
                        .setComponents()
                        .queue();
                PlayerManager.removeGameSession(channelId);
                break;
        }
    }
}