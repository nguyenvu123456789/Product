package com.example.product.service;

import com.example.product.dto.user.response.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    Page<UserResponse> getEnabledUsers(Pageable pageable);
}