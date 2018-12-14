package com.mnsoft.gateway.filter;

import com.mnsoft.common.exception.BusinessException;
import com.mnsoft.common.utils.StringUtils;
import com.mnsoft.gateway.helper.ErrorMessage;
import com.mnsoft.gateway.service.AccessTokenService;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;

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

        log.debug("------------------->pre");
        log.debug("------------------->pre：request:{}", request.getRequestURI());

        String uri = request.getRequestURI();
        String method = request.getMethod();

        if (uri.contains("/token")) {
            //创建token
            if ("POST".equalsIgnoreCase(method)) {
                //获取access token时，必须有Authorization
                String authorization = request.getHeader("Authorization");
                if (authorization == null || !authorization.startsWith("Basic ")) {
                    throw new BusinessException(ErrorMessage.AUTHORIZATION_ERROR);
                }
                String clientId, clientSecret;
                try {
                    String authDecode = new String(Base64Utils.decodeFromString(authorization.substring(6)));
                    String[] accounts = authDecode.split(":");
                    clientId = accounts[0];
                    clientSecret = accounts[1];
                } catch (Exception e) {
                    throw new BusinessException(ErrorMessage.AUTHORIZATION_ERROR);
                }

                ctx.addZuulRequestHeader("clientId", clientId);
                ctx.addZuulRequestHeader("clientSecret", clientSecret);
            } else {
                //通过access_token获取jwtToken
                String accessToken = request.getParameter("access_token");
                if (StringUtils.isEmpty(accessToken)) {
                    throw new BusinessException(ErrorMessage.ACCESS_TOKEN_ERROR);
                }
            }
        } else {
            //使用access_token换取jwtToken后传递给后方服务
            String accessToken = request.getParameter("access_token");
            if (StringUtils.isEmpty(accessToken)){
                throw new BusinessException(ErrorMessage.ACCESS_TOKEN_ERROR);
            }

            String jwtToken;
            try {
                jwtToken = tokenService.getJwtToken(accessToken);
            } catch (BusinessException e) {
                throw new BusinessException(e.getCode(), e.getMessage());
            }

            if (StringUtils.isEmpty(jwtToken)) {
                throw new BusinessException(ErrorMessage.ACCESS_TOKEN_ERROR);
            }
            ctx.addZuulRequestHeader("jwtToken", jwtToken);
        }
        return null;
    }
}