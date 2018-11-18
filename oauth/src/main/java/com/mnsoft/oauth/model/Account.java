package com.mnsoft.oauth.model;

import lombok.Data;

@Data
public class Account {
    private String username;
    private String password;
    private String[] roles;
}
