package com.sadbeast.dao;

import com.sadbeast.dto.UserDto;
import org.dalesbred.Database;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class UserDao {
    private final Database db;

    @Inject
    public UserDao(final Database db) {
        this.db = db;
    }

    public UserDto findByUsername(final String username) {
        return db.findUniqueOrNull(UserDto.class, "SELECT user_id AS id, password FROM users WHERE username = ?", username);
    }
}
