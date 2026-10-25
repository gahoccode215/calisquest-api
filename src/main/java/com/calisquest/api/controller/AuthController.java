package com.calisquest.api.controller;

import com.calisquest.api.common.response.ApiResponseDTO;
import com.calisquest.api.dto.request.RegisterRequest;
import com.calisquest.api.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponseDTO<Boolean>> register(
            @Valid @RequestBody RegisterRequest request) {

        userService.register(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponseDTO.success(true));
    }
}