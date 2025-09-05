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
            event.reply("Não há nenhuma batalha ativa neste canal. Digite `/battle` para começar uma nova.")
                    .setEphemeral(true).queue();
            return;
        }

        // Garante que o jogador que atacou é o mesmo que iniciou a sessão (ou a quem a sessão pertence)
        if (!session.getPlayer().getId().equals(userId)) {
            event.reply("Você não está participando desta batalha!").setEphemeral(true).queue();
            return;
        }

        String battleLog = session.playerAttack();
        event.reply(battleLog).queue();
        logger.debug("Comando /attack executado por {} no canal {}", userName, channelId);

        if (!session.isBattleActive()) {
            PlayerManager.savePlayer(session.getPlayer()); // Salva o estado do jogador após a batalha
            PlayerManager.removeGameSession(channelId);
            logger.info("Batalha finalizada no canal {}", channelId);
        }
    }
}
