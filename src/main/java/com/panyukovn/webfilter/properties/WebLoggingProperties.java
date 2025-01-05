package com.panyukovn.webfilter.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Set;

@ConfigurationProperties(prefix = "logging.web-logging")
public class WebLoggingProperties {

    private Set<String> maskedHeaders = Set.of("authorization", "cookie");
    private Set<String> excludedPaths = Set.of();

    public Set<String> getExcludedPaths() {
        return excludedPaths;
    }

    public void setExcludedPaths(Set<String> excludedPaths) {
        this.excludedPaths = excludedPaths;
    }

    public Set<String> getMaskedHeaders() {
        return maskedHeaders;
    }

    public void setMaskedHeaders(Set<String> maskedHeaders) {
        this.maskedHeaders = maskedHeaders;
    }
}
