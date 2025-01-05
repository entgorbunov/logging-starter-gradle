package com.panyukovn.webfilter.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashSet;
import java.util.Set;

@ConfigurationProperties(prefix = "logging.web-logging")
public class WebLoggingProperties {

    private Set<String> maskedHeaders = new HashSet<>();

    public WebLoggingProperties() {
        this.maskedHeaders.add("authorization");
        this.maskedHeaders.add("cookie");
    }

    public Set<String> getMaskedHeaders() {
        return maskedHeaders;
    }

    public void setMaskedHeaders(Set<String> maskedHeaders) {
        this.maskedHeaders = maskedHeaders;
    }
}
