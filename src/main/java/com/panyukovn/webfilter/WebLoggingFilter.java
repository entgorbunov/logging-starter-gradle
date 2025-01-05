package com.panyukovn.webfilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class WebLoggingFilter extends HttpFilter {

    private static final Logger log = LoggerFactory.getLogger(WebLoggingFilter.class);
    private static final String MASK = "******";
    private final Set<String> maskedHeaders;

    public WebLoggingFilter(Set<String> maskedHeaders) {
        this.maskedHeaders = maskedHeaders;
    }


    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String method = request.getMethod();
        String requestURI = request.getRequestURI();
        String headers = inlineHeaders(request);

        log.info("Запрос: {} {} {}", method, requestURI, headers);

        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

        try {
            super.doFilter(request, responseWrapper, chain);

            String responseBody = "body=" + new String(responseWrapper.getContentAsByteArray(), StandardCharsets.UTF_8);
            log.info("Ответ: {} {} {} {}", method, requestURI, response.getStatus(), responseBody);
        } finally {
            responseWrapper.copyBodyToResponse();
        }
    }

    private String inlineHeaders(HttpServletRequest request) {
        Map<String, String> headersMap = Collections.list(request.getHeaderNames()).stream()
            .collect(
                Collectors.toMap(
                    Function.identity(),
                    headerName -> shouldMaskHeader(headerName) ? MASK : request.getHeader(headerName)));

        String inlineHeaders = headersMap.entrySet().stream().map(entry -> {
            String headerName = entry.getKey();
            String headerValue = entry.getValue();

            return headerName + "=" + headerValue;
        }).collect(Collectors.joining(","));
        return "headers={" + inlineHeaders + "}";
    }

    private boolean shouldMaskHeader(String headerName) {
        return maskedHeaders.contains(headerName.toLowerCase());
    }
}
