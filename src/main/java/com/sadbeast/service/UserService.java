package com.sadbeast.service;

import com.sadbeast.dao.UserDao;
import com.sadbeast.dto.UserDto;
import com.sadbeast.util.JWTUtil;
import com.sadbeast.web.security.SBAccount;
import org.mindrot.jbcrypt.BCrypt;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class UserService {
    private final UserDao userDao;

    @Inject
    public UserService(final UserDao userDao) {
        this.userDao = userDao;
    }

    public SBAccount authenticate(final String username, final String password) {
        UserDto user = userDao.findByUsername(username);

        // User is not found, but hash a password anyway to prevent a timing attack on enumerating usernames
        if (user == null) {
            BCrypt.hashpw("sadbeast", BCrypt.gensalt());
            return null;
        }

        if (BCrypt.checkpw(password, user.getPassword())) {
            return new SBAccount(username, JWTUtil.createJWT(user));
        } else {
            return null;
        }
    }
}
