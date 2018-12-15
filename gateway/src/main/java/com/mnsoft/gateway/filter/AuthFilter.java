package com.mnsoft.gateway.filter;

import com.mnsoft.common.exception.BusinessException;
import com.mnsoft.common.utils.StringUtils;
import com.mnsoft.gateway.modules.oauth.constant.ErrorMessage;
import com.mnsoft.gateway.modules.oauth.service.AccessTokenService;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * Author by hiling, Email admin@mn-soft.com, Date on 10/15/2018.
 */
@Slf4j
@Component
public class AuthFilter extends ZuulFilter {
    @Autowired
    AccessTokenService tokenService;

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    //int值来定义过滤器的执行顺序，数值越小优先级越高
    @Override
    public int filterOrder() {
        return 2;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();

        String uri = request.getRequestURI();
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
            throw new BusinessException(ErrorMessage.ACCESS_TOKEN_ERROR);
        }

        try {
            jwtToken = tokenService.getJwtToken(accessToken);
        } catch (BusinessException e) {
            throw new BusinessException(e.getCode(), e.getMessage());
        }

        if (StringUtils.isEmpty(jwtToken)) {
            throw new BusinessException(ErrorMessage.ACCESS_TOKEN_ERROR);
        }
        ctx.addZuulRequestHeader("jwtToken", jwtToken);

        return null;
    }
}