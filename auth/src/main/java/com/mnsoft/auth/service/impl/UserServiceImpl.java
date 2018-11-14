package com.mnsoft.auth.service.impl;

import com.mnsoft.auth.model.Account;
import com.mnsoft.auth.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    public Account getByAccount(String username, String password) {
        return null;
    }
}
