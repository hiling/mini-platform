package com.mnsoft.oauth.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mnsoft.oauth.constant.ErrorMessage;
import com.mnsoft.oauth.model.Client;
import com.mnsoft.common.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/client")
public class ClientController {

    @GetMapping
    public ResponseEntity<List<Client>> get() {
        Client client = new Client();
        List<Client> list = client.selectAll();
        return ResponseEntity.ok(list);
    }

    @PostMapping
    public ResponseEntity<Boolean> insert(@RequestBody @Validated Client client) {

        if (StringUtils.isNotEmpty(client.getClientId())){
            //判断客户姓名是否存在
            boolean existsClientId = client.selectCount(new QueryWrapper<Client>().lambda().eq(Client::getClientId, client.getClientId())) > 0;
            if (existsClientId){
                throw new BusinessException(ErrorMessage.CLIENT_ID_EXIST);
            }
        }else {
            //不填时，自动生成不重复的随机clientId
            String clientId = null;
            boolean existsClientId = true;
            while (existsClientId) {
                clientId = RandomStringUtils.random(16, true, true);
                existsClientId = client.selectCount(new QueryWrapper<Client>().lambda().eq(Client::getClientId, clientId)) > 0;
            }
            client.setClientId(clientId);
        }

        //判断客户姓名是否存在
        boolean existsClientName = client.selectCount(new QueryWrapper<Client>().lambda().eq(Client::getClientName, client.getClientName())) > 0;
        if (existsClientName){
            throw new BusinessException(ErrorMessage.CLIENT_NAME_EXIST);
        }

        //生成随机密钥
        String clientSecret = RandomStringUtils.random(32, true, true);
        client.setClientSecret(clientSecret);
        boolean insertResult = client.insert();
        return ResponseEntity.ok(insertResult);
    }
}
