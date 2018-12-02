package com.mnsoft.oauth.modules.user.service;

import com.mnsoft.oauth.model.Account;

/**
 * Author by hiling, Email admin@mn-soft.com, Date on 11/29/2018.
 */
public interface UserService {
    Account login(String username, String password);
}
