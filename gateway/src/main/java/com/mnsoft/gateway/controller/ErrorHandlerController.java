package com.mnsoft.gateway.controller;

import com.mnsoft.common.exception.BusinessException;
import com.mnsoft.common.exception.ExceptionResult;
import com.mnsoft.common.utils.json.JsonUtils;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.http.HttpServletRequestWrapper;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.ConnectException;
import java.util.Date;

import com.mnsoft.common.utils.ExceptionUtils;

/**
 * Author by hiling, Email admin@mn-soft.com, Date on 10/15/2018.
 *
 *  已在配置中禁用了 SendErrorFilter 过滤器，不会路由到该页面，目前使用 ErrorFilter 处理异常。
 */

@Slf4j
@RestController
public class ErrorHandlerController implements ErrorController {

    final static String errorPath = "/error";

    @Override
    public String getErrorPath() {
        return errorPath;
    }

    @RequestMapping(errorPath)
    public Object error() {
        log.error("-----------------> /error ");
        RequestContext ctx = RequestContext.getCurrentContext();
        Throwable throwable = ctx.getThrowable();

        if (throwable == null) {
            log.error("-----------------> /error 未获取到异常！<-------");
            return null;
        }

        throwable = ExceptionUtils.getOriginException(ctx.getThrowable());

        if (throwable instanceof FeignException) {
            ctx.setResponseStatusCode(((FeignException) throwable).status());
            String message = throwable.getMessage();
            String json = message.substring(message.indexOf("{"), message.indexOf("}") + 1);
            return json;
        }

        ExceptionResult result = ExceptionUtils.getExceptionResult(ctx.getRequest(), throwable);

        if (result.getPath().startsWith(errorPath)) {
            //获取源uri
            String errorUri = ((HttpServletRequestWrapper) ctx.getRequest()).getRequest().getRequestURI();
            result.setPath(errorUri);
        }

        ctx.setResponseStatusCode(result.getStatus());
        ctx.getResponse().setContentType("application/json;charset=UTF-8");

        String json = JsonUtils.toJson(result);
        ctx.setResponseBody(json);
        return json;
    }
}