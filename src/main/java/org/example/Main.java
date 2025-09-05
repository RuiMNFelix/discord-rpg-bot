package org.example;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.example.config.BotConfig;
import org.example.listeners.CommandListener;
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
                        Commands.slash("attack", "Attacks a monster"),
                        Commands.slash("stats", "Checks player's stats")
                ).queue(success -> logger.info("Slash commands registered successfully!"),
                        failure -> logger.error("Failed to register slash commands: ", failure));
    }
}