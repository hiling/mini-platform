package com.mnsoft.gateway.modules.oauth.service;

import com.mnsoft.gateway.modules.oauth.model.Account;

public interface AccountService {
    Account login(String username, String password);
}
