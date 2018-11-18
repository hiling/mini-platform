package com.mnsoft.oauth.constant;

import com.mnsoft.common.exception.ExceptionMessage;

/**
 * Author by hiling, Email admin@mn-soft.com, Date on 10/10/2018.
 * 规范：6位数字组成，每2位1段，分别代表，模块[2位]-功能[2位]-错误码[2位]
 * 错误码分两类，公共错误和业务逻辑错误，公共错误码前面为100000，后两位从01开始，如：100000001
 * 模块：
 *  gateway: 10
 *  oauth:  11
 *      token:10
 *      client:11
 *      user:12
 *  discovery: 12
 *  common:19
 *
 *  user:   20
 *  item:   21
 *TOKEN_REFRESH_CLIENT_ID_not_Match
 */
public enum ErrorMessage implements ExceptionMessage {

    TOKEN_GRANT_TYPE_NOT_SUPPORTED(111001, "grant_type仅支持password,client_credentials和refresh_token。"),
    TOKEN_AUTHORIZATION_ERROR(111002, "Authorization信息错误。"),
    TOKEN_CLIENT_ERROR(111003, "客户端信息错误。"),
    TOKEN_USER_ERROR(111004, "用户信息错误。"),
    TOKEN_REFRESH_TOKEN_REQUIRED(111005, "refresh_token不能为空。"),
    TOKEN_REFRESH_TOKEN_ERROR(111006, "refresh_token无效。"),
    TOKEN_REFRESH_TOKEN_EXPIRATION(111007, "refresh_token已过期。"),
    TOKEN_REFRESH_CLIENT_ID_NOT_MATCH(111008, "clientId与经过授权的clientId不匹配。"),
    CLIENT_ID_EXIST(111101, "clientId已存在。"),
    CLIENT_NAME_EXIST(111102, "clientName已存在。"),
    UnknownError(111199, "未知异常");

    private final int value;
    private final String message;

    ErrorMessage(int value, String message) {
        this.value = value;
        this.message = message;
    }

    public int value() {
        return this.value;
    }

    public String getMessage() {
        return this.message;
    }

    public String toString() {
        return Integer.toString(this.value);
    }
}
