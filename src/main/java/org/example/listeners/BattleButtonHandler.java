package org.example.listeners;

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
                String attackResult = session.playerAttack();
                if (!session.getMonster().isAlive()) {
                    event.editMessage(attackResult)
                            .setComponents()
                            .queue();
                    PlayerManager.removeGameSession(channelId);
                } else {
                    event.editMessage(attackResult).queue();
                }
                break;

            case "run":
                event.editMessage("🏃 You ran away!")
                        .setComponents()
                        .queue();
                PlayerManager.removeGameSession(channelId);
                break;
        }
    }
}