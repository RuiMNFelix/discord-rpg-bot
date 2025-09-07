package org.example.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import org.example.rpg.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BattleCommand implements Command{
    private static final Logger logger = LoggerFactory.getLogger(BattleCommand.class);

    @Override
    public String name() {
        return "battle";
    }

    @Override
    public String description() {
        return "Starts a battle against a random monster.";
    }

    @Override
    public void executeSlash(SlashCommandInteractionEvent event) {
        long channelId = event.getChannel().getIdLong();
        String userId = event.getUser().getId();
        String userName = event.getUser().getName();

        if (PlayerManager.hasActiveGameSession(channelId)) {
            event.reply("There is already a battle in progress in this channel! Type `/attack` to continue.")
                    .setEphemeral(true).queue();
            return;
        }

        Player player = PlayerManager.getPlayer(userId, userName);
        if (player.getHp() == 0){
            event.reply("you don't have enough ❤️ for a battle!").queue();
            return;
        }
        Monster monster = MonsterFactory.createRandomMonster(player.getLevel());
        PlayerManager.startGameSession(channelId, player, monster);
        GameSession session = PlayerManager.getGameSession(channelId);

        EmbedBuilder battleStartMessage = session.startBattle();
        event.replyEmbeds(battleStartMessage.build())
                .addActionRow(
                        Button.primary("attack:" + userId, "⚔️ Attack"),
                        Button.danger("run:" + userId, "🏃 Run")
                )
                .queue();
    }
}