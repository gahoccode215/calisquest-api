
package com.calisquest.api.service;

import com.calisquest.api.entity.Skill;

import java.util.List;
import java.util.UUID;

public interface SkillService {

    List<Skill> getAllSkills();

    Skill getSkillById(UUID id);
}
