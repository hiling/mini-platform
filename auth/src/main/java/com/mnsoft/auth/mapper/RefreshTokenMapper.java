package com.mnsoft.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mnsoft.auth.model.RevokeToken;
import com.mnsoft.auth.model.RefreshToken;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Author by hiling, Email admin@mn-soft.com, Date on 10/12/2018.
 */
public interface RefreshTokenMapper extends BaseMapper<RefreshToken> {
    List<String> getRevokeRefreshToken(List<RevokeToken> list);

    void batchDeleteByRefreshToken(@Param("refreshTokenList") List<String> refreshTokenList);

    void deleteExpiredRefreshToken();
}
