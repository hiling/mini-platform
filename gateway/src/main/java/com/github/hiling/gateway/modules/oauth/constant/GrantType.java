package com.github.hiling.gateway.modules.oauth.constant;

import org.springframework.lang.Nullable;

public enum GrantType {

    /**
     * 暂不支持
     */
//    AUTHORIZATION_CODE("authorization_code"),

    /**
     * 暂不支持
     */
//    IMPLICIT("implicit"),

    PASSWORD("password"),
    CLIENT_CREDENTIALS("client_credentials"),
    REFRESH_TOKEN("refresh_token");

    private final String type;

    GrantType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

    public String toString() {
        return this.type;
    }

    public static GrantType typeOf(String type) {
        GrantType grantType = resolve(type);
        if (grantType == null) {
            throw new IllegalArgumentException("没有找到 [" + type + "]");
        } else {
            return grantType;
        }
    }

    @Nullable
    public static GrantType resolve(String typeString) {
        GrantType[] grantTypes = values();
        for (GrantType grantType : grantTypes) {
            if (grantType.type.equalsIgnoreCase(typeString)) {
                return grantType;
            }
        }
        return null;
    }
}
