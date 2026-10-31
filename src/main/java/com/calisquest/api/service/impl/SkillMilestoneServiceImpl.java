package com.calisquest.api.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.calisquest.api.dto.response.SkillMilestoneResponse;
import com.calisquest.api.entity.SkillMilestone;
import com.calisquest.api.repository.SkillMilestoneRepository;
import com.calisquest.api.repository.SkillRepository;
import com.calisquest.api.service.SkillMilestoneService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SkillMilestoneServiceImpl implements SkillMilestoneService {

    private final SkillMilestoneRepository skillMilestoneRepository;
    private final SkillRepository skillRepository;

    @Transactional(readOnly = true)
    public List<SkillMilestoneResponse> getMilestones(UUID skillId) {

        if (!skillRepository.existsById(skillId)) {
            throw new RuntimeException("Skill not found");
        }

        List<SkillMilestone> milestones = skillMilestoneRepository
                .findBySkillIdOrderByOrderIndexAsc(skillId);

        return milestones.stream()
                .map(milestone -> SkillMilestoneResponse.builder()
                        .id(milestone.getId())
                        .name(milestone.getName())
                        .description(milestone.getDescription())
                        .orderIndex(milestone.getOrderIndex())
                        .requirementType(milestone.getRequirementType())
                        .targetValue(milestone.getTargetValue())
                        .instructions(milestone.getInstructions())
                        .build())
                .toList();
    }

}
