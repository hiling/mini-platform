package com.mnsoft.oauth.service;

import com.mnsoft.oauth.model.Account;

public interface AccountService {
    Account login(String username, String password);
}
