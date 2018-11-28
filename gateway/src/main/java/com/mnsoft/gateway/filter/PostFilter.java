package com.mnsoft.gateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.netflix.zuul.filters.post.SendErrorFilter;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * Author by hiling, Email admin@mn-soft.com, Date on 10/15/2018.
 */
@Slf4j
@Component
public class PostFilter  extends ZuulFilter {
    @Override
    public String filterType() {
        return FilterConstants.POST_TYPE;
    }

    /**
     * int值来定义过滤器的执行顺序，数值越小优先级越高
     * @return
     */
    @Override
    public int filterOrder() {
        // Needs to run before SendErrorFilter which has filterOrder == 0
        return 2;
    }

    @Override
    public boolean shouldFilter() {
        // only forward to errorPath if it hasn't been forwarded to already
        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        log.debug("------------------->post");
        log.debug("------------------->post：request:{}",request.getRequestURI());

        //如果出错，Zuul默认的 SendErrorFilter 会添加key并转发到/error请求路径
        if (ctx.containsKey("javax.servlet.error.status_code")){
            log.error("error.status_code:"+ctx.get("javax.servlet.error.status_code").toString());
        }
        return null;
    }
}
