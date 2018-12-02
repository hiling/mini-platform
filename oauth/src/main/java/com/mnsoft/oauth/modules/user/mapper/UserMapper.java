package com.mnsoft.oauth.modules.user.mapper;

import com.mnsoft.oauth.model.Account;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Author by hiling, Email admin@mn-soft.com, Date on 11/29/2018.
 */
@Repository
public interface UserMapper {
    Account login(@Param("sql") String sql, @Param("username") String username, @Param("password") String password);
}
