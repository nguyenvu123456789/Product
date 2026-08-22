package com.example.product.dto.common.response;

public record LoginResponse(String accessToken, String tokenType, String username, String role) {

}