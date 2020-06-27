package com.github.hiling.auth.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;


/**
 * ActiveRecord 模式实现（开发效率高，但容易在controller层或service层直接写业务逻辑，所以不建议生产代码使用，可以用于测试代码）
 *  1、继承 Model<T>
 *  2、通过pkVal()方法指定主键
 *  3、创建UserMapper
 *  4、使用示例：
 *      new AccessToken().setId(1L).selectById().getAccessToken()
 *      new AccessToken().setId(1L).setAccessToken("abc").updateById()
 */
@Data
@Accessors(chain = true)
public class AccessToken {

    @JsonIgnore
    private Long id;

    @JsonIgnore
    private String clientId;

    @JsonIgnore
    private Long userId;

    private String accessToken;

    @JsonIgnore
    private String jwtToken;

    private String refreshToken;

    private Integer expiresIn;

    //@JsonIgnore
    private LocalDateTime createTime;

}
