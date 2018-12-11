package com.mnsoft.common.utils;

import com.mnsoft.common.exception.BusinessException;
import com.mnsoft.common.exception.ExceptionResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;
import java.net.ConnectException;

/**
 * Author by hiling, Email admin@mn-soft.com, Date on 12/11/2018.
 */
@Slf4j
public class ExceptionUtils {

    final static String errorPath = "/error";

    public static ExceptionResult getExceptionResult(HttpServletRequest request, Throwable throwable){

        ExceptionResult result = new ExceptionResult();

        if (throwable instanceof BusinessException) {
            result.setCode(((BusinessException) throwable).getCode());
            result.setStatus(HttpStatus.BAD_REQUEST.value());
            result.setMessage(throwable.getMessage());
        } else {
            if (throwable instanceof ConnectException) {
                result.setCode(HttpStatus.REQUEST_TIMEOUT.value());
                result.setStatus(HttpStatus.REQUEST_TIMEOUT.value());
                result.setMessage("无法连接到远程服务器。");
            }else {
                result.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
                result.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
                result.setMessage(throwable.toString());
            }
        }

        String errorUri = request.getRequestURI();

        if (errorUri.startsWith(errorPath)) {
            //获取源uri
            log.debug("-----> log path: {}",errorUri);
        }
        result.setPath(errorUri);

        StringBuilder sbStackTrace = new StringBuilder();
        //堆栈信息
        if (throwable.getStackTrace() != null && throwable.getStackTrace().length > 0) {
            for (StackTraceElement trace : throwable.getStackTrace()) {
                sbStackTrace.append(trace.toString());
                break;
            }
        }
        result.setError(sbStackTrace.toString());
        return result;
    }


    public static Throwable getOriginException(Throwable e) {
        e = e.getCause();
        while (e.getCause() != null) {
            log.debug("===========> error info: {}",e.toString());
            e = e.getCause();
        }
        return e;
    }
}
