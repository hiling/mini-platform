package com.github.hiling.auth.service;

import com.github.hiling.auth.model.RevokeToken;

import java.util.List;

/**
 * Author by hiling, Email admin@mn-soft.com, Date on 10/12/2018.
 */
public interface RefreshTokenService {

    List<String> getRevokeRefreshToken(List<RevokeToken> list);

    void batchDeleteByRefreshToken(List<String> refreshTokenList);

    void deleteExpiredRefreshToken();
}
