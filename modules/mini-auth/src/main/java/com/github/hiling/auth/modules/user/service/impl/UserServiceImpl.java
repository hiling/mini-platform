package com.github.hiling.auth.modules.user.service.impl;

import com.github.hiling.common.utils.StringUtils;
import com.github.hiling.auth.model.Account;
import com.github.hiling.auth.modules.user.service.PasswordHash;
import com.github.hiling.auth.modules.user.service.UserService;
import com.github.hiling.auth.modules.user.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


/**
 * Author by hiling, Email admin@mn-soft.com, Date on 11/29/2018.
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    @Value("${oauth.user.login.sql}")
    String loginSql;

    @Value("${oauth.user.password.hash.type}")
    String passwordHashType;

    @Override
    public Account login(String username, String password) {

//       String sql = "select id as userId,username, password, '' as salt from user where username=#{username};";
        String sql = StringUtils.replace(loginSql, "{", "#{");
        log.debug("login sql: " + sql);
        Account user = userMapper.login(sql, username, password);

        if (user != null) {
            PasswordHash passwordHash = PasswordHashFactory.getInstance(passwordHashType);
            log.warn("PasswordHashFactory:{}", passwordHash);
            if (passwordHash.validate(password, user.getSalt() == null ? "" : user.getSalt(), user.getPassword())) {
                return user;
            }
        }

        return null;
    }
}