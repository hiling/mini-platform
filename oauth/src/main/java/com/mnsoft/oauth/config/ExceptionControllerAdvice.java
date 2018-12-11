package com.mnsoft.oauth.config;

import com.mnsoft.common.exception.ExceptionResult;
import com.mnsoft.common.utils.ExceptionUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import javax.servlet.http.HttpServletRequest;

/**
 * Author by hiling, Email admin@mn-soft.com, Date on 12/11/2018.
 */
@RestControllerAdvice
public class ExceptionControllerAdvice {
    @ExceptionHandler(Exception.class)
    public ExceptionResult jsonErrorHandler(HttpServletRequest request, Exception e) {

        ExceptionResult result = ExceptionUtils.getExceptionResult(request, e);
        return result;
    }
}
