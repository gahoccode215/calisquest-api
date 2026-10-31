package com.calisquest.api.entity;

import com.calisquest.api.entity.enums.MilestoneRequirementType;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "skill_milestones", uniqueConstraints = {
        @UniqueConstraint(name = "uk_skill_milestone_order", columnNames = { "skill_id", "order_index" })
})
public class SkillMilestone extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "skill_id", nullable = false)
    private Skill skill;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "order_index", nullable = false)
    private Integer orderIndex;

    @Enumerated(EnumType.STRING)
    @Column(name = "requirement_type", nullable = false, length = 30)
    private MilestoneRequirementType requirementType;

    @Column(name = "target_value", nullable = false)
    private Integer targetValue;

    @Column(columnDefinition = "TEXT")
    private String instructions;
}