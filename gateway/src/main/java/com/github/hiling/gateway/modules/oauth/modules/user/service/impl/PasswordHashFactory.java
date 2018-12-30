package com.github.hiling.gateway.modules.oauth.modules.user.service.impl;

import com.github.hiling.gateway.modules.oauth.modules.user.service.PasswordHash;

import javax.validation.constraints.NotNull;

/**
 * Author by hiling, Email admin@mn-soft.com, Date on 12/30/2018.
 */
public class PasswordHashFactory {

    private PasswordHashFactory() {
    }

    private static class PlaintextSingletonHolder {
        private static final PasswordPlaintext INSTANCE = new PasswordPlaintext();
    }

    private static class MD5SingletonHolder {
        private static final PasswordMD5 INSTANCE = new PasswordMD5();
    }

    private static class PBKDF2SingletonHolder {
        private static final PasswordPBKDF2 INSTANCE = new PasswordPBKDF2();
    }

    private static class BCryptSingletonHolder {
        private static final PasswordBCrypt INSTANCE = new PasswordBCrypt();
    }

    public static final PasswordHash getInstance(@NotNull String hashType) {
        switch (hashType.toLowerCase()) {
            case "md5": {
                return MD5SingletonHolder.INSTANCE;
            }
            case "bcrypt": {
                return BCryptSingletonHolder.INSTANCE;
            }
            case "pbkdf2": {
                return PBKDF2SingletonHolder.INSTANCE;
            }
            default: {
                return PlaintextSingletonHolder.INSTANCE;
            }
        }
    }
}
