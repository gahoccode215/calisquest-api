package com.calisquest.api.service;

import com.calisquest.api.entity.Exercise;

import java.util.List;
import java.util.UUID;

public interface ExerciseService {

    List<Exercise> getAllExercises();

    Exercise getExerciseById(UUID id);
}