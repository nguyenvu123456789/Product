package com.example.product.service.impl;

import com.example.product.dto.common.request.LoginRequest;
import com.example.product.dto.common.response.LoginResponse;
import com.example.product.entity.User;
import com.example.product.repository.UserRepository;
import com.example.product.security.JwtService;
import com.example.product.service.AuthService;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthServiceImpl(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Override
    @Transactional(readOnly = true)
    public LoginResponse login(LoginRequest request) {

        User user = userRepository
                .findByUsername(request.getUsername())
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "auth.login.failed"
                        )
                );

        if (!user.getEnabled()) {
            throw new IllegalArgumentException(
                    "auth.account.disabled"
            );
        }

        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword()
        )) {
            throw new IllegalArgumentException(
                    "auth.login.failed"
            );
        }

        String token = jwtService.generateToken(
                user.getUsername(),
                user.getRole()
        );

        return new LoginResponse(
                token,
                "Bearer",
                user.getUsername(),
                user.getRole()
        );
    }
}