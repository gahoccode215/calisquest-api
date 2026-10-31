
package com.calisquest.api.repository;

import com.calisquest.api.entity.UserSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserSkillRepository extends JpaRepository<UserSkill, UUID> {

    @Query(value = "SELECT * FROM user_skills WHERE user_id = :userId", nativeQuery = true)
    List<UserSkill> findAllByUser_Id(@Param("userId") UUID userId);

    @Query(value = "SELECT EXISTS (SELECT 1 FROM user_skills WHERE user_id = :userId AND skill_id = :skillId)", nativeQuery = true)
    boolean existsByUser_IdAndSkill_Id(@Param("userId") UUID userId, @Param("skillId") UUID skillId);

    @Query(value = "SELECT * FROM user_skills WHERE user_id = :userId AND skill_id = :skillId", nativeQuery = true)
    Optional<UserSkill> findByUserIdAndSkillId(@Param("userId") UUID userId, @Param("skillId") UUID skillId);
}
