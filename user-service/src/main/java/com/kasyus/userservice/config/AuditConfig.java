package com.kasyus.userservice.config;

import com.kasyus.userservice.audit.AuditorAwareImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.server.WebFilter;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAwareImpl")
public class AuditConfig {

    private final AuditorAwareImpl auditorAwareImpl;

    public AuditConfig(AuditorAwareImpl auditorAwareImpl) {
        this.auditorAwareImpl = auditorAwareImpl;
    }

    @Bean
    public WebFilter auditorFilter() {
        return auditorAwareImpl.auditorFilter();
    }
}