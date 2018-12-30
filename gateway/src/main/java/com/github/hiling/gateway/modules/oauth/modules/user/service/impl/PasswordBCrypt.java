package com.github.hiling.gateway.modules.oauth.modules.user.service.impl;

import com.github.hiling.gateway.modules.oauth.modules.user.service.PasswordHash;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Author by hiling, Email admin@mn-soft.com, Date on 12/30/2018.
 */
public class PasswordBCrypt implements PasswordHash {

    public boolean validate(String password, String salt, String hashPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        //加密时使用
        //String hashPassword = passwordEncoder.encode(password + salt);
        return passwordEncoder.matches(password + salt, hashPassword);
    }
}