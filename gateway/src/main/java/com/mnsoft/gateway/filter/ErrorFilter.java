package com.mnsoft.gateway.filter;

import com.mnsoft.common.exception.ExceptionResult;
import com.mnsoft.common.utils.ExceptionUtils;
import com.mnsoft.common.utils.json.JsonUtils;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.netflix.zuul.http.HttpServletRequestWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

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

            Throwable throwable = ExceptionUtils.getOriginException(ctx.getThrowable());
            ExceptionResult result = ExceptionUtils.getExceptionResult(ctx.getRequest(), throwable);

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
}