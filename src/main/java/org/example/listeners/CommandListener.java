package org.example.listeners;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.example.commands.*;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;


public class CommandListener extends ListenerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(CommandListener.class);
    private final Map<String, Command> commands = new HashMap<>();

    public CommandListener() {
        commands.put("battle", new BattleCommand());
        commands.put("attack", new AttackCommand());
        commands.put("stats", new StatsCommand());
        commands.put("inventory", new InventoryCommand());
        commands.put("equip", new EquipCommand());
        commands.put("buy", new BuyCommand());
        commands.put("shop", new ShopCommand());
        commands.put("heal", new HealCommand());
        commands.put("quest", new QuestCommand());
        logger.info("Registered {} commands", commands.size());
    }

    @Override
    public void onReady(@NotNull ReadyEvent event) {
        logger.info("JDA is ready! Logged in as {}#{}",
                event.getJDA().getSelfUser().getName(),
                event.getJDA().getSelfUser().getDiscriminator());
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event){
        String commandName = event.getName();
        Command command = commands.get(commandName);

        if (command != null) {
            logger.debug("Executing Slash command {} at {}", commandName, event.getUser().getId());
            command.executeSlash(event);
        }else{
            logger.warn("Unknown Slash command: {} from user: {}", commandName, event.getUser().getId());
            event.reply("Unknown Slash command!").setEphemeral(true).queue();
        }
    }
}
