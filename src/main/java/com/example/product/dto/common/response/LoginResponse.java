package com.example.product.dto.common.response;

public class LoginResponse {

    private String accessToken;
    private String tokenType;
    private String username;
    private String role;

    public LoginResponse(
            String accessToken,
            String tokenType,
            String username,
            String role
    ) {
        this.accessToken = accessToken;
        this.tokenType = tokenType;
        this.username = username;
        this.role = role;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }
}