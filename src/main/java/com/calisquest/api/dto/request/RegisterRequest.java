package com.calisquest.api.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @NotBlank(message = "{validation.email.required}") @Email(message = "{validation.email.invalid}") @Size(max = 255) String email,

        @NotBlank(message = "{validation.password.required}") @Size(min = 8, max = 72, message = "{validation.password.size}") String password) {
}