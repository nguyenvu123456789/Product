package com.example.product.controller;

import com.example.product.dto.common.response.ApiResponse;
import com.example.product.dto.common.response.PageResponse;
import com.example.product.dto.user.response.UserResponse;
import com.example.product.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.example.product.ultis.paging.PageableUtils;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final PageableUtils pageableUtils;

    public UserController(UserService userService, PageableUtils pageableUtils) {
        this.userService = userService;
        this.pageableUtils = pageableUtils;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<UserResponse>>> getEnabledUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        Pageable pageable = pageableUtils.build(page, size, sortBy, direction);
        Page<UserResponse> userPage = userService.getEnabledUsers(pageable);
        PageResponse<UserResponse> data = PageResponse.from(userPage);

        return ResponseEntity.ok(
                ApiResponse.success(
                        data,
                        "user.list.success"
                )
        );
    }
}