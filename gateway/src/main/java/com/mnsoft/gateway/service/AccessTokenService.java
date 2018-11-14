package com.mnsoft.gateway.service;

import com.mnsoft.common.constant.ServiceNames;
import com.mnsoft.gateway.service.impl.AccessTokenServiceImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Author by hiling, Email admin@mn-soft.com, Date on 10/14/2018.
 */
@FeignClient(name = ServiceNames.AUTH_SERVICE,fallback = AccessTokenServiceImpl.class)
public interface AccessTokenService {

    @GetMapping("/token")
    String getJwtToken(@RequestParam(value = "access_token") String accessToken);
}
