package com.mnsoft.gateway.helper;


import com.mnsoft.common.exception.ExceptionMessage;

/**
 * Author by hiling, Email admin@mn-soft.com, Date on 10/14/2018.
 */
public enum ErrorMessage implements ExceptionMessage {

    AUTHORIZATION_ERROR(111001, "Authorization错误。"),
    ACCESS_TOKEN_ERROR(111002, "access_token已过期或无效。"),
    UnknownError(111199, "未知异常");

    private final int value;
    private final String message;

    private ErrorMessage(int value, String message) {
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