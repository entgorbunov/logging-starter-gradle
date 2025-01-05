package com.panyukovn.webfilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.AntPathMatcher;

import java.util.Set;

public class LoggingSkipService {

    private static final Logger log = LoggerFactory.getLogger(LoggingSkipService.class);

    private final AntPathMatcher pathMatcher = new AntPathMatcher();
    private final Set<String> excludedPaths;

    public LoggingSkipService(Set<String> excludedPaths) {
        this.excludedPaths = excludedPaths;
        log.debug("LoggingSkipService инициализирован с исключенными путями {}", excludedPaths);
    }

    public boolean shouldSkipLogging(String requestURI) {
        boolean shouldSkip = excludedPaths.stream()
            .anyMatch(pattern -> pathMatcher.match(pattern, requestURI));

        if (shouldSkip) {
            log.debug("URL {} пропущен согласно настройкам", requestURI);
        }
        return shouldSkip;
    }
}
