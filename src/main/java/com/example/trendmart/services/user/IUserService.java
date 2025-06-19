package com.example.trendmart.services.user;

import com.example.trendmart.dtos.UserDTO;
import com.example.trendmart.requests.CreateUserRequest;
import com.example.trendmart.requests.UserUpdateRequest;
import com.example.trendmart.entities.User;

public interface IUserService {

    User getUserById(Long userId);
    User createUser(CreateUserRequest request);
    User updateUser(UserUpdateRequest request, Long userId);
    void deleteUser(Long userId);

    UserDTO convertUserToDto(User user);
}

