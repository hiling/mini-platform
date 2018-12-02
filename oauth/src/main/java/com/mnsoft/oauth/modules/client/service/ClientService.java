package com.mnsoft.oauth.modules.client.service;

import com.mnsoft.oauth.modules.client.model.Client;

/**
 * Author by hiling, Email admin@mn-soft.com, Date on 12/1/2018.
 */
public interface ClientService {
    Client get(String clientId, String clientSecret);
}
