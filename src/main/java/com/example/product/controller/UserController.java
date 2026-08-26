package com.example.product.controller;

import com.example.product.dto.common.response.ApiResponse;
import com.example.product.dto.common.response.PageResponse;
import com.example.product.dto.user.response.UserResponse;
import com.example.product.service.UserService;
import com.example.product.ultis.MessageHelper;
import com.example.product.ultis.paging.PageableUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    private final MessageHelper messageHelper;

    public UserController(UserService userService,
                          PageableUtils pageableUtils,
                          MessageHelper messageHelper) {
        this.userService = userService;
        this.pageableUtils = pageableUtils;
        this.messageHelper = messageHelper;
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
                ApiResponse.of(data, messageHelper.get("user.list.success"))
        );
    }
}