package com.atherys.rpg.repository.converter;

import com.atherys.rpg.api.skill.BlankSkill;
import com.atherys.rpg.api.skill.RPGSkill;
import com.atherys.skills.AtherysSkills;

import javax.persistence.AttributeConverter;

public class SkillConverter implements AttributeConverter<RPGSkill, String> {
    @Override
    public String convertToDatabaseColumn(RPGSkill skill) {
        return skill.getId();
    }

    @Override
    public RPGSkill convertToEntityAttribute(String skillId) {
        return (RPGSkill) AtherysSkills.getInstance().getSkillService().getSkillById(skillId).orElse(BlankSkill.blank);
    }
}
