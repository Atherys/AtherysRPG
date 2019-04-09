package com.atherys.rpg.api.skill;

import com.atherys.rpg.util.TextUtils;
import org.spongepowered.api.text.TextTemplate;
import org.spongepowered.api.util.Tuple;

import java.util.HashMap;
import java.util.Map;

public class SkillSpec {

    private String id;

    private String name;

    private String permission;

    private TextTemplate descriptionTemplate;

    private Tuple<String, ?>[] descriptionArguments;

    private String cooldownExpression;

    private String resourceCostExpression;

    private Map<String,String> properties = new HashMap<>();

    protected SkillSpec() {
    }

    public static SkillSpec create() {
        return new SkillSpec();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPermission() {
        return permission;
    }

    public TextTemplate getDescriptionTemplate() {
        return descriptionTemplate;
    }

    public Tuple<String, ?>[] getDescriptionArguments() {
        return descriptionArguments;
    }

    public String getCooldownExpression() {
        return cooldownExpression;
    }

    public String getResourceCostExpression() {
        return resourceCostExpression;
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public SkillSpec id(String id) {
        this.id = id;
        return this;
    }

    public SkillSpec name(String name) {
        this.name = name;
        return this;
    }

    public SkillSpec permission(String permission) {
        this.permission = permission;
        return this;
    }

    public SkillSpec descriptionTemplate(TextTemplate descriptionTemplate) {
        this.descriptionTemplate = descriptionTemplate;
        return this;
    }

    /**
     * Create a new TextTemplate and set it
     * @param template
     * @return
     */
    public SkillSpec descriptionTemplate(String template) {
        this.descriptionTemplate = TextUtils.templateOf(template);
        return this;
    }

    @SafeVarargs
    public final SkillSpec descriptionArguments(Tuple<String, ?>... descriptionArguments) {
        this.descriptionArguments = descriptionArguments;
        return this;
    }

    public SkillSpec cooldown(String cooldownExpression) {
        this.cooldownExpression = cooldownExpression;
        return this;
    }

    public SkillSpec resourceCost(String resourceCostExpression) {
        this.resourceCostExpression = resourceCostExpression;
        return this;
    }

    public SkillSpec properties(Map<String, String> properties) {
        this.properties = properties;
        return this;
    }

    public SkillSpec property(String key, String value) {
        this.properties.put(key, value);
        return this;
    }
}
