package com.panyukovn.webfilter;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import java.lang.reflect.Type;
import java.util.Optional;
import java.util.Set;

public class WebLoggingRequestBodyAdvice extends RequestBodyAdviceAdapter {

    private static final Logger log = LoggerFactory.getLogger(WebLoggingRequestBodyAdvice.class);

    private final LoggingSkipService loggingSkipService;

    @Autowired
    private HttpServletRequest request;

    public WebLoggingRequestBodyAdvice(Set<String> excludedPaths) {
        this.loggingSkipService = new LoggingSkipService(excludedPaths);
    }

    @Override
    public Object afterBodyRead(
        Object body,
        HttpInputMessage inputMessage,
        MethodParameter parameter,
        Type targetType,
        Class<? extends HttpMessageConverter<?>> converterType
    ) {
        String method = request.getMethod();
        String requestURI = request.getRequestURI() + formatQueryString(request);

        if (loggingSkipService.shouldSkipLogging(requestURI)) {
            return body;
        }

        log.info("Тело запроса: {} {} {}", method, requestURI, body);

        return super.afterBodyRead(body, inputMessage, parameter, targetType, converterType);
    }

    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    private String formatQueryString(HttpServletRequest request) {
        return Optional.ofNullable(request.getQueryString()).map(qs -> "?" + qs).orElse(Strings.EMPTY);
    }
}
