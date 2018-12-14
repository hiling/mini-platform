package com.mnsoft.gateway.filter;

import com.mnsoft.common.exception.BusinessException;
import com.mnsoft.common.exception.ExceptionResult;
import com.mnsoft.common.utils.json.JsonUtils;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.util.Enumeration;

/**
 * Author by hiling, Email admin@mn-soft.com, Date on 11/25/2018.
 *  统一处理异常
 * 需要禁用默认的SendErrorFilter过滤器，禁用配置：zuul.SendErrorFilter.error.disable=true
 *
 */
@Slf4j
@Component
public class ErrorFilter extends ZuulFilter {

    @Override
    public String filterType() {
        return FilterConstants.ERROR_TYPE;
    }

    @Override
    public int filterOrder() {
        return -1;
    }

    @Override
    public boolean shouldFilter() {
        return RequestContext.getCurrentContext().getThrowable() != null;
    }

    @Override
    public Object run() {
        try {
            RequestContext ctx = RequestContext.getCurrentContext();

            Enumeration<String> headerIt = ctx.getRequest().getHeaderNames();
            StringBuilder sb = new StringBuilder();
            while (headerIt.hasMoreElements()) {
                String name = headerIt.nextElement();
                String value = ctx.getRequest().getHeader(name);
                sb.append("REQUEST:: > " + name + ":" + value+"\n");
            }
            log.debug(sb.toString());

            Throwable throwable = getOriginException(ctx.getThrowable());
            ExceptionResult result = getExceptionResult(ctx.getRequest(), throwable);

            ctx.setSendZuulResponse(false);

            String json = JsonUtils.toJson(result);
            HttpServletResponse response = ctx.getResponse();
            response.setContentType("application/json;charset=UTF-8");
            //response.getOutputStream().write(json.getBytes());

            PrintWriter writer = null;
            try {
                writer = response.getWriter();
                writer.print(json);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if(writer!=null){
                    writer.close();
                }
            }
        } catch (Exception ex) {
            log.error("-------------->Exception filtering in custom error filter", ex);
            ReflectionUtils.rethrowRuntimeException(ex);
        }
        return null;
    }

    private ExceptionResult getExceptionResult(HttpServletRequest request, Throwable throwable) {

        ExceptionResult result = new ExceptionResult();
        result.setPath(getUri(request));

        if (throwable instanceof BusinessException) {
            result.setCode(((BusinessException) throwable).getCode());
            result.setStatus(HttpStatus.BAD_REQUEST.value());
            result.setMessage(throwable.getMessage());
        } else {
            if (throwable instanceof ConnectException) {
                result.setCode(HttpStatus.REQUEST_TIMEOUT.value());
                result.setStatus(HttpStatus.REQUEST_TIMEOUT.value());
                result.setMessage("请求后端服务超时。");
            } else {
                result.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
                result.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
                result.setMessage(throwable.toString());
            }
        }

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

    Throwable getOriginException(Throwable e) {
        e = e.getCause();
        while (e.getCause() != null) {
            log.debug("===========> error info: {}", e.toString());
            e = e.getCause();
        }
        return e;
    }

    private String getUri(HttpServletRequest request) {
        String queryString = request.getQueryString();
        return request.getMethod() + ":" + request.getRequestURI() + (queryString.isEmpty() ? "" : ("?" + queryString));
    }
}