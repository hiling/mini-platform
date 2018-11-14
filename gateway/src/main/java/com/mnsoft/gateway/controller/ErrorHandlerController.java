package com.mnsoft.gateway.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mnsoft.common.exception.BusinessException;
import com.mnsoft.common.exception.ExceptionResult;
import com.netflix.zuul.context.RequestContext;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public String getErrorPath() {
        return "/error";
    }

    @RequestMapping("/error")
    public Object error() {
        RequestContext ctx = RequestContext.getCurrentContext();
        Throwable throwable = ctx.getThrowable().getCause();

        if (throwable instanceof FeignException) {
            ctx.setResponseStatusCode(((FeignException) throwable).status());
            String message = throwable.getMessage();
            return message.substring(message.indexOf("{"), message.indexOf("}") + 1);
        }

        ExceptionResult result = new ExceptionResult();
        try {
            if (throwable instanceof BusinessException) {
                result.setCode(((BusinessException) throwable).getCode());
                result.setStatus(HttpStatus.BAD_REQUEST.value());
            }  else {
                result.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
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
            result.setPath(ctx.getRequest().getRequestURI());
            result.setTimestamp(new Date());

            ctx.setResponseStatusCode(result.getStatus());
            ctx.getResponse().setContentType("application/json;charset=UTF-8");

            String json = objectMapper.writeValueAsString(result);
            ctx.setResponseBody(json);
            return json;

        } catch (JsonProcessingException ex) {
            log.error(ex.getMessage());
        }
        return null;
    }
}