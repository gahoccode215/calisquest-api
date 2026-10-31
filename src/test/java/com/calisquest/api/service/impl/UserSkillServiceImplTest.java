
package com.calisquest.api.service.impl;

import com.calisquest.api.entity.Skill;
import com.calisquest.api.entity.User;
import com.calisquest.api.entity.UserSkill;
import com.calisquest.api.entity.enums.SkillDifficulty;
import com.calisquest.api.entity.enums.SkillProgressStatus;
import com.calisquest.api.exception.AppException;
import com.calisquest.api.exception.ErrorCode;
import com.calisquest.api.repository.SkillRepository;
import com.calisquest.api.repository.UserRepository;
import com.calisquest.api.repository.UserSkillRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserSkillServiceImplTest {

    @Mock
    private UserSkillRepository userSkillRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private SkillRepository skillRepository;

    @InjectMocks
    private UserSkillServiceImpl userSkillService;

    @Test
    void addSkillToUser_shouldSaveUserSkill_whenUserAndSkillExist() {
        // Arrange
        UUID userId = UUID.randomUUID();
        UUID skillId = UUID.randomUUID();

        User user = User.builder().id(userId).build();
        Skill skill = Skill.builder()
                .id(skillId)
                .name("Planche")
                .difficulty(SkillDifficulty.ADVANCED)
                .build();

        when(userRepository.findById(userId))
                .thenReturn(Optional.of(user));
        when(skillRepository.findById(skillId))
                .thenReturn(Optional.of(skill));
        when(userSkillRepository.existsByUser_IdAndSkill_Id(userId, skillId))
                .thenReturn(false);
        when(userSkillRepository.save(any(UserSkill.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        UserSkill result = userSkillService.addSkillToUser(userId, skillId);

        // Assert
        assertNotNull(result);
        assertEquals(user, result.getUser());
        assertEquals(skill, result.getSkill());
        assertEquals(0, result.getProgress());
        assertEquals(
                SkillProgressStatus.NOT_STARTED,
                result.getStatus());

        verify(userSkillRepository).save(any(UserSkill.class));
    }

    @Test
    void addSkillToUser_shouldThrowException_whenUserDoesNotExist() {
        // Arrange
        UUID userId = UUID.randomUUID();
        UUID skillId = UUID.randomUUID();

        when(userRepository.findById(userId))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(
                AppException.class,
                () -> userSkillService.addSkillToUser(userId, skillId));

        verify(skillRepository, never()).findById(any());
        verify(userSkillRepository, never()).save(any());
    }

    @Test
    void addSkillToUser_shouldThrowException_whenSkillDoesNotExist() {
        // Arrange
        UUID userId = UUID.randomUUID();
        UUID skillId = UUID.randomUUID();

        when(userRepository.findById(userId))
                .thenReturn(Optional.of(User.builder().id(userId).build()));
        when(skillRepository.findById(skillId))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(
                AppException.class,
                () -> userSkillService.addSkillToUser(userId, skillId));

        verify(userSkillRepository, never()).save(any());
    }

    @Test
    void addSkillToUser_shouldThrowException_whenSkillAlreadyExists() {
        // Arrange
        UUID userId = UUID.randomUUID();
        UUID skillId = UUID.randomUUID();

        when(userRepository.findById(userId))
                .thenReturn(Optional.of(User.builder().id(userId).build()));
        when(skillRepository.findById(skillId))
                .thenReturn(Optional.of(Skill.builder().id(skillId).build()));
        when(userSkillRepository.existsByUser_IdAndSkill_Id(userId, skillId))
                .thenReturn(true);

        // Act & Assert
        assertThrows(
                AppException.class,
                () -> userSkillService.addSkillToUser(userId, skillId));

        verify(userSkillRepository, never()).save(any());
    }

    @Test
    void getUserSkills_shouldReturnSkills_whenUserExists() {
        // Arrange
        UUID userId = UUID.randomUUID();

        UserSkill userSkill = UserSkill.builder()
                .user(User.builder().id(userId).build())
                .skill(Skill.builder().name("Planche").build())
                .progress(30)
                .status(SkillProgressStatus.IN_PROGRESS)
                .build();

        when(userRepository.existsById(userId)).thenReturn(true);
        when(userSkillRepository.findAllByUser_Id(userId))
                .thenReturn(List.of(userSkill));

        // Act
        List<UserSkill> result = userSkillService.getUserSkills(userId);

        // Assert
        assertEquals(1, result.size());
        assertEquals(30, result.get(0).getProgress());

        verify(userSkillRepository).findAllByUser_Id(userId);
    }

    @Test
    void getUserSkills_shouldThrowException_whenUserDoesNotExist() {
        // Arrange
        UUID userId = UUID.randomUUID();

        when(userRepository.existsById(userId)).thenReturn(false);

        // Act & Assert
        assertThrows(
                AppException.class,
                () -> userSkillService.getUserSkills(userId));

        verify(userSkillRepository, never()).findAllByUser_Id(any());
    }

    @Test
    void updateProgress_shouldSetInProgress_whenProgressIsBetweenZeroAndHundred() {
        // Arrange
        UUID userId = UUID.randomUUID();
        UUID skillId = UUID.randomUUID();

        UserSkill userSkill = UserSkill.builder()
                .progress(0)
                .status(SkillProgressStatus.NOT_STARTED)
                .build();

        when(userSkillRepository.findByUserIdAndSkillId(userId, skillId))
                .thenReturn(Optional.of(userSkill));
        when(userSkillRepository.save(userSkill)).thenReturn(userSkill);

        // Act
        UserSkill result = userSkillService.updateProgress(userId, skillId, 50);

        // Assert
        assertEquals(50, result.getProgress());
        assertEquals(SkillProgressStatus.IN_PROGRESS, result.getStatus());

        verify(userSkillRepository).save(userSkill);
    }

    @Test
    void updateProgress_shouldSetCompleted_whenProgressIsOneHundred() {
        // Arrange
        UUID userId = UUID.randomUUID();
        UUID skillId = UUID.randomUUID();

        UserSkill userSkill = UserSkill.builder().build();

        when(userSkillRepository.findByUserIdAndSkillId(userId, skillId))
                .thenReturn(Optional.of(userSkill));
        when(userSkillRepository.save(userSkill)).thenReturn(userSkill);

        // Act
        UserSkill result = userSkillService.updateProgress(userId, skillId, 100);

        // Assert
        assertEquals(100, result.getProgress());
        assertEquals(SkillProgressStatus.COMPLETED, result.getStatus());
    }

    @Test
    void updateProgress_shouldSetNotStarted_whenProgressIsZero() {
        // Arrange
        UUID userId = UUID.randomUUID();
        UUID skillId = UUID.randomUUID();

        UserSkill userSkill = UserSkill.builder().build();

        when(userSkillRepository.findByUserIdAndSkillId(userId, skillId))
                .thenReturn(Optional.of(userSkill));
        when(userSkillRepository.save(userSkill)).thenReturn(userSkill);

        // Act
        UserSkill result = userSkillService.updateProgress(userId, skillId, 0);

        // Assert
        assertEquals(0, result.getProgress());
        assertEquals(SkillProgressStatus.NOT_STARTED, result.getStatus());
    }

    @Test
    void updateProgress_shouldThrowException_whenProgressIsInvalid() {
        // Arrange
        UUID userId = UUID.randomUUID();
        UUID skillId = UUID.randomUUID();

        // Act & Assert
        assertAll(
                () -> assertThrows(
                        AppException.class,
                        () -> userSkillService.updateProgress(userId, skillId, -1)),
                () -> assertThrows(
                        AppException.class,
                        () -> userSkillService.updateProgress(userId, skillId, 101)),
                () -> assertThrows(
                        AppException.class,
                        () -> userSkillService.updateProgress(userId, skillId, null)));

        verify(userSkillRepository, never())
                .findByUserIdAndSkillId(any(), any());
        verify(userSkillRepository, never()).save(any());
    }

    @Test
    void updateProgress_shouldThrowException_whenUserSkillDoesNotExist() {
        // Arrange
        UUID userId = UUID.randomUUID();
        UUID skillId = UUID.randomUUID();

        when(userSkillRepository.findByUserIdAndSkillId(userId, skillId))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(
                AppException.class,
                () -> userSkillService.updateProgress(userId, skillId, 50));

        verify(userSkillRepository, never()).save(any());
    }
}
