package com.mnsoft.oauth.listener;

import com.mnsoft.oauth.service.RefreshTokenService;
import com.mnsoft.oauth.service.AccessTokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Author by hiling, Email admin@mn-soft.com, Date on 10/9/2018.
 * 每秒移除一次5秒前的过期Token，避免当客户端同时发起获取token和刷新token的请求时，先执行了刷新token，导致获取token失败。
 * ***** 数据库的create_time与last_used_time的精度需要到毫秒级，MySql字段类型须为：DATETIME(3) *****
 */
@Component
@Slf4j
public class ExpiredTokenListener implements InitializingBean {

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