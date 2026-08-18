package com.example.product.controller;

import com.example.product.dto.common.request.LoginRequest;
import com.example.product.dto.common.response.ApiResponse;
import com.example.product.dto.common.response.LoginResponse;
import com.example.product.service.AuthService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(
            @Valid @RequestBody LoginRequest request
    ) {

        LoginResponse response =
                authService.login(request);

        return ResponseEntity.ok(
                ApiResponse.success(response)
        );
    }
}