package com.mnsoft.oauth.modules.client.controller;

import com.mnsoft.common.exception.BusinessException;
import com.mnsoft.oauth.constant.ErrorMessage;
import com.mnsoft.oauth.modules.client.model.Client;
import com.mnsoft.oauth.modules.client.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
        return clientService.get(clientId, clientSecret);
    }

    @PutMapping
    public ResponseEntity<String> refreshSecret(String clientId, String currentSecret) {
        String newSecret = clientService.refreshSecret(clientId, currentSecret);
        if (newSecret == null) {
            throw new BusinessException(ErrorMessage.CLIENT_REFRESH_SECRET_ERROR);
           //return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(newSecret);
    }
}
