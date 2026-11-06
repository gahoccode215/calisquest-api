package com.calisquest.api.user.controller;

import com.calisquest.api.common.response.ApiResponseDTO;
import com.calisquest.api.security.CustomUserDetails;
import com.calisquest.api.user.dto.response.UserResponse;
import com.calisquest.api.user.service.UserService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<ApiResponseDTO<UserResponse>> getCurrentUser(
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        UserResponse response = userService.getCurrentUser(
                userDetails.getId());

        return ResponseEntity.ok(ApiResponseDTO.success(response));
    }
}