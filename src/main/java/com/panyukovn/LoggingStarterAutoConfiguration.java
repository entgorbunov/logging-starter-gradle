package com.panyukovn;

import com.panyukovn.aspect.LogExecutionAspect;
import com.panyukovn.service.LoggingSkipService;
import com.panyukovn.webfilter.WebLoggingFilter;
import com.panyukovn.webfilter.WebLoggingRequestBodyAdvice;
import com.panyukovn.properties.WebLoggingProperties;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.util.AntPathMatcher;

import java.util.Set;

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
    @ConditionalOnProperty(prefix = "logging.web-logging", value = {"enabled", "log-body"}, havingValue = "true", matchIfMissing = true)
    public WebLoggingRequestBodyAdvice webLoggingRequestBodyAdvice(WebLoggingProperties webLoggingProperties) {
        return new WebLoggingRequestBodyAdvice(webLoggingProperties.getExcludedPaths());
    }

    @Bean
    public LoggingSkipService loggingSkipService(Set<AntPathMatcher> antPathMatchers) {
        return new LoggingSkipService(antPathMatchers);
    }
}
