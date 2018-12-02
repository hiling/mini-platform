package com.mnsoft.oauth.modules.client.mapper;

import com.mnsoft.oauth.modules.client.model.Client;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Author by hiling, Email admin@mn-soft.com, Date on 12/1/2018.
 */
@Repository
public interface ClientMapper {
    Client get(@Param("clientId") String clientId, @Param("clientSecret") String clientSecret);
}
