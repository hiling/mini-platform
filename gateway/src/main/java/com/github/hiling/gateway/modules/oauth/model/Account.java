package com.github.hiling.gateway.modules.oauth.model;

import lombok.Data;

@Data
public class Account {
    private Long userId;
    private String username;
    private String password;

    /**
     * 授权范围
     */
    private String scope;

    /**
     * 密码加盐
     */
    private String salt;
}
