package com.calisquest.api.auth.service;

import com.calisquest.api.auth.dto.request.LoginRequest;
import com.calisquest.api.auth.dto.request.RegisterRequest;
import com.calisquest.api.auth.dto.response.AuthResponse;

public interface AuthService {

    boolean register(RegisterRequest request);

    AuthResponse login(LoginRequest request);

}