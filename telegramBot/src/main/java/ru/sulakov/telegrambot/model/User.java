package ru.sulakov.telegrambot.model;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Table(value = "users")
public class User {
    @Id
    private Long id;
    private final long telegramId;
    private final String firstName;
    private final String lastName;

    @PersistenceConstructor

    public User(Long id, long telegramId, String firstName, String lastName) {
        this.id = id;
        this.telegramId = telegramId;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
