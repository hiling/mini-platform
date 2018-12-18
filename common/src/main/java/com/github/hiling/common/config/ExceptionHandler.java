package com.github.hiling.common.config;

import com.github.hiling.common.exception.BusinessException;
import com.github.hiling.common.exception.ExceptionResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import java.net.ConnectException;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Author by hiling, Email admin@mn-soft.com, Date on 10/19/2018.
 */
@Slf4j
public class ExceptionHandler {
    @ResponseBody
    @org.springframework.web.bind.annotation.ExceptionHandler(value = BusinessException.class)
    public ExceptionResult businessException(HttpServletRequest request, BusinessException e) {
        String uri = getUri(request);
        String stackTrace = getStackTrace(e.getStackTrace());
        writeLog(uri, e.getMessage(), stackTrace);
        return new ExceptionResult(e.getCode(), e.getMessage(), HttpStatus.BAD_REQUEST.value(), stackTrace, uri);
    }

    @ResponseBody
    @org.springframework.web.bind.annotation.ExceptionHandler(value = ConnectException.class)
    public ExceptionResult connectException(HttpServletRequest request, ConnectException e) {
        String uri = getUri(request);
        String stackTrace = getStackTrace(e.getStackTrace());
        Integer code = HttpStatus.GATEWAY_TIMEOUT.value();

        writeLog(uri, "无法连接到远程服务器。", stackTrace);
        return new ExceptionResult(code, "无法连接到远程服务器。", code, stackTrace, uri);
    }

    @ResponseBody
    @org.springframework.web.bind.annotation.ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ExceptionResult methodArgumentNotValidException(HttpServletRequest request, MethodArgumentNotValidException e) {

        String uri = getUri(request);
        String stackTrace = getStackTrace(e.getStackTrace());
        List<ObjectError> errors = e.getBindingResult().getAllErrors();
        StringBuffer errorMsg = new StringBuffer();
        errors.stream().forEach(x -> errorMsg.append(x.getDefaultMessage()).append(";"));
        String message = errorMsg.toString();
        Integer code = HttpStatus.BAD_REQUEST.value();

        writeLog(uri, message, stackTrace);
        return new ExceptionResult(code, message, code, stackTrace, uri);
    }

    @ResponseBody
    @org.springframework.web.bind.annotation.ExceptionHandler(value = NoHandlerFoundException.class)
    public ExceptionResult noHandlerFoundException(HttpServletRequest request, NoHandlerFoundException e) {
        String uri = getUri(request);
        String stackTrace = getStackTrace(e.getStackTrace());
        Integer code = HttpStatus.NOT_FOUND.value();

        writeLog(uri, "接口不存在！", stackTrace);
        return new ExceptionResult(code, "接口不存在！", code, stackTrace, uri);
    }

    @ResponseBody
    @org.springframework.web.bind.annotation.ExceptionHandler(value = Exception.class)
    public ExceptionResult defaultException(HttpServletRequest request, Exception e) {
        String uri = getUri(request);
        String stackTrace = getStackTrace(e.getStackTrace());
        Integer code = HttpStatus.INTERNAL_SERVER_ERROR.value();

        writeLog(uri, e.getMessage(), stackTrace);
        return new ExceptionResult(code, e.getMessage(), code, stackTrace, uri);
    }

    private String getStackTrace(StackTraceElement[] element) {
        String stackTrace = null;
        //堆栈信息
        if (element != null && element.length > 0) {
            stackTrace = element[0].toString();
        }
        return stackTrace;
    }

    private String getUri(HttpServletRequest request) {
        String queryString = request.getQueryString();
        return request.getMethod() + ":" + request.getRequestURI() + (queryString.isEmpty() ? "" : ("?" + queryString));
    }

    private void writeLog(String uri, String message, String stackTrace) {
        StringBuilder error = new StringBuilder();
        error.append(System.getProperty("line.separator") + "Error:" + System.getProperty("line.separator"));
        error.append("URI:" + uri + System.getProperty("line.separator"));
        error.append("Message:" + message + System.getProperty("line.separator"));
        error.append("StackTrace:" + stackTrace + System.getProperty("line.separator"));
        error.append("Time:" + LocalDateTime.now().toString() + System.getProperty("line.separator"));
        log.error(error.toString());
    }
}
