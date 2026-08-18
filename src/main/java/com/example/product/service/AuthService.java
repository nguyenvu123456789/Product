package com.example.product.service;

import com.example.product.dto.common.request.LoginRequest;
import com.example.product.dto.common.response.LoginResponse;

public interface AuthService {

    LoginResponse login(LoginRequest request);
}