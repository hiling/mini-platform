package com.mnsoft.gateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Author by hiling, Email admin@mn-soft.com, Date on 11/25/2018.
 */
@Slf4j
@Component
public class SendErrorBeforeFilter extends ZuulFilter {

    @Override
    public String filterType() {
        return FilterConstants.ERROR_TYPE;
    }

    @Override
    public int filterOrder() {
        //需要在默认的 SendErrorFilter 之前
        return -1;
    }

    @Override
    public boolean shouldFilter() {
        //return RequestContext.getCurrentContext().getThrowable() != null;
        return true;
    }

    @Override
    public Object run() {
        log.info("------------------->error before");
        try {
            RequestContext ctx = RequestContext.getCurrentContext();
            Throwable throwable = ctx.getThrowable();
            if (throwable!=null){
                log.error("Throwable:"+throwable.getMessage());
            }else {
                log.error("Throwable is null");
            }

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

    private void responseOutJson(HttpServletResponse response, String json) {
        //将实体对象转换为JSON Object转换
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.append(json);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }
}