package com.github.hiling.gateway.filter;

import com.github.hiling.common.constant.ServiceNames;
import com.github.hiling.common.exception.BusinessException;
import com.github.hiling.common.utils.StringUtils;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.List;

/**
 * Author by hiling, Email admin@mn-soft.com, Date on 10/15/2018.
 */
@Slf4j
@Component
public class AuthFilter extends ZuulFilter {
    @Autowired
    TokenFeignClient tokenService;
    public static final String ACCESS_TOKEN_ERROR = "access_token无效或已过期。";

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    /**
     * int值来定义过滤器的执行顺序，数值越小优先级越高
     * @return
     */
    @Override
    public int filterOrder() {
        return 2;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Value("#{'${auth.ignore.path-prefix}'.split(',')}")
    private List<String> authIgnorePaths;

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();

        String uri = request.getRequestURI();

        //忽略不需要授权的连接
        int pathCount = authIgnorePaths.size();
        for (int i = 0; i < pathCount; i++) {
            if (uri.startsWith(authIgnorePaths.get(i))) {
                return null;
            }
        }

        String method = request.getMethod();

        log.debug("------------------->pre Request:{}:{}",method, uri);

        //内部应用通过jwt_token访问后端服务
        String jwtToken = request.getHeader("jwt_token");
        if (StringUtils.isNotEmpty(jwtToken)) {
            return null;
        }

        //外部应用通过access_token访问后端服务,需要使用access_token在OAuth Server上换取jwtToken后传递给后方服务
        String accessToken = request.getParameter("access_token");

        if (StringUtils.isEmpty(accessToken)) {
            throw new BusinessException(ACCESS_TOKEN_ERROR);
        }

        try {
            jwtToken = tokenService.getJwtToken(accessToken);
        } catch (BusinessException e) {
            throw new BusinessException(e.getCode(), e.getMessage());
        }

        if (StringUtils.isEmpty(jwtToken)) {
            throw new BusinessException(ACCESS_TOKEN_ERROR);
        }
        ctx.addZuulRequestHeader("jwtToken", jwtToken);

        return null;
    }

    @FeignClient(name = ServiceNames.AUTH_SERVICE)
    public interface TokenFeignClient {
        @RequestMapping(value = "token?access_token={access_token}", method = RequestMethod.GET)
        String getJwtToken(@PathVariable("access_token") String accessToken);
    }
}