package com.mnsoft.oauth.modules.client.service;

import com.mnsoft.oauth.modules.client.model.Client;

import java.util.List;

/**
 * Author by hiling, Email admin@mn-soft.com, Date on 12/1/2018.
 */
public interface ClientService {

    List<Client> getList(String clientId,String clientName,String clientSecret,Integer status, List<String> scope);

    int insert(Client client, String loginUserId, List<String> scope);

    int updateStatus(String clientId, Integer status, String loginUserId, List<String> scope);

    String refreshSecret(String clientId, String clientSecret, String loginUserId, List<String> scope);
}
