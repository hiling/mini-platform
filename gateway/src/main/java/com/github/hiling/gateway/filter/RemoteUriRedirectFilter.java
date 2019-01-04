package com.github.hiling.gateway.filter;

import com.github.hiling.common.utils.StringUtils;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.util.Map;

/**
 * Author by hiling, Email admin@mn-soft.com, Date on 10/15/2018.
 */
@Slf4j
@Getter
@Setter
@Component
@ConfigurationProperties("link")
public class RemoteUriRedirectFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return FilterConstants.ROUTE_TYPE;
    }

    //值来定义过滤器的执行顺序，数值越小优先级越高
    @Override
    public int filterOrder() {
        return FilterConstants.SEND_FORWARD_FILTER_ORDER - 1;
    }

    @Override
    public boolean shouldFilter() {
        final String serviceId = (String) RequestContext.getCurrentContext().get("proxy");
        return "link".equals(serviceId);
    }

    private Map<String, String> redirect;

    @Override
    public Object run() {
        try {
            RequestContext ctx = RequestContext.getCurrentContext();
            //url格式必须为 /link/key
            String mapKey = ctx.getRequest().getRequestURI().split("/")[2].toLowerCase();
            String path = redirect.get(mapKey);
            if (StringUtils.isNoneEmpty(path)) {
                ctx.getResponse().sendRedirect(path);
            }
        } catch (Exception ex) {
            ReflectionUtils.rethrowRuntimeException(ex);
        }
        return null;
    }
}