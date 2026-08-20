package com.example.product.mapper;

import com.example.product.dto.user.response.UserResponse;
import com.example.product.entity.User;
import org.mapstruct.Mapper;
import java.util.List;

@Mapper(componentModel= "spring")
public interface UserMapper {
    UserResponse toResponse(User user);
    List<UserResponse> toResponseList(List<User>users);
}
