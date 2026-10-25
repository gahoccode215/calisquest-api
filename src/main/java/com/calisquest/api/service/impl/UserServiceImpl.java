package com.calisquest.api.service.impl;

import com.calisquest.api.dto.request.RegisterRequest;
import com.calisquest.api.entity.User;
import com.calisquest.api.entity.enums.UserRole;
import com.calisquest.api.exception.AppException;
import com.calisquest.api.exception.ErrorCode;
import com.calisquest.api.repository.UserRepository;
import com.calisquest.api.service.UserService;

import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void register(RegisterRequest request) {
        String email = request.email()
                .trim()
                .toLowerCase(Locale.ROOT);

        if (userRepository.existsByEmail(email)) {
            throw new AppException(ErrorCode.USER_ALREADY_EXISTS);
        }

        User user = User.builder()
                .email(email)
                .password(passwordEncoder.encode(request.password()))
                .role(UserRole.USER)
                .build();

        userRepository.save(user);
    }
}