package org.example.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.example.rpg.GameSession;
import org.example.rpg.PlayerManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AttackCommand implements Command {
    private static final Logger logger = LoggerFactory.getLogger(AttackCommand.class);

    @Override
    public String name() {
        return "attack";
    }

    @Override
    public String description() {
        return "Attacks a Monster.";
    }

    @Override
    public void executeSlash(SlashCommandInteractionEvent event) {
        long channelId = event.getChannel().getIdLong();
        String userId = event.getUser().getId();
        String userName = event.getUser().getName();

        GameSession session = PlayerManager.getGameSession(channelId);

        if (session == null || !session.isBattleActive()) {
            event.reply("There is no active battle on this channel. Type `/battle` to start a new one.")
                    .setEphemeral(true).queue();
            return;
        }

        if (!session.getPlayer().getId().equals(userId)) {
            event.reply("You are not participating on this battle!").setEphemeral(true).queue();
            return;
        }

        String battleLog = session.playerAttack();
        event.reply(battleLog).queue();
        logger.debug("Command /attack executed by {} on channel {}", userName, channelId);

        if (!session.isBattleActive()) {
            PlayerManager.savePlayer(session.getPlayer());
            PlayerManager.removeGameSession(channelId);
            logger.info("Battle ended on channel {}", channelId);
        }
    }
}
