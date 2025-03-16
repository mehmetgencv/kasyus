package com.kasyus.cartservice.audit;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component("auditorAwareImpl")
public class AuditorAwareImpl implements AuditorAware<String> {

    // TODO: Change this method
    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of("kasyus ADMIN");
    }
}
