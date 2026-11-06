package com.calisquest.api.auth.service.impl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.calisquest.api.auth.dto.request.LoginRequest;
import com.calisquest.api.auth.dto.request.RegisterRequest;
import com.calisquest.api.auth.dto.response.AuthResponse;
import com.calisquest.api.auth.service.AuthService;
import com.calisquest.api.entity.User;
import com.calisquest.api.exception.AppException;
import com.calisquest.api.exception.ErrorCode;
import com.calisquest.api.repository.UserRepository;
import com.calisquest.api.security.CustomUserDetails;
import com.calisquest.api.security.JwtService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    public boolean register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.email())) {
            throw new AppException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }

        User user = User.builder()
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .build();

        userRepository.save(user);

        return true;
    }

    @Override
    public AuthResponse login(LoginRequest request) {

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.email(),
                            request.password()));

            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

            String accessToken = jwtService.generateToken(userDetails);

            String role = userDetails.getAuthorities()
                    .iterator()
                    .next()
                    .getAuthority();

            return new AuthResponse(
                    userDetails.getId(),
                    userDetails.getUsername(),
                    role,
                    accessToken,
                    "Bearer",
                    "Login successful");

        } catch (AuthenticationException exception) {
            throw new AppException(ErrorCode.INVALID_CREDENTIALS);
        }
    }
}