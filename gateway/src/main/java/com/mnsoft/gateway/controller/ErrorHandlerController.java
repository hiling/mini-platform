package com.mnsoft.gateway.controller;

import com.mnsoft.common.exception.BusinessException;
import com.mnsoft.common.exception.ExceptionResult;
import com.mnsoft.common.utils.json.JsonUtils;
import com.mnsoft.gateway.helper.ZuulBusinessException;
import com.netflix.zuul.context.RequestContext;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * Author by hiling, Email admin@mn-soft.com, Date on 10/15/2018.
 */

@Slf4j
@RestController
public class ErrorHandlerController implements ErrorController {

    @Override
    public String getErrorPath() {
        return "/error";
    }

    @RequestMapping("/error")
    public Object error() {
        log.error("-----------------> /error ");
        RequestContext ctx = RequestContext.getCurrentContext();
        Throwable throwable = getOriginException(ctx.getThrowable());
        if (throwable == null) {
            log.error("-----------------> /error 未获取到异常！<-------");
        }

        if (throwable instanceof FeignException) {
            ctx.setResponseStatusCode(((FeignException) throwable).status());
            String message = throwable.getMessage();
            String json = message.substring(message.indexOf("{"), message.indexOf("}") + 1);
            return json;
        }

        ExceptionResult result = new ExceptionResult();

        if (throwable instanceof ZuulBusinessException) {
            result.setCode(((ZuulBusinessException) throwable).getCode());
            result.setStatus(HttpStatus.BAD_REQUEST.value());
            result.setPath(((ZuulBusinessException) throwable).getUri());
        } else {
            if (throwable instanceof BusinessException) {
                result.setCode(((BusinessException) throwable).getCode());
                result.setStatus(HttpStatus.BAD_REQUEST.value());
            } else {
                result.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            }
            result.setPath(ctx.getRequest().getRequestURI());
        }
        result.setMessage(throwable.getMessage());

        StringBuilder sbStackTrace = new StringBuilder();
        //堆栈信息
        if (throwable.getStackTrace() != null && throwable.getStackTrace().length > 0) {
            for (StackTraceElement trace : throwable.getStackTrace()) {
                sbStackTrace.append(trace.toString());
                break;
            }
        }
        result.setError(sbStackTrace.toString());
        result.setTimestamp(new Date());

        ctx.setResponseStatusCode(result.getStatus());
        ctx.getResponse().setContentType("application/json;charset=UTF-8");

        String json = JsonUtils.toJson(result);
        ctx.setResponseBody(json);
        return json;
    }

    private Throwable getOriginException(Throwable e){
        e = e.getCause();
        while(e.getCause() != null){
            e = e.getCause();
        }
        return e;
    }
}