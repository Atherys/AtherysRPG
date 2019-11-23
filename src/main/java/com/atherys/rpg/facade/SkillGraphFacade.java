package com.atherys.rpg.facade;

import com.atherys.rpg.AtherysRPG;
import com.atherys.rpg.api.skill.RPGSkill;
import com.atherys.rpg.api.skill.SkillGraph;
import com.atherys.rpg.api.util.Graph;
import com.atherys.rpg.config.AtherysRPGConfig;
import com.atherys.rpg.config.SkillNodeConfig;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.*;

@Singleton
public class SkillGraphFacade {

    @Inject
    private AtherysRPGConfig config;

    @Inject
    private RPGSkillFacade skillFacade;

    private SkillGraph skillGraph;

    public SkillGraph getSkillGraph() {

        if (skillGraph != null) {
            return skillGraph;
        }

        Map<String, RPGSkill> namedSkillNodes = new HashMap<>();

        // Create an empty skill graph
        SkillGraph newSkillGraph = new SkillGraph(getSkillFromConfigNode(config.SKILL_GRAPH.ROOT));

        // Read skills and store temporarily for later use
        config.SKILL_GRAPH.NODES.forEach((key, node) -> {
            namedSkillNodes.put(key, getSkillFromConfigNode(node));
        });

        AtherysRPG.getInstance().getLogger().info(config.SKILL_GRAPH.LINKS.toString());

        // Read links between skills and apply to skill graph from previously stored interpreted skills
        config.SKILL_GRAPH.LINKS.forEach((linkConfig) -> {
            RPGSkill parent = namedSkillNodes.get(linkConfig.PARENT_SKILL_NODE_ID);
            RPGSkill child = namedSkillNodes.get(linkConfig.CHILD_SKILL_NODE_ID);
            double cost = linkConfig.COST;
            Graph.LinkType type = Graph.LinkType.valueOf(linkConfig.TYPE.toString());

            newSkillGraph.add(parent, child, cost, type);
        });

        AtherysRPG.getInstance().getLogger().info(newSkillGraph.toString());
        // Cache the skill graph for later use
        this.skillGraph = newSkillGraph;
        return skillGraph;
    }

    private RPGSkill getSkillFromConfigNode(SkillNodeConfig node) {
        Optional<RPGSkill> rpgSkillOptional = skillFacade.getSkillById(node.SKILL_ID);

        // If no castable object registered under the provided id could be found, throw an exception
        if (!rpgSkillOptional.isPresent()) {
            throw new NoSuchElementException("No registered skill with an id of '" + node.SKILL_ID + "' could be found.");
        }

        RPGSkill rpgSkill = rpgSkillOptional.get();

        // Set configured cooldown expression, if available
        if (node.COOLDOWN_EXPRESSION != null) {
            rpgSkill.setCooldownExpression(node.COOLDOWN_EXPRESSION);
        }

        // Set configured cost expression, if available
        if (node.COST_EXPRESSION != null) {
            rpgSkill.setResourceCostExpression(node.COST_EXPRESSION);
        }

        // Set configured properties, if available
        if (node.PROPERTIES != null && !node.PROPERTIES.isEmpty()) {
            rpgSkill.setProperties(node.PROPERTIES);
        }
        return rpgSkill;
    }

    public RPGSkill getSkillGraphRoot() {
        return getSkillGraph().getRoot();
    }

    /**
     * Tests whether a list of skill ID's has a path through the server's skill graph.
     * @return Whether the list passes.
     */
    public boolean isPathValid(List<String> skills) {
        if (skills.isEmpty()) {
            return false;
        }

        Optional<RPGSkill> root = skillFacade.getSkillById(skills.get(0));
        if (!root.isPresent() || !getSkillGraph().getRoot().equals(root.get())) {
            return false;
        }

        int i = 1;
        for (String skillId : skills.subList(1, skills.size())) {
            Optional<RPGSkill> skill = skillFacade.getSkillById(skillId);
            if (skill.isPresent()) {
                if (!checkLink(skill.get(), skills.subList(0, i))) {
                    return false;
                }
            } else {
                return false;
            }
            i++;
        }

        return true;
    }

    private boolean checkLink(RPGSkill skill, List<String> previousIds) {
        for (String id : previousIds) {
            RPGSkill s = skillFacade.getSkillById(id).get();
            if (getSkillGraph().getLink(skill, s) != null) {
                return true;
            }
        }
        return false;
    }

    public Set<RPGSkill> getLinkedSkills(List<String> skillIds) {
        Set<RPGSkill> linked = new HashSet<>();

        skillIds.forEach(s -> {
            getSkillGraph().getLinksWhereParent(skillFacade.getSkillById(s).get()).forEach(link -> {
                if (!skillIds.contains(link.getChild().getData().getId())) {
                    linked.add(link.getChild().getData());
                }
            });
        });

        return linked;
    }


    public Optional<Graph.Link<RPGSkill>> getLinkBetween(RPGSkill skill, List<String> skillIds) {
        for (String skillId : skillIds) {
            Graph.Link<RPGSkill> link = getSkillGraph().getLink(skill, skillFacade.getSkillById(skillId).get());
            if (link != null) {
                return Optional.of(link);
            }
        }
        return Optional.empty();
    }

    public Optional<Double> getCostForSkill(RPGSkill skill, List<String> skillIds) {
        return getLinkBetween(skill, skillIds).map(Graph.Link::getWeight);
    }
}
