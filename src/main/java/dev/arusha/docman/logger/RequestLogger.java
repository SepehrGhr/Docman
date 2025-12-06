package dev.arusha.docman.logger;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Enumeration;
import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class RequestLogger {

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void restControllerPointCut() {
    }

    @Around("restControllerPointCut()")
    public Object logRequestAndResponse(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest httpServletRequest = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        log.info("------- Request Details --------");
        log.info("URL: {}", httpServletRequest.getRequestURL());
        log.info("Http Method: {}", httpServletRequest.getMethod());

        log.info("Headers: ");
        Enumeration<String> headers = httpServletRequest.getHeaderNames();
        while (headers.hasMoreElements()) {
            String name = headers.nextElement();
            log.info("  {}: {}", name, httpServletRequest.getHeader(name));
        }

        if (httpServletRequest.getQueryString() != null) {
            log.info("Query Params: {}", httpServletRequest.getQueryString());
        }

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        log.info("Method Name: {}", signature.getMethod().getName());
        log.info("Arguments: {}", Arrays.toString(joinPoint.getArgs()));

        Object response;
        try {
            response = joinPoint.proceed();
        } catch (Exception e) {
            log.error("Exception in request: {}", e.getMessage());
            throw e;
        }

        log.info("Response: {}", response);
        log.info("------------------------------");
        return response;
    }
}