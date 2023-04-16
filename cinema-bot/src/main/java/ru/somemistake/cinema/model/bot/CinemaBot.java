package ru.somemistake.cinema.model.bot;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.somemistake.cinema.config.BotConfig;
import ru.somemistake.cinema.integration.BotIntegration;
import ru.somemistake.cinema.model.exception.CinemaBotException;

import javax.annotation.PostConstruct;
import java.util.List;

import static ru.somemistake.cinema.model.command.BotCommand.*;

@Component
public class CinemaBot extends TelegramLongPollingBot {

    private final String username;
    private final BotIntegration integration;

    public CinemaBot(BotConfig config, BotIntegration integration) {
        super(config.getToken());

        this.username = config.getUsername();
        this.integration = integration;
    }

    @PostConstruct
    public void init() {
        List<BotCommand> commands = List.of(
                BotCommand.builder().command(RANDOM.command()).description(RANDOM.description()).build(),
                BotCommand.builder().command(SEARCH.command()).description(SEARCH.description()).build(),
                BotCommand.builder().command(ADD.command()).description(ADD.description()).build(),
                BotCommand.builder().command(CLEAR.command()).description(CLEAR.description()).build(),
                BotCommand.builder().command(SHOW.command()).description(SHOW.description()).build(),
                BotCommand.builder().command(ADD_FAVORITES.command()).description(ADD_FAVORITES.description()).build(),
                BotCommand.builder().command(DELETE_FAVORITES.command()).description(DELETE_FAVORITES.description()).build(),
                BotCommand.builder().command(DELETE_ALL_FAVORITES.command()).description(DELETE_ALL_FAVORITES.description()).build(),
                BotCommand.builder().command(SHOW_ALL_FAVORITES.command()).description(SHOW_ALL_FAVORITES.description()).build()
        );

        try {
            execute(SetMyCommands.builder().commands(commands).build());

            TelegramBotsApi api = new TelegramBotsApi(DefaultBotSession.class);
            api.registerBot(this);
        } catch (Exception exception) {
            throw new CinemaBotException("Cannot register bot", exception);
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        integration.processUpdate(update)
                .forEach(method -> {
                    try {
                        if (method instanceof SendPhoto) {
                            execute((SendPhoto) method);
                        }

                        if (method instanceof SendMessage) {
                            execute((SendMessage) method);
                        }
                    } catch (Exception exception) {
                        throw new CinemaBotException("Cannot process method", exception);
                    }
                });
    }

    @Override
    public String getBotUsername() {
        return username;
    }
}
