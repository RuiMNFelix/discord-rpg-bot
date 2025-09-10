package org.example.listeners;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.example.rpg.GameSession;
import org.example.rpg.Items.Consumable;
import org.example.rpg.Items.Item;
import org.example.rpg.Player;
import org.example.rpg.PlayerManager;
import java.util.List;

public class BattleButtonHandler extends ListenerAdapter {
    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        String[] parts = event.getComponentId().split(":");
        if (!parts[0].equals("attack") && !parts[0].equals("run")) return;
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

    @Override
    public void onStringSelectInteraction(StringSelectInteractionEvent event){
        String[] parts = event.getComponentId().split(":");
        String action = parts[0];
        String userId = parts[1];
        if(event.getComponentId().equals("menu:id")){

            long channelId = event.getChannel().getIdLong();
            GameSession session = PlayerManager.getGameSession(channelId);
            Player player = session.getPlayer();

            List<String> selectedOption = event.getValues();

            Item item = null;
            for(Item entry : player.getInventory()){
                if(entry.getName().equals(selectedOption.getFirst())){
                    item = entry;
                }
            }
            Consumable potion = (Consumable) item;

            if(selectedOption.getFirst().equals("No Potions")){
                EmbedBuilder embedBuilder = session.duringBattle();
                embedBuilder.addField("`" + session.getPlayer().getUsername() + "` tried to heal!", "You do not have any Potion!", false);
                event.editMessageEmbeds(embedBuilder.build())
                        .queue();
                return;
            }

            if(item == null){
                EmbedBuilder embedBuilder = session.duringBattle();
                embedBuilder.addField("`" + session.getPlayer().getUsername() + "` tried to heal!", "You do not have this Potion!", false);
                event.editMessageEmbeds(embedBuilder.build())
                        .queue();
                return;
            }

            if(player.getHp() == player.getRealMaxHp()) {
                EmbedBuilder embedBuilder = session.duringBattle();
                embedBuilder.addField("`" + session.getPlayer().getUsername() + "` tried to heal!", "You already have a full life!", false);
                event.editMessageEmbeds(embedBuilder.build())
                        .queue();
                return;
            }

            player.heals(potion.getRestoreHealth(), item);

            EmbedBuilder embedBuilder = session.duringBattle();

            embedBuilder.addField("`" + session.getPlayer().getUsername()+ "` is healing!",
                        "Glug glug! You chugged a **" + potion.getName() + "** and feel healthier! +" + potion.getRestoreHealth() + " HP", false);
            event.editMessageEmbeds(embedBuilder.build())
                    .queue();
        }
    }
}