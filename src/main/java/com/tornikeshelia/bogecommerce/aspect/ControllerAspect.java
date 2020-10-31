package com.tornikeshelia.bogecommerce.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class ControllerAspect {

    @Before("execution(* com.tornikeshelia.bogecommerce.controller.*.*(..))")
    public void preLogger(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        log.info("================Received Http Request================");
        log.info("HTTP METHOD = {}", request.getMethod());
        log.info("URI = {}", request.getRequestURI());
        log.info("QUERY = {}", request.getQueryString());
        log.info("CLASS_METHOD = {}", joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        log.info("ARGS = {}", Arrays.toString(joinPoint.getArgs()));
        log.info("REQUESTER IP = {}", request.getRemoteAddr());
    }

}
