package com.example.product.repository;

import com.example.product.entity.User;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserEntityManagerRepository {

    List<User> findEnabledUsers(Pageable pageable);

    long countEnabledUsers();
}