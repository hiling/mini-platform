package com.github.hiling.gateway.modules.oauth.task;

import com.github.hiling.gateway.modules.oauth.service.AccessTokenService;
import com.github.hiling.gateway.modules.oauth.service.RefreshTokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Author by hiling, Email admin@mn-soft.com, Date on 12/16/2018.
 */
@Slf4j
@Component
public class RemoveTokenRunner implements ApplicationRunner {

    @Resource
    private AccessTokenService accessTokenService;

    @Resource
    private RefreshTokenService refreshTokenService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void run(ApplicationArguments args) {
        log.info("启动清除Token任务！");
        RemoveAccessTokenThread accessTokenExpiredThread = new RemoveAccessTokenThread(accessTokenService, stringRedisTemplate);
        accessTokenExpiredThread.start();

        RemoveRefreshTokenThread refreshTokenExpiredThread = new RemoveRefreshTokenThread(refreshTokenService);
        refreshTokenExpiredThread.start();
    }
}