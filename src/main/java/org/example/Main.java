package org.example;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.example.listeners.BattleButtonHandler;
import org.example.config.BotConfig;
import org.example.listeners.CommandListener;
import org.example.listeners.ShopButtonHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.EnumSet;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    private static JDA jda;

    public static void main(String[] args) {
        String botToken = BotConfig.getBotToken();

        if(botToken == null || botToken.isEmpty()) {
            logger.error("Bot token is null or empty, please provide a valid token");
            return;
        }

        try {
            jda = JDABuilder.createDefault(botToken)
                    .enableIntents(EnumSet.allOf(GatewayIntent.class))
                    .addEventListeners(new CommandListener())
                    .addEventListeners(new BattleButtonHandler())
                    .addEventListeners(new ShopButtonHandler())
                    .build();
            jda.awaitReady();
            logger.info("Bot is online and ready");
            registerSlashCommands();
        }catch(Exception e) {
            logger.error("Error starting the bot: ", e);
        }
    }

    private static void registerSlashCommands() {
        if(jda == null) {
            logger.error("JDA has not been initialized");
            return;
        }

        logger.info("Registering slash commands...");
        jda.updateCommands()
                .addCommands(
                        Commands.slash("battle", "Starts a battle"),
                        Commands.slash("stats", "Checks player's stats"),
                        Commands.slash("inventory", "Opens player's inventory"),
                        Commands.slash("equip", "Equip a Weapon or Armor")
                                .addOption(OptionType.STRING, "item", "Name of the item that you want to equip", true),
                        Commands.slash("buy", "Buy an Item")
                                .addOption(OptionType.STRING, "item", "Name of the item that you want to buy", true),
                        Commands.slash("shop", "Lists all shop items"),
                        Commands.slash("heal", "Heals using a Potion")
                                .addOption(OptionType.STRING, "item", "Name of the Potion you want to use", true),
                        Commands.slash("quest", "Look at your quest")
                ).queue(success -> logger.info("Slash commands registered successfully!"),
                        failure -> logger.error("Failed to register slash commands: ", failure));
    }
}