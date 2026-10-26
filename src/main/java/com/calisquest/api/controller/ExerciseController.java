
package com.calisquest.api.controller;

import com.calisquest.api.common.response.ApiResponseDTO;
import com.calisquest.api.entity.Exercise;
import com.calisquest.api.service.ExerciseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/exercises")
@RequiredArgsConstructor
public class ExerciseController {

    private final ExerciseService exerciseService;

    @GetMapping
    public ApiResponseDTO<List<Exercise>> getAllExercises() {
        return ApiResponseDTO.success(
                exerciseService.getAllExercises());
    }

    @GetMapping("/{id}")
    public ApiResponseDTO<Exercise> getExerciseById(
            @PathVariable UUID id) {
        return ApiResponseDTO.success(
                exerciseService.getExerciseById(id));
    }
}
