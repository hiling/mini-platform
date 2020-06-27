package com.github.hiling.auth.mapper;

import com.github.hiling.auth.model.AccessToken;
import com.github.hiling.auth.model.RevokeToken;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AccessTokenMapper {
    String getRefreshToken(@Param("accessToken") String accessToken);

    List<String> getRevokeAccessToken(List<RevokeToken> list);

    Long insert(AccessToken accessToken);

    void batchDeleteByAccessToken(@Param("accessTokenList") List<String> accessTokenList);

    void deleteExpiredAccessToken();
}
