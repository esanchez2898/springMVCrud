package org.example.security;

import java.io.IOException;
import java.io.Serializable;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Custom entry point implementation for Spring Security.
 * Intercepts unauthorized access attempts (unauthenticated users trying to
 * access protected resources) and returns an HTTP 401 Unauthorized response
 * instead of redirecting to a login page.
 */
@Component
public class JwtAuthenticationEntryPoint
        implements AuthenticationEntryPoint, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Invoked whenever an unauthenticated user attempts to access a secured
     * HTTP resource.
     * Returns an HTTP 401 Unauthorized error code along with an error message.
     *
     * @param request HttpServletRequest that resulted in an AuthenticationException.
     * @param response HttpServletResponse used to return the 401 Unauthorized error.
     * @param authException Exception thrown when authentication fails or is missing.
     * @throws IOException if an input/output error occurs while sending the error response.
     * @throws ServletException if a servlet configuration error occurs.
     */
    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException, ServletException {

        // Send an HTTP 401 Unauthorized response to the client.
        response.sendError(
                HttpServletResponse.SC_UNAUTHORIZED,
                "Unauthorized"
        );
    }
}