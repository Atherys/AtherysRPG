package com.atherys.rpg.character;

import com.atherys.rpg.api.character.RPGCharacter;
import com.atherys.rpg.api.skill.RPGSkill;
import com.atherys.rpg.api.stat.AttributeType;
import com.atherys.rpg.repository.converter.AttributeTypeConverter;
import org.spongepowered.api.entity.living.player.Player;

import javax.annotation.Nonnull;
import javax.persistence.*;
import java.util.*;

@Entity
@Table(schema = "atherysrpg", name = "PlayerCharacter")
public class PlayerCharacter implements RPGCharacter<Player> {

    @Id
    private UUID id;

    @Transient
    private Player entity;

    @Transient
    private boolean hasJoined;

    /**
     * Attributes the player has leveled up, not including the default attribute amount
     * from the configuration
     * Only Upgradable Attributes should be stored within this map
     */
    @Convert(attributeName = "key.", converter = AttributeTypeConverter.class)
    @ElementCollection(fetch = FetchType.EAGER)
    @MapKeyColumn(name = "attribute_type")
    @Column(name = "value")
    @CollectionTable(schema = "atherysrpg", name = "PlayerCharacter_attributes")
    private Map<AttributeType, Double> characterAttributes = new HashMap<>();

    /**
     * Attributes that come from temporary sources
     */
    @Transient
    private Map<AttributeType, Double> buffAttributes = new HashMap<>();

    private double experience;

    private double spentExperience;

    private double spentAttributeExperience;

    private double spentSkillExperience;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(schema = "atherysrpg", name = "PlayerCharacter_skills")
    private List<String> skills = new ArrayList<>();

    @Transient
    private List<RPGSkill> rpgSkills;

    private String role;

    public PlayerCharacter() {
    }

    public PlayerCharacter(UUID uniqueId) {
        this.id = uniqueId;
    }

    @Nonnull
    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public Optional<Player> getEntity() {
        return Optional.ofNullable(entity);
    }

    public void setEntity(Player living) {
        this.entity = living;
    }

    public boolean hasJoined() {
        return hasJoined;
    }

    public void setHasJoined(boolean hasJoined) {
        this.hasJoined = hasJoined;
    }

    @Override
    public Map<AttributeType, Double> getCharacterAttributes() {
        return Collections.unmodifiableMap(characterAttributes);
    }

    @Override
    public void setCharacterAttribute(AttributeType type, Double value) {
        characterAttributes.put(type, value);
    }

    @Override
    public void addCharacterAttribute(AttributeType type, Double amount) {
        characterAttributes.merge(type, amount, Double::sum);
    }

    public void setCharacterAttributes(Map<AttributeType, Double> characterAttributes) {
        this.characterAttributes = characterAttributes;
    }

    @Override
    public Map<AttributeType, Double> getBuffAttributes() {
        return Collections.unmodifiableMap(buffAttributes);
    }

    @Override
    public void mergeBuffAttributes(Map<AttributeType, Double> additional) {
        additional.forEach((type, value) -> buffAttributes.merge(type, value, Double::sum));
    }

    public double getExperience() {
        return experience;
    }

    public void setExperience(double experience) {
        this.experience = experience;
    }

    public double getSpentExperience() {
        return spentExperience;
    }

    public void setSpentExperience(double spentExperience) {
        this.spentExperience = spentExperience;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void addSkill(String skill) {
        skills.add(skill);
    }

    public void removeSkill(String skill) {
        skills.remove(skill);
    }

    public double getSpentSkillExperience() {
        return spentSkillExperience;
    }

    public void setSpentSkillExperience(double spentSkillsExperience) {
        this.spentSkillExperience = spentSkillsExperience;
    }

    public double getSpentAttributeExperience() {
        return spentAttributeExperience;
    }

    public void setSpentAttributeExperience(double spentAttributeExperience) {
        this.spentAttributeExperience = spentAttributeExperience;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
