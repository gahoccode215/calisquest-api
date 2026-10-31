package com.calisquest.api.repository;

import com.calisquest.api.entity.SkillMilestone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface SkillMilestoneRepository
        extends JpaRepository<SkillMilestone, UUID> {

    @Query(value = "SELECT * FROM skill_milestones WHERE skill_id = :skillId ORDER BY order_index ASC", nativeQuery = true)
    List<SkillMilestone> findBySkillIdOrderByOrderIndexAsc(@Param("skillId") UUID skillId);
}