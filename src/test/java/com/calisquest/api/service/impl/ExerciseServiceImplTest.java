package com.calisquest.api.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.calisquest.api.entity.Exercise;
import com.calisquest.api.exception.AppException;
import com.calisquest.api.exception.ErrorCode;
import com.calisquest.api.repository.ExerciseRepository;

@ExtendWith(MockitoExtension.class)
public class ExerciseServiceImplTest {

    @Mock
    private ExerciseRepository exerciseRepository;

    @InjectMocks
    private ExerciseServiceImpl exerciseService;

    @Test
    void getAllExercises_shouldReturnExercises() {
        List<Exercise> exercises = List.of(
                new Exercise(),
                new Exercise());

        when(exerciseRepository.findAll()).thenReturn(exercises);

        List<Exercise> result = exerciseService.getAllExercises();

        assertEquals(2, result.size());
        assertEquals(exercises, result);
        verify(exerciseRepository).findAll();

    }

    @Test
    void getExerciseById_shouldReturnExercise() {
        UUID userId = UUID.randomUUID();
        Exercise exercise = Exercise.builder()
                .id(userId)
                .build();

        when(exerciseRepository.findById(exercise.getId()))
                .thenReturn(Optional.of(exercise));

        Exercise result = exerciseService.getExerciseById(exercise.getId());

        assertEquals(exercise, result);

    }

    @Test
    void getExerciseById_shouldThrowException_whenExerciseNotFound() {
        UUID exerciseId = UUID.randomUUID();

        when(exerciseRepository.findById(exerciseId))
                .thenReturn(Optional.empty());

        AppException exception = assertThrows(
                AppException.class,
                () -> exerciseService.getExerciseById(exerciseId));

        assertEquals(
                ErrorCode.EXERCISE_NOT_FOUND,
                exception.getErrorCode());
    }

}
