package com.calisquest.api.user.service;

import com.calisquest.api.user.dto.response.UserResponse;

import java.util.UUID;

public interface UserService {

    UserResponse getCurrentUser(UUID userId);
}