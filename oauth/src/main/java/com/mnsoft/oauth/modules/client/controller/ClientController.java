package com.mnsoft.oauth.modules.client.controller;

import com.mnsoft.oauth.modules.client.model.Client;
import com.mnsoft.oauth.modules.client.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author by hiling, Email admin@mn-soft.com, Date on 12/2/2018.
 */
@RestController
@RequestMapping("/client")
public class ClientController {
    @Autowired
    private ClientService clientService;

    @GetMapping()
    public Client get(String clientId, String clientSecret) {
        return clientService.get(clientId,clientSecret);
    }


}
