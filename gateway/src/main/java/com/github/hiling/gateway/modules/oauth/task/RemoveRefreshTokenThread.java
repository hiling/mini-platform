package com.github.hiling.gateway.modules.oauth.task;

import com.github.hiling.gateway.modules.oauth.model.RevokeToken;
import com.github.hiling.gateway.modules.oauth.service.RefreshTokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Author by hiling, Email admin@mn-soft.com, Date on 10/16/2018.
 */
@Slf4j
public class RemoveRefreshTokenThread extends BaseRemoveTokenThread {

    /**
     * 每执行一次后sleep的时间（秒），默认1秒
     */
    @Value("${oauth.refresh-token.remove-expired.loop-wait:1}")
    final int refreshTokenLoopWait = 10;

    /**
     * 每次移除过期数据时，保留最近几秒的数据，默认5秒
     */
    @Value("${oauth.refresh-token.remove-expired.reserve-time:5}")
    final int refreshTokenReserveTime = 5;

    /**
     * 每次移除的最多行数，避免单次处理数据过多导致数据库性能压力，默认1000条
     */
    @Value("${oauth.refresh-token.remove-expired.max-remove-count:1000}")
    final int refreshTokenMaxRemoveCount = 1000;


    final RefreshTokenService refreshTokenService;

    /**
     * 过期的Refresh Token队列
     */
    private static ConcurrentLinkedQueue<RevokeToken> refreshTokenQueue = new ConcurrentLinkedQueue<>();

    public RemoveRefreshTokenThread(RefreshTokenService refreshTokenService) {
        super.setName("RemoveRefreshTokenThread");
        this.revokeTokens = refreshTokenQueue;
        this.refreshTokenService = refreshTokenService;
        this.loopWait = refreshTokenLoopWait;
        this.reserveTime = refreshTokenReserveTime;
        this.maxRemoveCount = refreshTokenMaxRemoveCount;
    }

    public static boolean addRefreshTokenToRevokeQueue(String clientId, Long userId, LocalDateTime time) {
        log.debug("addRefreshTokenToRevokeQueue({}, {},{})",clientId,userId,time);
        return refreshTokenQueue.offer(RevokeToken.builder().clientId(clientId).userId(userId).time(time).build());
    }

    @Override
    protected void removeRevokeAndExpiredToken(ArrayList<RevokeToken> revokeList) {
        try {
            //查询所有过期token
            List<String> tokenList = refreshTokenService.getRevokeRefreshToken(revokeList);

            //删除数据库
            refreshTokenService.batchDeleteByRefreshToken(tokenList);

            //删除缓存
            //stringRedisTemplate.delete(tokenList);

            refreshTokenService.deleteExpiredRefreshToken();
            log.debug("删除过期的Refresh Token");

        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
    }
}