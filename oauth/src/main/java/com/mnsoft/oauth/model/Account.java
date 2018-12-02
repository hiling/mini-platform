package com.mnsoft.oauth.model;

import lombok.Data;

@Data
public class Account {
    private String userId;
    private String username;
    private String password;
    private String scope;
}
