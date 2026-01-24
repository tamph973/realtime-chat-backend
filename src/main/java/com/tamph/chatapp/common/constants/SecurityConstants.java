package com.tamph.chatapp.common.constants;

public class SecurityConstants {
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String TOKEN_TYPE = "JWT";

    // Public endpoints - no authentication required
    public static final String[] PUBLIC_URLS = {
            "/api/v1/auth/**",
            "/api/health",
            "/api/test",
            "/ws/**",
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html"
    };

    private SecurityConstants() {
        // Private constructor to prevent instantiation
    }
}
