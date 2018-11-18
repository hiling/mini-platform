package com.mnsoft.oauth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mnsoft.oauth.model.AccessToken;
import com.mnsoft.oauth.model.RevokeToken;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AccessTokenMapper extends BaseMapper<AccessToken> {
    String getRefreshToken(@Param("accessToken") String accessToken);

    List<String> getRevokeAccessToken(List<RevokeToken> list);

    void batchDeleteByAccessToken(@Param("accessTokenList") List<String> accessTokenList);

    void deleteExpiredAccessToken();
}
