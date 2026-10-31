package com.calisquest.api.service;

import com.calisquest.api.dto.response.SkillMilestoneResponse;
import com.calisquest.api.entity.SkillMilestone;
import com.calisquest.api.repository.SkillMilestoneRepository;
import com.calisquest.api.repository.SkillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface SkillMilestoneService {

    List<SkillMilestoneResponse> getMilestones(UUID skillId);

}