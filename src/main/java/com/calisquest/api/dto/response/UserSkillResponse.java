package com.calisquest.api.dto.response;

import com.calisquest.api.entity.enums.SkillDifficulty;
import com.calisquest.api.entity.enums.SkillProgressStatus;

import java.util.UUID;

public record UserSkillResponse(
        UUID skillId,
        String skillName,
        SkillDifficulty difficulty,
        SkillProgressStatus status,
        Integer progress) {
}