package com.mnsoft.common.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import java.util.Date;

@Getter
@Setter
@Accessors(chain = true)
public class ExceptionResult {

    public ExceptionResult() {
    }

    public ExceptionResult(Integer code, String message, Integer status, String error, String path) {
        this.code = code;
        this.message = message;
        this.status = status;
        this.error = error;
        this.path = path;
        this.timestamp = new Date();
    }

    /**
     * 业务异常Code
     */
    private Integer code;

    /**
     * 业务异常信息
     */
    private String message;

    /**
     * HttpStatus
     */
    private Integer status;
    /**
     * 默认异常信息
     */
    private String error;

    /**
     * 异常路径
     */
    private String path;

    /**
     * 异常时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private Date timestamp;



}
