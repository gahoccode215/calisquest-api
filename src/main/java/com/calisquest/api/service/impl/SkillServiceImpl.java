
package com.calisquest.api.service.impl;

import com.calisquest.api.entity.Skill;
import com.calisquest.api.exception.AppException;
import com.calisquest.api.exception.ErrorCode;
import com.calisquest.api.repository.SkillRepository;
import com.calisquest.api.service.SkillService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SkillServiceImpl implements SkillService {

    private final SkillRepository skillRepository;

    @Override
    public List<Skill> getAllSkills() {
        return skillRepository.findAll();
    }

    @Override
    public Skill getSkillById(UUID id) {
        return skillRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.SKILL_NOT_FOUND));
    }
}
