package com.github.hiling.gateway.modules.oauth.task;

import com.github.hiling.gateway.modules.oauth.service.AccessTokenService;
import com.github.hiling.gateway.modules.oauth.service.RefreshTokenService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;

/**
 * Author by hiling, Email admin@mn-soft.com, Date on 12/16/2018.
 */
public class ExpireTokenHandler implements InitializingBean {

    @Resource
    private AccessTokenService accessTokenService;

    @Resource
    private RefreshTokenService refreshTokenService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void afterPropertiesSet() {
        AccessTokenRevokeThread accessTokenExpiredThread = new AccessTokenRevokeThread(accessTokenService, stringRedisTemplate);
        accessTokenExpiredThread.start();

        RefreshTokenRevokeThread refreshTokenExpiredThread = new RefreshTokenRevokeThread(refreshTokenService);
        refreshTokenExpiredThread.start();
    }
}