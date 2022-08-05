package ru.sulakov.telegrambot.bot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.abilitybots.api.objects.Locality;
import org.telegram.abilitybots.api.objects.Privacy;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.sulakov.telegrambot.model.User;
import ru.sulakov.telegrambot.service.ExchangeInfoService;
import ru.sulakov.telegrambot.service.UserService;

import java.util.Optional;

@Service
public class MyAbilityBot extends AbilityBot {
    public static final Logger log = LoggerFactory.getLogger(MyAbilityBot.class);
    private static final String BOT_NAME = Optional.ofNullable(System.getenv("BOT_NAME"))
            .orElseThrow(() -> new RuntimeException("Check environment variable BOT_NAME"));
    private static final String BOT_TOKEN = Optional.ofNullable(System.getenv("BOT_TOKEN"))
            .orElseThrow(() -> new RuntimeException("Check environment variable BOT_TOKEN"));
    private final UserService userService;
    private final ExchangeInfoService exchangeInfoService;

    public MyAbilityBot(UserService userService, ExchangeInfoService exchangeInfoService) {
        super(BOT_TOKEN, BOT_NAME);
        this.userService = userService;
        this.exchangeInfoService = exchangeInfoService;
    }

    @Override
    public long creatorId() {
        return 292225453;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            long userId = update.getMessage().getFrom().getId();
            Optional<User> userById = userService.getUserById(userId);
            if (userById.isEmpty()) {
                userService.saveUser(
                        userId,
                        update.getMessage().getFrom().getFirstName(),
                        update.getMessage().getFrom().getLastName()
                );
            }
        }
        super.onUpdateReceived(update);
    }

    public Ability getExchangeRates() {
        return Ability.builder()
                .name("rate")
                .info("Получить курс обмена USD/RUB")
                .privacy(Privacy.PUBLIC)
                .locality(Locality.USER)
                .input(0)
                .action(messageContext -> {
                    String responseText = exchangeInfoService.getInfoMessageText()
                            .orElse("Не могу знать");
                    silent.send(responseText, messageContext.chatId());
                })
                .enableStats()
                .build();
    }
}
