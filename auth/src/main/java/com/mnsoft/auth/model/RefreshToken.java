package com.mnsoft.auth.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Author by hiling, Email admin@mn-soft.com, Date on 10/11/2018.
 */
@Data
@Accessors(chain = true)
@TableName("oauth_refresh_token")
public class RefreshToken extends Model<RefreshToken> {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String clientId;

    private String userId;

    private String refreshToken;

    private Integer expiresIn;

    private LocalDateTime createTime;

    private LocalDateTime lastUsedTime;

    @Override
    protected Serializable pkVal() {
        return id;
    }
}
