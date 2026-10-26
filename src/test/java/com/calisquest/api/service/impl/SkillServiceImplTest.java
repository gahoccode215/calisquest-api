
package com.calisquest.api.service.impl;

import com.calisquest.api.entity.Skill;
import com.calisquest.api.entity.enums.SkillDifficulty;
import com.calisquest.api.exception.AppException;
import com.calisquest.api.repository.SkillRepository;
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
class SkillServiceImplTest {

    @Mock
    private SkillRepository skillRepository;

    @InjectMocks
    private SkillServiceImpl skillService;

    @Test
    void getAllSkills_shouldReturnAllSkills() {
        // Arrange
        Skill pushUp = Skill.builder()
                .name("Push-up")
                .difficulty(SkillDifficulty.BEGINNER)
                .build();

        Skill muscleUp = Skill.builder()
                .name("Muscle-up")
                .difficulty(SkillDifficulty.INTERMEDIATE)
                .build();

        when(skillRepository.findAll())
                .thenReturn(List.of(pushUp, muscleUp));

        // Act
        List<Skill> result = skillService.getAllSkills();

        // Assert
        assertEquals(2, result.size());
        assertEquals("Push-up", result.get(0).getName());
        assertEquals("Muscle-up", result.get(1).getName());

        verify(skillRepository, times(1)).findAll();
    }

    @Test
    void getSkillById_shouldReturnSkill_whenSkillExists() {
        // Arrange
        UUID id = UUID.randomUUID();

        Skill skill = Skill.builder()
                .id(id)
                .name("Planche")
                .difficulty(SkillDifficulty.ADVANCED)
                .build();

        when(skillRepository.findById(id))
                .thenReturn(Optional.of(skill));

        // Act
        Skill result = skillService.getSkillById(id);

        // Assert
        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals("Planche", result.getName());

        verify(skillRepository, times(1)).findById(id);
    }

    @Test
    void getSkillById_shouldThrowException_whenSkillDoesNotExist() {
        // Arrange
        UUID id = UUID.randomUUID();

        when(skillRepository.findById(id))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(
                AppException.class,
                () -> skillService.getSkillById(id));

        verify(skillRepository, times(1)).findById(id);
    }
}
