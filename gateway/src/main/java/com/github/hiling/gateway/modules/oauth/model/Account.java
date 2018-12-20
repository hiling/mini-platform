package com.github.hiling.gateway.modules.oauth.model;

import lombok.Data;

@Data
public class Account {
    private Long userId;
    private String username;
    private String password;
    private String scope;
}
