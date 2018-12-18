package com.github.hiling.gateway.modules.oauth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Author by hiling, Email admin@mn-soft.com, Date on 12/16/2018.
 */
@Component
@PropertySource(value = "classpath:config/oauth.properties")
public class OAuthApplication {
    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
}