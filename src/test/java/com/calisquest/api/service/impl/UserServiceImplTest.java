
package com.calisquest.api.service.impl;

import com.calisquest.api.dto.request.RegisterRequest;
import com.calisquest.api.entity.User;
import com.calisquest.api.entity.enums.UserRole;
import com.calisquest.api.exception.AppException;
import com.calisquest.api.exception.ErrorCode;
import com.calisquest.api.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void register_shouldSaveUser_whenEmailDoesNotExist() {
        // Arrange
        RegisterRequest request = new RegisterRequest(
                "minh@example.com",
                "MyPassword123");

        when(userRepository.existsByEmail("minh@example.com"))
                .thenReturn(false);

        when(passwordEncoder.encode("MyPassword123"))
                .thenReturn("hashed-password");

        // Act
        userService.register(request);

        // Assert
        verify(userRepository).save(argThat(user -> user.getEmail().equals("minh@example.com")
                && user.getPassword().equals("hashed-password")
                && user.getRole() == UserRole.USER));
    }

    @Test
    void register_shouldThrowException_whenEmailAlreadyExists() {
        // Arrange
        RegisterRequest request = new RegisterRequest("minh@example.com", "MyPassword123");

        when(userRepository.existsByEmail("minh@example.com"))
                .thenReturn(true);

        // Act & Assert
        assertThatThrownBy(() -> userService.register(request))
                .isInstanceOf(AppException.class);

        verify(userRepository, never()).save(any(User.class));
    }

}
