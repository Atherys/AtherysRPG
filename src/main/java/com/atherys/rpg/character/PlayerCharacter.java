package com.atherys.rpg.character;

import com.atherys.rpg.api.character.RPGCharacter;
import com.atherys.rpg.api.stat.AttributeType;
import com.atherys.rpg.repository.converter.AttributeTypeConverter;
import com.atherys.rpg.repository.converter.ItemTypeConverter;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.ItemType;

import javax.annotation.Nonnull;
import javax.persistence.*;
import java.util.*;

@Entity
public class PlayerCharacter implements RPGCharacter<Player> {

    @Id
    private UUID id;

    @Transient
    private Player entity;

    @Transient
    private boolean hasJoined;

    @Convert(attributeName = "key.", converter = AttributeTypeConverter.class)
    @ElementCollection(fetch = FetchType.EAGER)
    @MapKeyColumn(name = "attribute_type")
    @Column(name = "value")
    @CollectionTable(name = "playercharacter_attributes")
    private Map<AttributeType, Double> baseAttributes = new HashMap<>();

    @Transient
    private Map<AttributeType, Double> buffAttributes = new HashMap<>();

    @Convert(attributeName = "key.", converter = ItemTypeConverter.class)
    @ElementCollection(fetch = FetchType.EAGER)
    @MapKeyColumn(name = "item_bindings")
    @CollectionTable(name = "playercharacter_bindings")
    private Map<ItemType, String> itemBindings = new HashMap<>();

    private double experience;

    private double spentExperience;

    private double spentAttributeExperience;

    private double spentSkillExperience;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> skills = new ArrayList<>();

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
    public Map<AttributeType, Double> getBaseAttributes() {
        return baseAttributes;
    }

    public void setBaseAttributes(Map<AttributeType, Double> baseAttributes) {
        this.baseAttributes = baseAttributes;
    }

    @Override
    public Map<AttributeType, Double> getBuffAttributes() {
        return buffAttributes;
    }

    public void setBuffAttributes(Map<AttributeType, Double> buffAttributes) {
        this.buffAttributes = buffAttributes;
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

    public Map<ItemType, String> getItemBindings() {
        return itemBindings;
    }

    public void setItemBindings(Map<ItemType, String> itemBindings) {
        this.itemBindings = itemBindings;
    }

    public void addItemBinding(ItemType itemType, String skillId) {
        this.itemBindings.put(itemType, skillId);
    }

    public void removeItemBinding(ItemType itemType) {
        this.itemBindings.remove(itemType);
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
}
