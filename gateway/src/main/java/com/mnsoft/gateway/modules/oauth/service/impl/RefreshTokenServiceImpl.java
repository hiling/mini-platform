package com.mnsoft.gateway.modules.oauth.service.impl;

import com.mnsoft.gateway.modules.oauth.mapper.RefreshTokenMapper;
import com.mnsoft.gateway.modules.oauth.model.RevokeToken;
import com.mnsoft.gateway.modules.oauth.model.RefreshToken;
import com.mnsoft.gateway.modules.oauth.service.RefreshTokenService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * Author by hiling, Email admin@mn-soft.com, Date on 10/12/2018.
 */
@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

    @Resource
    private RefreshTokenMapper refreshTokenMapper;

    public List<String> getRevokeRefreshToken(List<RevokeToken> list){
        if (list.isEmpty()){
            return Arrays.asList();
        }
        return refreshTokenMapper.getRevokeRefreshToken(list);
    }

   public void batchDeleteByRefreshToken(List<String> refreshTokenList){
        if (refreshTokenList.isEmpty()){
            return;
        }
        refreshTokenMapper.batchDeleteByRefreshToken(refreshTokenList);
    }

    public void deleteExpiredRefreshToken(){
        refreshTokenMapper.deleteExpiredRefreshToken();
    }
}
