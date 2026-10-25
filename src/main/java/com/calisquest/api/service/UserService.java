package com.calisquest.api.service;

import com.calisquest.api.dto.request.RegisterRequest;

public interface UserService {

    void register(RegisterRequest request);
}