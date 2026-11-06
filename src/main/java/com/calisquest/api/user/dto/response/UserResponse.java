package com.calisquest.api.user.dto.response;

import java.util.UUID;

import com.calisquest.api.entity.enums.UserRole;

public record UserResponse(
        UUID id,
        String email,
        UserRole role) {
}