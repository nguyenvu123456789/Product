package com.example.product.service.impl;

import com.example.product.dto.user.response.UserResponse;
import com.example.product.entity.User;
import com.example.product.mapper.UserMapper;
import com.example.product.repository.UserEntityManagerRepository;
import com.example.product.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserEntityManagerRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserEntityManagerRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public Page<UserResponse> getEnabledUsers(Pageable pageable) {
        List<User> users = userRepository.findEnabledUsers(pageable);
        long total = userRepository.countEnabledUsers();

        List<UserResponse> content = userMapper.toResponseList(users);

        return new PageImpl<>(content, pageable, total);
    }
}