package com.github.hiling.common.config;

import org.springframework.beans.factory.annotation.Value;

public class BaseConfig {
    @Value("${spring.application.name}")
    public String applicationName;

    @Value("${spring.application.description:API服务}")
    public String applicationDescription;

    @Value("${spring.application.url:https://api.mn-soft.com}")
    public String applicationServiceUrl;

    @Value("${spring.application.version:0.0.1")
    public String applicationVersion;

    /**
     * 是否打印http request日志， 默认：true
     */
    @Value("${config.logging.httpRequest.enabled:true}")
    public Boolean loggingHttpRequest;

    /**
     * 是否打印http request的body日志，默认: true
     *
     * 只打印ContentType包含application/json的request
     */
    @Value("${config.logging.httpRequest.includeBody:true}")
    public Boolean loggingHttpRequestBody;

    /**
     * 是否打印http response日志， 默认：false
     */
    @Value("${config.logging.httpResponse.enabled:false}")
    public Boolean loggingHttpResponse;
}
