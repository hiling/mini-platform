package com.github.hiling.auth.modules.client.controller;

import com.github.hiling.common.exception.BusinessException;
import com.github.hiling.auth.modules.client.model.Client;
import com.github.hiling.auth.constant.ErrorMessage;
import com.github.hiling.auth.modules.client.service.ClientService;
import com.github.hiling.oauth.client.web.BaseController;
import com.github.hiling.oauth.client.web.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Author by hiling, Email admin@mn-soft.com, Date on 12/2/2018.
 */
@RestController
@RequestMapping("/client")
public class ClientController extends BaseController {

    @Autowired
    private ClientService clientService;

    @GetMapping
    public ResponseEntity<List<Client>> getList(String clientId, String clientName, String clientSecret, Integer status) {
        List<String> scope = getScopeList();
        List<Client> list = clientService.getList(clientId, clientName, clientSecret, status, scope);
        return ResponseEntity.ok(list);
    }

    @PostMapping()
    public ResponseEntity<Boolean> insert(Client client) {
        UserInfo userInfo = getUserInfo();
        int newSecret = clientService.insert(client, userInfo.getUserName(), userInfo.getScopeList());
        if (newSecret == 0) {
            throw new BusinessException(ErrorMessage.UNKNOWN_ERROR);
            //return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(true);
    }

    @PutMapping("/secret")
    public ResponseEntity<String> refreshSecret(String clientId, String currentSecret) {
        UserInfo userInfo = getUserInfo();
        String newSecret = clientService.refreshSecret(clientId, currentSecret, userInfo.getUserName(), userInfo.getScopeList());
        if (newSecret == null) {
            throw new BusinessException(ErrorMessage.CLIENT_REFRESH_SECRET_ERROR);
            //return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(newSecret);
    }

    @PutMapping("/status")
    public ResponseEntity<Boolean> updateStatus(String clientId, Integer status) {
        UserInfo userInfo = getUserInfo();
        Integer result = clientService.updateStatus(clientId, status, userInfo.getUserName(), userInfo.getScopeList());
        if (result == 0) {
            throw new BusinessException(ErrorMessage.CLIENT_REFRESH_SECRET_ERROR);
            //return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(true);
    }
}
