package com.calisquest.api.controller;

import com.calisquest.api.common.response.ApiResponseDTO;
import com.calisquest.api.dto.response.SkillMilestoneResponse;
import com.calisquest.api.service.SkillMilestoneService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/skills/{skillId}/milestones")
@RequiredArgsConstructor
public class SkillMilestoneController {

    private final SkillMilestoneService skillMilestoneService;

    @GetMapping
    public ApiResponseDTO<List<SkillMilestoneResponse>> getMilestones(
            @PathVariable UUID skillId) {

        return ApiResponseDTO.success(
                skillMilestoneService.getMilestones(skillId));
    }
}