package ru.sulakov.telegrambot.service;

import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sulakov.telegrambot.model.User;
import ru.sulakov.telegrambot.repository.UserRepository;
import ru.sulakov.telegrambot.sessionmanager.TransactionManagerSpring;

import java.util.Optional;

@Service
@AllArgsConstructor
@Log
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final TransactionManagerSpring transactionManager;

    @Override
    @Transactional
    public void saveUser(long telegram_id, String firstName, String lastName) {
        transactionManager.doInTransaction(() -> userRepository.save(new User(null, telegram_id, firstName, lastName)));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> getUserById(long id) {
        return userRepository.findByTelegramId(id);
    }
}
