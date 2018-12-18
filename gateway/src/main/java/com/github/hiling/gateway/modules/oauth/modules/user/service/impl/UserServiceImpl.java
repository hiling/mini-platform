package com.github.hiling.gateway.modules.oauth.modules.user.service.impl;

import com.github.hiling.gateway.modules.oauth.model.Account;
import com.github.hiling.gateway.modules.oauth.modules.user.service.UserService;
import com.github.hiling.gateway.modules.oauth.modules.user.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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

    @Value("${user.login.sql:}")
    String loginSql;

    @Override
    public Account login(String username, String password) {

//        String sql = "select id as userId,username, password from user where username=#{username};";
        String sql = StringUtils.replace(loginSql, "{", "#{");
        log.debug("login sql: " + sql);
        Account user = userMapper.login(sql, username, password);
        return user;
    }
}