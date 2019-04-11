package com.atherys.rpg.facade;

import com.atherys.rpg.api.skill.RPGSkill;
import com.atherys.rpg.api.skill.SkillGraph;
import com.atherys.rpg.api.util.Graph;
import com.atherys.rpg.config.AtherysRPGConfig;
import com.atherys.rpg.skill.DummySkill;
import com.atherys.skills.api.skill.Castable;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.apache.commons.lang3.StringUtils;
import org.spongepowered.api.Sponge;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

@Singleton
public class SkillGraphFacade {

    @Inject
    private AtherysRPGConfig config;

    private SkillGraph skillGraph;

    public SkillGraph getSkillGraph() {

        if (skillGraph != null) {
            return skillGraph;
        }

        Map<String, RPGSkill> namedSkillNodes = new HashMap<>();

        // Create an empty skill graph
        SkillGraph newSkillGraph = new SkillGraph(new DummySkill("root", "Root", "The Root of the skill graph."));

        // Read skills and store temporarily for later use
        config.SKILL_GRAPH.NODES.forEach((key, node) -> {
            Optional<Castable> castable = Sponge.getRegistry().getType(Castable.class, node.SKILL_ID);

            // If no castable object registered under the provided id could be found, throw an exception
            if (!castable.isPresent()) {
                throw new NoSuchElementException("No registered skill with an id of '" + node.SKILL_ID + "' could be found.");
            }

            Castable skill = castable.get();

            // If the castable does not extend RPGSkill, throw an exception
            if (!(skill instanceof RPGSkill)) {
                throw new IllegalArgumentException("Skill with id '" + node.SKILL_ID + "' does not extend RPGSkill. Only skills extending the RPGSkill class may be included as part of a SkillGraph.");
            }

            RPGSkill rpgSkill = (RPGSkill) skill;

            // Set configured cooldown expression, if available
            if (!StringUtils.isEmpty(node.COOLDOWN_EXPRESSION)) {
                rpgSkill.setCooldownExpression(node.COOLDOWN_EXPRESSION);
            }

            // Set configured cost expression, if available
            if (!StringUtils.isEmpty(node.COST_EXPRESSION)) {
                rpgSkill.setResourceCostExpression(node.COST_EXPRESSION);
            }

            // Set configured properties, if available
            if (node.PROPERTIES != null && !node.PROPERTIES.isEmpty()) {
                rpgSkill.setProperties(node.PROPERTIES);
            }

            namedSkillNodes.put(key, rpgSkill);
        });

        // Read links between skills and apply to skill graph from previously stored interpreted skills
        config.SKILL_GRAPH.LINKS.forEach((linkConfig) -> {
            RPGSkill parent = namedSkillNodes.get(linkConfig.PARENT_SKILL_NODE_ID);
            RPGSkill child = namedSkillNodes.get(linkConfig.CHILD_SKILL_NODE_ID);
            double cost = linkConfig.COST;
            Graph.LinkType type = Graph.LinkType.valueOf(linkConfig.TYPE.toString());

            newSkillGraph.add(parent, child, cost, type);
        });

        // Cache the skill graph for later use
        this.skillGraph = newSkillGraph;
        return skillGraph;
    }

}
