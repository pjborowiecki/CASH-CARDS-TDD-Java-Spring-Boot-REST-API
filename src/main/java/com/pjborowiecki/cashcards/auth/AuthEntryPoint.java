package com.pjborowiecki.cashcards.auth;

import java.net.URI;
import java.io.IOException;
import org.springframework.stereotype.Component;
import org.springframework.http.ProblemDetail;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.oauth2.jwt.JwtValidationException;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletException;

@Component
public class AuthEntryPoint implements AuthenticationEntryPoint {
    private final AuthenticationEntryPoint delegate = new BearerTokenAuthenticationEntryPoint();

    private final ObjectMapper objectMapper;

    public AuthEntryPoint(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {

        this.delegate.commence(request, response, authException);

        if (authException.getCause() instanceof JwtValidationException validation) {
            ProblemDetail problemDetail = ProblemDetail.forStatus(401);

            problemDetail.setType(URI.create("https://tools.ietf.org/html/rfc6750#section-3.1"));
            problemDetail.setTitle("Invalid Token");
            problemDetail.setProperty("errors", validation.getErrors());

            this.objectMapper.writeValue(response.getWriter(), problemDetail);
        }
    }
}
