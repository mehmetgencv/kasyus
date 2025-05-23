package com.kasyus.apigateway.config;

import com.kasyus.apigateway.dto.responses.TokenValidationResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
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
    private static final List<String> PUBLIC_PATHS = List.of(
            "/auth-service/**", "/actuator/**", "/swagger-ui/**",
            "/v3/api-docs/**", "/webjars/**", "/favicon.ico"
    );
    private static final List<String> ADMIN_REQUIRED_SERVICE_PREFIXES = List.of(
            "/product-service/api/v1"
    );
    private static final List<String> SELLER_REQUIRED_SERVICE_PREFIXES = List.of(
            "/product-service/api/v1"
    );
    private final WebClient webClient;

    public SecurityConfig(WebClient webClient) {
        this.webClient = webClient;
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http.csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers("/auth-service/**").permitAll()
                        .pathMatchers("/actuator/**").permitAll()
                        .pathMatchers(HttpMethod.GET, "/product-service/api/v1/products/**").permitAll()
                        //.pathMatchers("/product-service/api/v1/products/**").permitAll()
                        .pathMatchers(HttpMethod.GET, "/product-service/api/v1/categories/**").permitAll()
                        .pathMatchers(HttpMethod.POST,
                                SELLER_REQUIRED_SERVICE_PREFIXES.stream()
                                        .map(prefix -> prefix + "/**")
                                        .toArray(String[]::new)).hasAnyRole("ADMIN", "SELLER")
                        .pathMatchers(HttpMethod.PUT,
                                "/product-service/api/v1/products/**").hasAnyRole("ADMIN", "SELLER")
                        .pathMatchers(HttpMethod.DELETE,
                                ADMIN_REQUIRED_SERVICE_PREFIXES.stream()
                                        .map(prefix -> prefix + "/**")
                                        .toArray(String[]::new)).hasAnyRole("ADMIN", "SELLER")
                        .pathMatchers("/swagger-ui/**", "/v3/api-docs/**", "/webjars/**", "/favicon.ico").permitAll()
                        .anyExchange().authenticated()
                )
                .cors(cors -> cors.configurationSource(request -> {
                    CorsConfiguration config = new CorsConfiguration();
                    config.setAllowedOrigins(List.of("http://localhost:3000"));
                    config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
                    config.setAllowedHeaders(List.of("*"));
                    config.setExposedHeaders(List.of("*"));
                    config.setAllowCredentials(true);
                    return config;
                }))
                .addFilterAt(authenticationFilter(), SecurityWebFiltersOrder.AUTHENTICATION);

        return http.build();
    }

    @Bean
    public WebFilter authenticationFilter() {
        return (ServerWebExchange exchange, WebFilterChain chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            String path = request.getURI().getPath();

            HttpMethod method = request.getMethod();

            if (method == HttpMethod.OPTIONS) {
                return chain.filter(exchange);
            }

            if (method == HttpMethod.GET &&
                    (path.startsWith("/product-service/api/v1/products") ||
                            path.startsWith("/product-service/api/v1/categories"))) {
                logger.info("GET request for path {} is permitted, skipping authentication", path);
                return chain.filter(exchange);
            }

            if (path.startsWith("/auth-service") ||
                    path.startsWith("/actuator") ||
                    path.startsWith("/v3/api-docs") ||
                    path.startsWith("/swagger-ui") ||
                    path.startsWith("/webjars/") ||
                    path.equals("/favicon.ico")) {
                return chain.filter(exchange);
            }

            if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                return Mono.error(new RuntimeException("Authorization header eksik"));
            }

            String token = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION).replace("Bearer ", "");
            System.out.println("WebClient requesting to: " + AUTH_SERVICE_VALIDATE_URL);
            return webClient.post()
                    .uri(AUTH_SERVICE_VALIDATE_URL)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .retrieve()
                    .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                            response -> Mono.error(new RuntimeException("Token geçersiz")))
                    .bodyToMono(TokenValidationResponse.class)
                    .flatMap(validationResponse -> {
                        logger.info("Token validated, userId: {}", validationResponse.userId());
                        if (!validationResponse.valid()) {
                            return Mono.error(new RuntimeException("Token is not valid"));
                        }
                        List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(validationResponse.roles());
                        UsernamePasswordAuthenticationToken auth =
                                new UsernamePasswordAuthenticationToken(validationResponse.email(), null, authorities);
                        if ((method == HttpMethod.POST || method == HttpMethod.PUT || method == HttpMethod.DELETE) &&
                                SELLER_REQUIRED_SERVICE_PREFIXES.stream().anyMatch(path::startsWith)) {
                            if (!validationResponse.roles().contains("ROLE_ADMIN") &&
                                    !validationResponse.roles().contains("ROLE_SELLER")) {
                                return Mono.error(new RuntimeException("You are not authorized to perform this action"));
                            }
                        }

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
