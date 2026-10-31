package com.calisquest.api.dto.response;

import com.calisquest.api.entity.enums.MilestoneRequirementType;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class SkillMilestoneResponse {

    private UUID id;

    private String name;

    private String description;

    private Integer orderIndex;

    private MilestoneRequirementType requirementType;

    private Integer targetValue;

    private String instructions;
}