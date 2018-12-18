package com.github.hiling.gateway.modules.oauth.service;

import com.github.hiling.gateway.modules.oauth.model.Account;

public interface AccountService {
    Account login(String username, String password);
}
