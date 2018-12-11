package com.mnsoft.oauth.modules.client.service.impl;

import com.mnsoft.common.exception.BusinessException;
import com.mnsoft.common.utils.UuidUtils;
import com.mnsoft.oauth.constant.ErrorMessage;
import com.mnsoft.oauth.modules.client.model.Client;
import com.mnsoft.oauth.modules.client.mapper.ClientMapper;
import com.mnsoft.oauth.modules.client.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Author by hiling, Email admin@mn-soft.com, Date on 12/1/2018.
 */
@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    ClientMapper clientMapper;

    @Override
    public Client get(String clientId, String clientSecret) {
        return clientMapper.get(clientId, clientSecret);
    }

    public int insert(Client client) {
        return clientMapper.insert(client);
    }

    public String refreshSecret(String clientId, String currentSecret) {
        String newSecret = UuidUtils.getUUID();
        int result = clientMapper.refreshSecret(clientId, currentSecret, newSecret);
        if (result > 0){
            return newSecret;
        }
        return null;
    }
}
