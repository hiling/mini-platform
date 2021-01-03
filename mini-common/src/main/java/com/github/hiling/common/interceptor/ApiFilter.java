package com.github.hiling.common.interceptor;

import com.github.hiling.common.config.BaseConfig;
import com.github.hiling.common.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class ApiFilter extends BaseConfig implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        HttpServletRequest request = (HttpServletRequest) req;

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE, PATCH");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept,keys");
        response.setHeader("Access-Control-Expose-Headers", "Location");

        // get和delete方法不处理body体
        String method = request.getMethod();
        if (HttpMethod.GET.matches(method) || HttpMethod.DELETE.matches(method)){
            chain.doFilter(req, res);
            return;
        }

        // 当打印body日志开启，且contentType=application/json时，需处理body体用于打印日志
        String contentType = request.getContentType();
        if (loggingHttpRequestBody && StringUtils.isNotEmpty(contentType)
                && contentType.toLowerCase().contains(MediaType.APPLICATION_JSON_VALUE)) {
            req = new RequestWrapper(request);
        }
        chain.doFilter(req, res);
    }

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void destroy() {
    }
}
