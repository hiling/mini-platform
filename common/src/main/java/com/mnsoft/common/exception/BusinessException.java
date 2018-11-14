package com.mnsoft.common.exception;

public class BusinessException extends RuntimeException  {
    public BusinessException(Throwable cause) {
        super(cause);
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(ExceptionMessage message) {
        super(message.getMessage());
        this.code = message.value();
    }

    public BusinessException(Integer code,String message) {
        super(message);
        this.code = code;
    }

    public BusinessException(Integer code,String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    /**
     * 业务定义的异常错误码
     */
    private Integer code;


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
