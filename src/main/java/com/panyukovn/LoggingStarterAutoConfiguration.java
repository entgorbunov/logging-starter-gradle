package com.panyukovn;

import com.panyukovn.aspect.LogExecutionAspect;
import com.panyukovn.webfilter.WebLoggingFilter;
import com.panyukovn.webfilter.WebLoggingRequestBodyAdvice;
import com.panyukovn.webfilter.properties.WebLoggingProperties;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
@EnableConfigurationProperties(WebLoggingProperties.class)
@ConditionalOnProperty(prefix = "logging", value = "enabled", havingValue = "true", matchIfMissing = true)
public class LoggingStarterAutoConfiguration {

    @Bean
    @ConditionalOnProperty(prefix = "logging", value = "log-exec-time", havingValue = "true")
    public LogExecutionAspect logExecutionAspect() {
        return new LogExecutionAspect();
    }

    @Bean
    @ConditionalOnProperty(prefix = "logging.web-logging", value = "enabled", havingValue = "true", matchIfMissing = true)
    public WebLoggingFilter webLoggingFilter(WebLoggingProperties webLoggingProperties) {
        return new WebLoggingFilter(
            webLoggingProperties.getMaskedHeaders(),
            webLoggingProperties.getExcludedPaths());
    }

    @Bean
    @ConditionalOnProperty(prefix = "logging.web-logging", value = { "enabled", "log-body" }, havingValue = "true", matchIfMissing = true)
    WebLoggingRequestBodyAdvice webLoggingRequestBodyAdvice(WebLoggingProperties webLoggingProperties) {
        return new WebLoggingRequestBodyAdvice(webLoggingProperties.getExcludedPaths());
    }
}
