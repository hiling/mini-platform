package com.mnsoft.oauth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mnsoft.oauth.mapper.RefreshTokenMapper;
import com.mnsoft.oauth.model.RevokeToken;
import com.mnsoft.oauth.model.RefreshToken;
import com.mnsoft.oauth.service.RefreshTokenService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * Author by hiling, Email admin@mn-soft.com, Date on 10/12/2018.
 */
@Service
public class RefreshTokenServiceImpl  extends ServiceImpl<RefreshTokenMapper,RefreshToken> implements RefreshTokenService {

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
