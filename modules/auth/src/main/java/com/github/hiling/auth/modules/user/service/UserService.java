package com.github.hiling.auth.modules.user.service;

import com.github.hiling.auth.model.Account;

/**
 * Author by hiling, Email admin@mn-soft.com, Date on 11/29/2018.
 */
public interface UserService {
    Account login(String username, String password);
}
