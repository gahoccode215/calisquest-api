
package com.calisquest.api.controller;

import com.calisquest.api.common.response.ApiResponseDTO;
import com.calisquest.api.dto.request.UpdateSkillProgressRequest;
import com.calisquest.api.dto.response.UserSkillResponse;
import com.calisquest.api.entity.UserSkill;
import com.calisquest.api.service.UserSkillService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users/{userId}/skills")
@RequiredArgsConstructor
public class UserSkillController {

        private final UserSkillService userSkillService;

        @PostMapping("/{skillId}")
        @ResponseStatus(HttpStatus.CREATED)
        public ApiResponseDTO<UserSkillResponse> addSkillToUser(
                        @PathVariable UUID userId,
                        @PathVariable UUID skillId) {
                return ApiResponseDTO.success(
                                userSkillService.addSkillToUser(userId, skillId));
        }

        @GetMapping
        public ApiResponseDTO<List<UserSkillResponse>> getUserSkills(
                        @PathVariable UUID userId) {
                return ApiResponseDTO.success(
                                userSkillService.getUserSkills(userId));
        }

        @PatchMapping("/{skillId}/progress")
        public ApiResponseDTO<UserSkillResponse> updateProgress(
                        @PathVariable UUID userId,
                        @PathVariable UUID skillId,
                        @Valid @RequestBody UpdateSkillProgressRequest request) {
                return ApiResponseDTO.success(
                                userSkillService.updateProgress(
                                                userId,
                                                skillId,
                                                request.getProgress()));
        }
}
