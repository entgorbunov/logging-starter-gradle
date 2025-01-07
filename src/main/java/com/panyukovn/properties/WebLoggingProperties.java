package com.panyukovn.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.AntPathMatcher;

import java.util.Set;

@ConfigurationProperties(prefix = "logging.web-logging")
public class WebLoggingProperties {

    private Set<String> maskedHeaders = Set.of("authorization", "cookie");
    private Set<AntPathMatcher> excludedPaths = Set.of();

    public Set<AntPathMatcher> getExcludedPaths() {
        return excludedPaths;
    }

    public void setExcludedPaths(Set<AntPathMatcher> excludedPaths) {
        this.excludedPaths = excludedPaths;
    }

    public Set<String> getMaskedHeaders() {
        return maskedHeaders;
    }

    public void setMaskedHeaders(Set<String> maskedHeaders) {
        this.maskedHeaders = maskedHeaders;
    }
}
