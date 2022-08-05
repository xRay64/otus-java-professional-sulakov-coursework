package ru.sulakov.telegrambot.service;

import ru.sulakov.telegrambot.model.User;

import java.util.Optional;

public interface UserService {
    void saveUser(long telegram_id, String firstName, String lastName);

    Optional<User> getUserById(long id);
}
