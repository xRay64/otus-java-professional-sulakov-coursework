package ru.sulakov.telegrambot.repository;

import org.springframework.data.repository.CrudRepository;
import ru.sulakov.telegrambot.model.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByTelegramId(Long telegramId);
}
