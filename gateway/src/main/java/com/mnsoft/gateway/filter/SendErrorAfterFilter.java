package com.mnsoft.gateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.post.SendErrorFilter;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.util.ReflectionUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Author by hiling, Email admin@mn-soft.com, Date on 11/27/2018.
 */
@Slf4j
public class SendErrorAfterFilter extends ZuulFilter {

    @Override
    public String filterType() {
        return FilterConstants.ERROR_TYPE;
    }

    @Override
    public int filterOrder() {
        //需要在默认的 SendErrorFilter 之后
//        SendErrorFilter
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        log.info("------------------->error after.shouldFilter");
        return RequestContext.getCurrentContext().containsKey("throwable");
    }

    @Override
    public Object run() {
        log.info("------------------->error before.run");
        try {
            RequestContext ctx = RequestContext.getCurrentContext();
            Object e = ctx.get("throwable");

            if (e != null && e instanceof ZuulException) {
                ZuulException zuulException = (ZuulException) e;

                // Remove error code to prevent further error handling in follow up filters
                // 删除该异常信息,不然在下一个过滤器中还会被执行处理
                //ctx.remove("throwable");
                // 根据具体的业务逻辑来处理
                //ctx.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
                log.error("Exception filtering in custom error filter", zuulException.getMessage());
            }
        } catch (Exception ex) {
            log.error("Exception filtering in custom error filter", ex);
            ReflectionUtils.rethrowRuntimeException(ex);
        }
        return null;

    }
}