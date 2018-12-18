package com.github.hiling.gateway.modules.oauth.service.impl;

import com.github.hiling.gateway.modules.oauth.model.Account;
import com.github.hiling.gateway.modules.oauth.modules.user.service.UserService;
import com.github.hiling.common.exception.BusinessException;
import com.github.hiling.gateway.modules.oauth.constant.ErrorMessage;
import com.github.hiling.gateway.modules.oauth.service.AccountService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private UserService userService;

    /**
     * 用户登陆类型，支持sql和url
     */
    @Value("${user.login.type:sql}")
    String loginType;

    /**
     * 用户登陆的服务地址
     */
    @Value("${user.login.url}")
    String loginUrl;

    public Account login(String username, String password) {
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            return null;
        }

        if ("sql".equalsIgnoreCase(loginType)) {
            return userService.login(username, password);
        } else {
            //url方式
            if (StringUtils.isEmpty(loginUrl)) {
                throw new BusinessException(ErrorMessage.USER_LOGIN_URL_EMPTY);
            }

            String url = StringUtils.replaceOnceIgnoreCase(StringUtils.replaceOnceIgnoreCase(loginUrl, "{username}", username), "{password}", password);

            ResponseEntity<Account> account = restTemplate.getForEntity(url, Account.class);
            return account.getBody();
        }
    }
}
