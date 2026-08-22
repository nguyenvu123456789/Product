package com.example.product.dto.common.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginRequest {

    @NotBlank(message = "{auth.username.notblank}")
    private String username;

    @NotBlank(message = "{auth.password.notblank}")
    private String password;

    public LoginRequest() {
    }

}