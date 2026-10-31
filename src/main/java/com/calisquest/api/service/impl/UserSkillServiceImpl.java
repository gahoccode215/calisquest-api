package com.calisquest.api.service.impl;

import com.calisquest.api.dto.response.UserSkillResponse;
import com.calisquest.api.entity.Skill;
import com.calisquest.api.entity.User;
import com.calisquest.api.entity.UserSkill;
import com.calisquest.api.entity.enums.SkillProgressStatus;
import com.calisquest.api.exception.AppException;
import com.calisquest.api.exception.ErrorCode;
import com.calisquest.api.repository.SkillRepository;
import com.calisquest.api.repository.UserRepository;
import com.calisquest.api.repository.UserSkillRepository;
import com.calisquest.api.service.UserSkillService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserSkillServiceImpl implements UserSkillService {

    private final UserSkillRepository userSkillRepository;
    private final UserRepository userRepository;
    private final SkillRepository skillRepository;

    @Override
    @Transactional
    public UserSkillResponse addSkillToUser(UUID userId, UUID skillId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        Skill skill = skillRepository.findById(skillId)
                .orElseThrow(() -> new AppException(ErrorCode.SKILL_NOT_FOUND));

        if (userSkillRepository.existsByUser_IdAndSkill_Id(userId, skillId)) {
            throw new AppException(ErrorCode.USER_SKILL_ALREADY_EXISTS);
        }

        UserSkill userSkill = UserSkill.builder()
                .user(user)
                .skill(skill)
                .status(SkillProgressStatus.NOT_STARTED)
                .progress(0)
                .build();

        UserSkill savedUserSkill = userSkillRepository.save(userSkill);

        return toResponse(savedUserSkill);
    }

    @Override
    public List<UserSkillResponse> getUserSkills(UUID userId) {
        if (!userRepository.existsById(userId)) {
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }

        return userSkillRepository.findAllByUser_Id(userId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public UserSkillResponse updateProgress(
            UUID userId,
            UUID skillId,
            Integer progress) {

        if (progress == null || progress < 0 || progress > 100) {
            throw new AppException(ErrorCode.INVALID_SKILL_PROGRESS);
        }

        UserSkill userSkill = userSkillRepository
                .findByUserIdAndSkillId(userId, skillId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_SKILL_NOT_FOUND));

        userSkill.setProgress(progress);
        userSkill.setStatus(resolveStatus(progress));

        UserSkill savedUserSkill = userSkillRepository.save(userSkill);

        return toResponse(savedUserSkill);
    }

    private SkillProgressStatus resolveStatus(Integer progress) {
        if (progress == 0) {
            return SkillProgressStatus.NOT_STARTED;
        }

        if (progress == 100) {
            return SkillProgressStatus.COMPLETED;
        }

        return SkillProgressStatus.IN_PROGRESS;
    }

    private UserSkillResponse toResponse(UserSkill userSkill) {
        return new UserSkillResponse(
                userSkill.getSkill().getId(),
                userSkill.getSkill().getName(),
                userSkill.getSkill().getDifficulty(),
                userSkill.getStatus(),
                userSkill.getProgress());
    }
}