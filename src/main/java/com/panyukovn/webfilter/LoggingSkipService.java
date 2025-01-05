package com.panyukovn.webfilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.AntPathMatcher;

import java.util.Set;

public class LoggingSkipService {

    private static final Logger log = LoggerFactory.getLogger(LoggingSkipService.class);

    private final Set<AntPathMatcher> excludedPaths;

    public LoggingSkipService(Set<AntPathMatcher> excludedPaths) {
        this.excludedPaths = excludedPaths;
    }

    public boolean shouldSkipLogging(String requestURI) {
        boolean shouldSkip = excludedPaths.stream()
            .anyMatch(matcher -> matcher.match(matcher.toString(), requestURI));

        if (shouldSkip) {
            log.debug("Логгирование для URL {} отключено настройками", requestURI);
        }
        return shouldSkip;
    }
}
