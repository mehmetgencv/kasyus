package com.kasyus.apigateway.config;

import com.kasyus.apigateway.dto.responses.TokenValidationResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.List;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {
    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    private static final String AUTH_SERVICE_VALIDATE_URL =  "/api/v1/auth/validate";

    private final WebClient webClient;

    public SecurityConfig(WebClient webClient) {
        this.webClient = webClient;
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http.csrf(ServerHttpSecurity.CsrfSpec::disable)
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers("/auth-service/**").permitAll()
                        .pathMatchers("/actuator/**").permitAll()
                        .pathMatchers("/swagger-ui/**", "/v3/api-docs/**", "/webjars/**", "/favicon.ico").permitAll()
                        .anyExchange().authenticated()
                )
                .addFilterAt(authenticationFilter(), SecurityWebFiltersOrder.AUTHENTICATION);

        return http.build();
    }

    @Bean
    public WebFilter authenticationFilter() {
        return (ServerWebExchange exchange, WebFilterChain chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            String path = request.getURI().getPath();

            // Eğer istek Auth Service'e login, register gibi public endpoint'lere gidiyorsa filtreleme yapma
            if (path.startsWith("/auth-service") ||
                    path.startsWith("/actuator") ||
                    path.startsWith("/v3/api-docs") ||
                    path.startsWith("/swagger-ui") ||
                    path.startsWith("/webjars/") ||
                    path.equals("/favicon.ico")) {
                return chain.filter(exchange);
            }

            // Authorization header kontrolü
            if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                return Mono.error(new RuntimeException("Authorization header eksik"));
            }

            String token = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION).replace("Bearer ", "");
            System.out.println("WebClient requesting to: " + AUTH_SERVICE_VALIDATE_URL);
            return webClient.post()
                    .uri(AUTH_SERVICE_VALIDATE_URL) // Auth Service üzerinden doğrulama
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .retrieve()
                    .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                            response -> Mono.error(new RuntimeException("Token geçersiz")))
                    .bodyToMono(TokenValidationResponse.class)
                    .flatMap(validationResponse -> {
                        logger.info("Token validated, userId: {}", validationResponse.userId());
                        if (!validationResponse.valid()) {
                            return Mono.error(new RuntimeException("Token geçersiz"));
                        }
                        List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(validationResponse.roles());
                        UsernamePasswordAuthenticationToken auth =
                                new UsernamePasswordAuthenticationToken(validationResponse.email(), null, authorities);
                        return chain.filter(exchange.mutate()
                                        .request(exchange.getRequest().mutate()
                                                .header("X-User-Email", validationResponse.email())
                                                .header("X-User-Id", validationResponse.userId())
                                                .header("X-User-Roles", String.join(",", validationResponse.roles()))
                                                .build())
                                        .build())
                                .contextWrite(ReactiveSecurityContextHolder.withAuthentication(auth));
                    });
        };
    }

}
