package com.example.product.controller;

import com.example.product.dto.common.request.LoginRequest;
import com.example.product.dto.common.response.ApiResponse;
import com.example.product.dto.common.response.LoginResponse;
import com.example.product.service.AuthService;
import com.example.product.ultis.MessageHelper;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final MessageHelper messageHelper;

    public AuthController(AuthService authService, MessageHelper messageHelper) {
        this.authService = authService;
        this.messageHelper = messageHelper;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(
            @Valid @RequestBody LoginRequest request) {

        LoginResponse response = authService.login(request);
        return ResponseEntity.ok(
                ApiResponse.of(response, messageHelper.get("auth.login.success"))
        );
    }
}