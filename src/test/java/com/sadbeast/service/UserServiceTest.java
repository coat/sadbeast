package com.sadbeast.service;

import com.sadbeast.dao.UserDao;
import com.sadbeast.dto.UserDto;
import com.sadbeast.web.security.SBAccount;
import com.sadbeast.web.beans.LoginBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mindrot.jbcrypt.BCrypt;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
    @Mock
    private UserDao userDao;

    @Test
    public void validUserGetsToken() {
        LoginBean validLoginBean = new LoginBean("username", "password");
        String encryptedPassword = BCrypt.hashpw(validLoginBean.getPassword(), BCrypt.gensalt());

        when(userDao.findByUsername(validLoginBean.getUsername())).thenReturn(new UserDto(1L, encryptedPassword));

        UserService userService = new UserService(userDao);

        // Valid password
        SBAccount account = userService.authenticate(validLoginBean.getUsername(), validLoginBean.getPassword());
        assertNotNull(account);
        assertNotNull(account.getToken());

        // Invalid password
        LoginBean invalidLoginBean = new LoginBean("username", "whoops");
        account = userService.authenticate(invalidLoginBean.getUsername(), invalidLoginBean.getPassword());
        assertNull(account);

        // Invalid user
        when(userDao.findByUsername(validLoginBean.getUsername())).thenReturn(null);
        userService = new UserService(userDao);
        account = userService.authenticate(validLoginBean.getUsername(), validLoginBean.getPassword());
        assertNull(account);
    }
}
