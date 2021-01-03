package com.github.hiling.auth.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * Author by hiling, Email admin@mn-soft.com, Date on 10/11/2018.
 */
@Data
@Accessors(chain = true)
public class RefreshToken {

    private Long id;

    private String clientId;

    private Long userId;

    private String refreshToken;

    private Integer expiresIn;

    private LocalDateTime createTime;

    private LocalDateTime lastUsedTime;

}
