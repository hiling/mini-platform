package com.github.hiling.auth.service;

import com.github.hiling.auth.model.Account;

public interface AccountService {
    Account login(String username, String password);
}
