package com.github.hiling.auth.constant;

import com.github.hiling.common.exception.ExceptionMessage;

/**
 * Author by hiling, Email admin@mn-soft.com, Date on 10/10/2018.
 *
 * 错误码分两类，公共错误和业务逻辑错误，公共错误码前面为100000，后两位从01开始，如：100000001
 * 业务错误规范：6位数字组成，每两位一段，分别代表，模块[2位]--功能[2位]--错误码[2位]
 * 公共错误规范：6位数字组成，每三位一段，分别代表，模块[业务模块2位后面补零]--错误码[3位，以HttpStatus码为准]
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
 *TOKEN_REFRESH_CLIENT_ID_NOT_MATCH
 */
public enum ErrorMessage implements ExceptionMessage {

    TOKEN_GRANT_TYPE_NOT_SUPPORTED(111001, "grant_type仅支持password,client_credentials和refresh_token。"),
    TOKEN_AUTHORIZATION_ERROR(111002, "Authorization信息错误。"),
    TOKEN_CLIENT_ERROR(111003, "clientId或client_secret错误。"),
    TOKEN_USER_ERROR(111004, "用户名或密码错误。"),
    ACCESS_TOKEN_ERROR(111010, "access_token无效或已过期。"),
    TOKEN_REFRESH_TOKEN_REQUIRED(111005, "refresh_token不能为空。"),
    TOKEN_REFRESH_TOKEN_ERROR(111006, "refresh_token无效。"),
    TOKEN_REFRESH_TOKEN_EXPIRATION(111007, "refresh_token已过期。"),
    TOKEN_REFRESH_CLIENT_ID_NOT_MATCH(111008, "clientId不正确。"),
    TOKEN_IP_WHITELIST_ERROR(111009, "获取Token的IP地址不在白名单中。"),
    CLIENT_ID_EXIST(111101, "clientId已存在。"),
    CLIENT_NAME_EXIST(111102, "clientName已存在。"),
    CLIENT_REFRESH_SECRET_ERROR(111103, "clientId或currentSecret不正确。"),
    CLIENT_UPDATE_STATUS_ERROR(111104, "更新状态信息错误，请稍后重试。"),
    USER_LOGIN_URL_EMPTY(111201, "用户认证服务地址为空。"),
    UNAUTHORIZED_ERROR(110401, "您没有该授权。"),
    UNKNOWN_ERROR(110500, "未知异常。");

    private final int value;
    private final String message;

    ErrorMessage(int value, String message) {
        this.value = value;
        this.message = message;
    }

    @Override
    public int value() {
        return this.value;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public String toString() {
        return Integer.toString(this.value);
    }
}
