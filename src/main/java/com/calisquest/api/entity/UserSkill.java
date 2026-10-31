
package com.calisquest.api.entity;

import com.calisquest.api.entity.enums.SkillProgressStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_skills", uniqueConstraints = {
        @UniqueConstraint(name = "uk_user_skills_user_skill", columnNames = { "user_id", "skill_id" })
})
public class UserSkill extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "skill_id", nullable = false)
    private Skill skill;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private SkillProgressStatus status = SkillProgressStatus.NOT_STARTED;

    @Builder.Default
    @Column(nullable = false)
    private Integer progress = 0;
}
