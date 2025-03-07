package com.kasyus.userservice.audit;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Component("auditorAwareImpl")
public class AuditorAwareImpl implements AuditorAware<String> {

    private static final ThreadLocal<String> currentAuditor = new ThreadLocal<>();

    @Override
    public Optional<String> getCurrentAuditor() {
        String auditor = currentAuditor.get();
        return Optional.ofNullable(auditor != null ? auditor : "system");
    }


    public WebFilter auditorFilter() {
        return (ServerWebExchange exchange, WebFilterChain chain) -> {
            String email = exchange.getRequest().getHeaders().getFirst("X-User-Email");
            if (email != null) {
                currentAuditor.set(email);
            }
            return chain.filter(exchange)
                    .doFinally(signal -> currentAuditor.remove());
        };
    }
}