package com.calisquest.api.entity;

import com.calisquest.api.entity.enums.Difficulty;
import com.calisquest.api.entity.enums.Equipment;
import com.calisquest.api.entity.enums.MuscleGroup;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.lang.reflect.Array;
import java.time.Instant;
import java.util.ArrayList;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "exercises")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Exercise extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Difficulty difficulty;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private MuscleGroup primaryMuscleGroup;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private Equipment equipment;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String instructions;

}