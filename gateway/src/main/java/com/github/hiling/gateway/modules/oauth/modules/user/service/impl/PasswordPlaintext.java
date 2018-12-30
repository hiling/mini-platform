package com.github.hiling.gateway.modules.oauth.modules.user.service.impl;

import com.github.hiling.common.utils.StringUtils;
import com.github.hiling.gateway.modules.oauth.modules.user.service.PasswordHash;

/**
 * Author by hiling, Email admin@mn-soft.com, Date on 12/31/2018.
 */
public class PasswordPlaintext implements PasswordHash {
    public boolean validate(String password, String salt, String hashPassword) {
        return StringUtils.equals(password, hashPassword);
    }
}
