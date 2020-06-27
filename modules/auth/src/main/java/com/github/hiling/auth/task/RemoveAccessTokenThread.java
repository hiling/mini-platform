package com.github.hiling.auth.task;

import com.github.hiling.auth.model.RevokeToken;
import com.github.hiling.auth.constant.RedisNamespaces;
import com.github.hiling.auth.service.AccessTokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Author by hiling, Email admin@mn-soft.com, Date on 10/16/2018.
 */
@Slf4j
public class RemoveAccessTokenThread extends BaseRemoveTokenThread {

    /**
     * 每执行一次清除过期Token后sleep的时间（秒），默认1秒
     */
    @Value("${oauth.access-token.remove-expired.loop-wait:1}")
    final int accessTokenLoopWait = 10;

    /**
     * 每次移除过期数据时，保留最近几秒的数据，默认5秒
     */
    @Value("${oauth.access-token.remove-expired.reserve-time:5}")
    final int accessTokenReserveTime = 5;

    /**
     * 每次移除的最多行数，避免单次处理数据过多导致数据库性能压力，默认1000条
     */
    @Value("${oauth.access-token.remove-expired.max-remove-count:1000}")
    final int accessTokenMaxRemoveCount = 1000;

    final AccessTokenService accessTokenService;
    final StringRedisTemplate stringRedisTemplate;

    /**
     * 过期的Access Token队列（先进先出）
     * size()要遍历整个集合，很慢，避免使用
     */
    private static ConcurrentLinkedQueue<RevokeToken> accessTokenQueue = new ConcurrentLinkedQueue<>();

    /**
     * 过期的Access Token队列（先进先出）
     * size()要遍历整个集合，很慢，避免使用
     */
    //private static ConcurrentLinkedQueue<RevokeToken> revokeTokens;
    public RemoveAccessTokenThread(AccessTokenService accessTokenService,
                                   StringRedisTemplate stringRedisTemplate) {
        setName("RemoveAccessTokenThread");
        this.revokeTokens = accessTokenQueue;
        this.accessTokenService = accessTokenService;
        this.stringRedisTemplate = stringRedisTemplate;
        this.loopWait = accessTokenLoopWait;
        this.reserveTime = accessTokenReserveTime;
        this.maxRemoveCount = accessTokenMaxRemoveCount;
    }

    public static boolean addAccessTokenToRevokeQueue(String clientId, Long userId, LocalDateTime time) {
        log.debug("addAccessTokenToRevokeQueue({}, {},{})", clientId, userId, time);
        return accessTokenQueue.offer(RevokeToken.builder().clientId(clientId).userId(userId).time(time).build());
    }

    @Override
    protected void removeRevokeAndExpiredToken(ArrayList<RevokeToken> revokeList) {
        try {
            //查询被吊销的token
            List<String> revokeTokens = accessTokenService.getRevokeAccessToken(revokeList);

            //删除数据库中被吊销的token
            accessTokenService.batchDeleteByAccessToken(revokeTokens);

            //删除缓存中被吊销的token
            List<String> revokeTokenKeys = new ArrayList<>(revokeTokens.size());
            for (String token : revokeTokens) {
                revokeTokenKeys.add(RedisNamespaces.ACCESS_TOKEN + token);
            }
            stringRedisTemplate.delete(revokeTokenKeys);

            //删除过期的token
            accessTokenService.deleteExpiredAccessToken();
            log.debug("删除过期的Access Token");

        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
    }
}