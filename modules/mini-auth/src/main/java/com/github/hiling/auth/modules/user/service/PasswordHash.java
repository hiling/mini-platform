package com.github.hiling.auth.modules.user.service;

/**
 * Author by hiling, Email admin@mn-soft.com, Date on 12/30/2018.
 */
public interface PasswordHash {
    boolean validate(String password, String salt, String hashPassword);
}
