package com.kasyus.authservice.bootstrap;

import com.kasyus.authservice.dto.requests.RegisterRequest;
import com.kasyus.authservice.model.enums.UserRole;
import com.kasyus.authservice.service.AuthenticationService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AdminInitializer {

    private final AuthenticationService authenticationService;

    @Value("${app.bootstrap.admin.email}")
    private String email;

    @Value("${app.bootstrap.admin.password}")
    private String password;

    @Value("${app.bootstrap.admin.first-name}")
    private String firstName;

    @Value("${app.bootstrap.admin.last-name}")
    private String lastName;

    @Value("${app.bootstrap.admin.role}")
    private String role;

    public AdminInitializer(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostConstruct
    public void initializeAdminUser() {
        try {
            RegisterRequest request = new RegisterRequest(
                    firstName,
                    lastName,
                    email,
                    password,
                    UserRole.valueOf(role)
            );
            authenticationService.register(request);
            System.out.println("✅ Admin user initialized: " + email);
        }  catch (Exception e) {
            System.err.println("Failed to initialize admin user: " + e.getMessage());
        }
    }
}
