package org.example.security;

import java.io.IOException;
import java.util.Optional;

import org.example.converter.TempConverter;
import org.example.dto.UserDto;
import org.example.service.UserService;
import org.example.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Custom Spring Security filter that intercepts every HTTP request once to authenticate JWT tokens.
 * Inherits from OncePerRequestFilter to guarantee execution once per request dispatch.
 */
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    /** Service for fetching user details from the database or domain layer. */
    private UserService userService;

    /** Utility component for parsing, validating, and extracting claims from JWTs. */
    private JwtUtil jwtUtil;

    /** Converter utility for mapping UserDto objects to Spring Security UserDetails objects. */
    private TempConverter tempConverter;

    /**
     * Method-level dependency injection for required Spring beans.
     *
     * @param jwtUtil Utility instance for JWT operations.
     * @param tempConverter Converter instance for DTO/Entity transformations.
     * @param userService Service instance for user lookups.
     */
    @Autowired
    private void initialize(
            JwtUtil jwtUtil,
            TempConverter tempConverter,
            UserService userService) {

        this.jwtUtil = jwtUtil;
        this.tempConverter = tempConverter;
        this.userService = userService;
    }

    /**
     * Main filter logic executed for each incoming HTTP request.
     *
     * @param request HttpServletRequest representing the incoming request.
     * @param response HttpServletResponse representing the outgoing response.
     * @param filterChain FilterChain for the remaining security filters.
     * @throws ServletException if a servlet configuration error occurs.
     * @throws IOException if an I/O error occurs during request processing.
     */
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        // 1. Extract the raw JWT token from the Authorization header.
        String token = extractToken(request);

        // 2. If no token exists or the token is invalid, continue the filter chain.
        if (token == null || !jwtUtil.validateToken(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        // 3. Extract the username or email from the token.
        String email = jwtUtil.extractUsername(token);

        // 4. Authenticate only if an email exists and no authentication is set.
        if (email != null
                && SecurityContextHolder.getContext().getAuthentication() == null) {

            setAuthentication(email, token, request);
        }

        // 5. Continue processing the request.
        filterChain.doFilter(request, response);
    }

    /**
     * Extracts the Bearer token from the Authorization header.
     *
     * @param request HttpServletRequest containing the incoming headers.
     * @return JWT token if the header starts with "Bearer ", otherwise null.
     */
    private String extractToken(HttpServletRequest request) {

        return Optional.ofNullable(request.getHeader("Authorization"))
                .filter(header -> header.startsWith("Bearer "))
                .map(header -> header.substring(7))
                .orElse(null);
    }

    /**
     * Loads the user, creates an authentication token, and stores it
     * in the Spring Security context.
     *
     * @param email Username or email extracted from the JWT.
     * @param token Validated JWT token.
     * @param request HttpServletRequest used to obtain request details.
     */
    private void setAuthentication(
            String email,
            String token,
            HttpServletRequest request) {

        // Fetch the user DTO using the email or username.
        //UserDto userDto = userService.getUserByEmailOrUsername(email);
        UserDto userDto = userService.getUserByEmal(email);

        // Convert the DTO to a Spring Security UserDetails implementation.
        //UserDetails userDetails = tempConverter.userDtoToEntity(userDto);
        UserDetails userDetails = tempConverter.dtoToEntity(userDto);

        // Create the authentication token with the user's authorities.
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                        userDetails,
                        token,
                        userDetails.getAuthorities()
                );

        // Attach request details such as the remote IP address and session ID.
        authentication.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(request)
        );

        // Store the authentication in the security context.
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
