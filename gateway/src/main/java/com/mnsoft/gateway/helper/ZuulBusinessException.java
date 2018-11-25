package com.mnsoft.gateway.helper;

import com.mnsoft.common.exception.BusinessException;
import com.mnsoft.common.exception.ExceptionMessage;

/**
 * Author by hiling, Email admin@mn-soft.com, Date on 11/25/2018.
 */
public class ZuulBusinessException extends BusinessException {

    public ZuulBusinessException(ExceptionMessage message, String uri) {
        super(message.getMessage());
        super.setCode(message.value());
        this.uri = uri;
    }

    public ZuulBusinessException(Integer code,String message, String uri) {
        super(code, message);
        this.uri = uri;
    }

    private String uri;

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
