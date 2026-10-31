package com.calisquest.api.service;

import com.calisquest.api.dto.response.UserSkillResponse;

import java.util.List;
import java.util.UUID;

public interface UserSkillService {

    UserSkillResponse addSkillToUser(UUID userId, UUID skillId);

    List<UserSkillResponse> getUserSkills(UUID userId);

    UserSkillResponse updateProgress(
            UUID userId,
            UUID skillId,
            Integer progress);
}