package ru.sulakov.telegrambot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TelegramBotApplication {
    private static final Logger log = LoggerFactory.getLogger(TelegramBotApplication.class);


    public static void main(String[] args) {
       SpringApplication.run(TelegramBotApplication.class, args);
    }

}
