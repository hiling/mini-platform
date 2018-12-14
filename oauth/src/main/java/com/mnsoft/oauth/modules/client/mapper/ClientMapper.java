package com.mnsoft.oauth.modules.client.mapper;

import com.mnsoft.oauth.modules.client.model.Client;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Author by hiling, Email admin@mn-soft.com, Date on 12/1/2018.
 */
@Repository
public interface ClientMapper {

    List<Client> getList(@Param("clientId") String clientId,
                         @Param("clientName") String clientName,
                         @Param("clientSecret") String clientSecret,
                         @Param("status") Integer status);

    Client getForVerify(@Param("clientId") String clientId, @Param("clientSecret") String clientSecret);

    int refreshSecret(@Param("clientId") String clientId,
                      @Param("currentSecret") String currentSecret,
                      @Param("newSecret") String newSecret,
                      @Param("userId") String userId);

    int updateStatus(@Param("clientId") String clientId,
                     @Param("status") Integer status,
                     @Param("userId") String userId);

    int insert(Client client);
}
