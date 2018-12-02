package com.mnsoft.oauth.modules.client.service.impl;

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
    public Client get(String clientId, String clientSecret){
        return clientMapper.get(clientId,clientSecret);
    }
}
