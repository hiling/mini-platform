package com.github.hiling.common.interceptor;

import com.alibaba.fastjson.JSON;
import com.github.hiling.common.config.BaseConfig;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
@Slf4j
public class ApiLogAspect extends BaseConfig {

    /**
     * 控制器切点
     */
    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)"
            + " || @annotation(org.springframework.web.bind.annotation.RestController)"
            + " || @annotation(org.springframework.web.bind.annotation.GetMapping)"
            + " || @annotation(org.springframework.web.bind.annotation.PostMapping)"
            + " || @annotation(org.springframework.web.bind.annotation.DeleteMapping)"
            + " || @annotation(org.springframework.web.bind.annotation.PutMapping)"
            + " || @annotation(org.springframework.web.bind.annotation.PatchMapping)"
    )
    public void controllerAspect() {

    }

    /**
     * 返回通知
     *
     * @param joinPoint
     */
    @Before(value = "controllerAspect()")
    public void beforeReturnMethod(JoinPoint joinPoint) {
        if (loggingHttpRequest) {
            beforeLog(joinPoint);
        }
    }

    /**
     * 返回通知
     *
     * @param joinPoint
     * @param resp      此参数值必须和方法签名里面的resp一致
     */
    @AfterReturning(pointcut = "controllerAspect()", returning = "resp")
    public void afterReturnMethod(JoinPoint joinPoint, Object resp) {
        if (loggingHttpResponse) {
            afterLog(joinPoint, resp);
        }
    }

    /**
     * 抛出通知
     *
     * @param joinPoint
     * @param ex        此参数值必须和方法签名里面的ex一致
     */
    @AfterThrowing(pointcut = "controllerAspect()", throwing = "ex")
    public void afterThrowingMethod(JoinPoint joinPoint, Exception ex) {
        afterLog(joinPoint, ex);
    }


    /**
     * 处理请求日志
     *
     * @param joinPoint
     */
    public void beforeLog(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        String body;
        if (loggingHttpRequestBody && request instanceof RequestWrapper) {
            body = new RequestWrapper(request).getBody();
        } else {
            body = "";
        }

        //只有打印response时，tracing才有用
        log.info(">>>>>>请求日志：[{} {}{}] keys={} token={} body={} ip={}",
                request.getMethod(),
                request.getRequestURI(),
                request.getQueryString() == null ? "" : "?" + request.getQueryString(),
                request.getHeader("keys"),
                request.getHeader("token"),
                body,
                request.getRemoteAddr()
        );

        //class_method={} joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName()
        //log.info("ARGS : " + JSON.toJSONString(joinPoint.getArgs()));
    }

    /**
     * 处理响应日志
     *
     * @param joinPoint
     * @param object
     */
    public void afterLog(JoinPoint joinPoint, Object object) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        log.debug(">>>>>>响应日志：[{} {}{}] resp={} args={}",
                request.getMethod(),
                request.getRequestURI(),
                request.getQueryString() == null ? "" : "?" + request.getQueryString(),
                JSON.toJSONString(object),
                JSON.toJSONString(joinPoint.getArgs())
        );

    }
}
