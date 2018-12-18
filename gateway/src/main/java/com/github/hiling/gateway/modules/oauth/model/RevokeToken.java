package com.github.hiling.gateway.modules.oauth.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Author by hiling, Email admin@mn-soft.com, Date on 10/12/2018.
 */
@Getter
@Setter
@Builder
public class RevokeToken {
    String clientId;
    String userId;
    LocalDateTime time;
}