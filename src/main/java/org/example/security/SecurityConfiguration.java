package org.example.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Main Spring Security configuration class.
 * Configures HTTP security rules, CSRF handling, session management,
 * JWT filter integration, custom entry points, and authentication beans.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfiguration {

    /** Custom filter used to extract and validate JWT tokens. */
    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    /** Handles HTTP 401 Unauthorized responses. */
    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    /**
     * Configures the primary SecurityFilterChain bean.
     *
     * @param http HttpSecurity builder used to configure security.
     * @return Configured SecurityFilterChain.
     * @throws Exception if security configuration fails.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        return http
                // Disable CSRF for stateless JWT authentication.
                .csrf(csrf -> csrf.disable())

                // Do not create or use HTTP sessions.
                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )

                // Handle unauthenticated access to protected resources.
                .exceptionHandling(exception ->
                        exception.authenticationEntryPoint(
                                jwtAuthenticationEntryPoint
                        )
                )

                // Configure endpoint authorization rules.
                .authorizeHttpRequests(authorize -> authorize

                        // Accessible only to unauthenticated users.
                        .requestMatchers(
                                "/api/user/login",
                                "/api/auth/requestPasswordChange/**",
                                "/api/customer/register"
                        ).anonymous()

                        // Publicly accessible endpoints.
                        .requestMatchers(
                                "/api/health",
                                "/prometheus",
                                "/api/categories/**",
                                "/api/products/**"
                        ).permitAll()

                        // All other endpoints require authentication.
                        .anyRequest().authenticated()
                )

                // Execute the JWT filter before the standard authentication filter.
                .addFilterBefore(
                        jwtRequestFilter,
                        UsernamePasswordAuthenticationFilter.class
                )

                // Build the security filter chain.
                .build();
    }

    /**
     * Configures the AuthenticationManager used to authenticate credentials.
     *
     * @param userDetailsService Service used to retrieve user details.
     * @param passwordEncoder Encoder used to verify passwords.
     * @return Configured AuthenticationManager.
     */
    @Bean
    public AuthenticationManager authenticationManager(
            UserDetailsService userDetailsService,
            BCryptPasswordEncoder passwordEncoder) {

        // Configure authentication using the application's user service.
        DaoAuthenticationProvider authProvider =
                new DaoAuthenticationProvider(userDetailsService);

        // Configure password verification.
        authProvider.setPasswordEncoder(passwordEncoder);

        // Return the authentication manager.
        return new ProviderManager(authProvider);
    }

    /**
     * Configures the BCrypt password encoder.
     *
     * @return BCryptPasswordEncoder instance.
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
