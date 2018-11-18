package com.mnsoft.oauth.service;

import com.mnsoft.oauth.model.Account;
import com.mnsoft.oauth.service.impl.UserServiceImpl;
import com.mnsoft.common.constant.ServiceNames;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = ServiceNames.USER_SERVICE,fallback = UserServiceImpl.class)
public interface UserService {

    @GetMapping("/user")
    Account getByAccount(@RequestParam(value = "username") String username, @RequestParam(value = "password") String password);

}
