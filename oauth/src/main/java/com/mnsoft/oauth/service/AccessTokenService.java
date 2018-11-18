package com.mnsoft.oauth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mnsoft.oauth.model.AccessToken;
import com.mnsoft.oauth.model.RevokeToken;

import java.util.List;

/**
 * Author by hiling, Email admin@mn-soft.com, Date on 10/9/2018.
 */
public interface AccessTokenService extends IService<AccessToken> {

    String getJwtToken(String accessToken);

    boolean deleteToken(String accessToken);

    List<String> getRevokeAccessToken(List<RevokeToken> list);

    void deleteExpiredAccessToken();

    void batchDeleteByAccessToken(List<String> accessTokenList);

    AccessToken createAccessToken(String clientId,String clientSecret, String grantType, String username, String password, String refreshToken);

}