package com.atherys.rpg.service;

import com.atherys.rpg.api.skill.RPGSkill;
import com.atherys.rpg.api.skill.SkillGraph;
import com.atherys.rpg.api.util.Graph;
import com.atherys.rpg.character.PlayerCharacter;
import com.atherys.rpg.command.exception.RPGCommandException;
import com.atherys.rpg.config.skill.SkillGraphConfig;
import com.atherys.rpg.config.skill.SkillNodeConfig;
import com.atherys.rpg.facade.RPGSkillFacade;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.udojava.evalex.Expression;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Singleton
public class SkillGraphService {

    @Inject
    private SkillGraphConfig config;

    @Inject
    private RPGSkillFacade skillFacade;

    @Inject
    private ExpressionService expressionService;

    @Inject
    private AttributeService attributeService;

    private SkillGraph skillGraph;

    private Set<RPGSkill> uniqueSkills;

    public SkillGraph getSkillGraph() {

        if (skillGraph != null) {
            return skillGraph;
        }

        // Cache the skill graph for later use
        this.skillGraph = loadSkillGraph();
        return skillGraph;
    }

    private SkillGraph loadSkillGraph() {
        Map<String, RPGSkill> namedSkillNodes = new HashMap<>();

        RPGSkill rootSkill = getSkillFromConfigNode(config.ROOT);
        namedSkillNodes.put("root-skill", rootSkill);

        // Create an empty skill graph
        SkillGraph newSkillGraph = new SkillGraph(rootSkill);

        // Read skills and store temporarily for later use
        config.NODES.forEach((key, node) -> {
            namedSkillNodes.put(key, getSkillFromConfigNode(node));
        });

        // Read links between skills and apply to skill graph from previously stored interpreted skills
        config.LINKS.forEach((linkConfig) -> {
            RPGSkill parent = namedSkillNodes.get(linkConfig.PARENT_SKILL_NODE_ID);
            RPGSkill child = namedSkillNodes.get(linkConfig.CHILD_SKILL_NODE_ID);
            String cost = linkConfig.COST;
            Graph.LinkType type = Graph.LinkType.valueOf(linkConfig.TYPE.toString());

            newSkillGraph.add(parent, child, cost, type);
        });

        this.uniqueSkills = config.UNIQUE_SKILLS.stream()
                .map(skillId -> skillFacade.getSkillById(skillId).get())
                .collect(Collectors.toSet());

        return newSkillGraph;
    }

    public Set<RPGSkill> getUniqueSkills() {
        return uniqueSkills;
    }

    public void resetSkillGraph() throws RPGCommandException {
        SkillGraph resetSkillGraph = loadSkillGraph();

        if (!resetSkillGraph.equals(skillGraph)) {
            throw new RPGCommandException(
                    "The skill tree layout was changed. Changes will not be applied. (A full restart is required to change the tree's layout.)"
            );
        }

        this.skillGraph = resetSkillGraph;
    }

    private RPGSkill getSkillFromConfigNode(SkillNodeConfig node) {
        Optional<RPGSkill> rpgSkillOptional = skillFacade.getSkillById(node.SKILL_ID);

        // If no castable object registered under the provided id could be found, throw an exception
        if (!rpgSkillOptional.isPresent()) {
            throw new NoSuchElementException("No registered skill with an id of '" + node.SKILL_ID + "' could not be found.");
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
        Set<RPGSkill> skills = skillIds.stream()
                .map(skillId -> skillFacade.getSkillById(skillId).get())
                .collect(Collectors.toSet());

        return getLinkedSkills(skills);
    }

    public Set<RPGSkill> getLinkedSkills(Set<RPGSkill> skills) {
        Set<RPGSkill> linked = new HashSet<>();

        boolean hasUnique = skills.stream()
                .anyMatch(skill -> uniqueSkills.contains(skill));

        skills.forEach(s -> {
            getSkillGraph().getLinksWhereParent(s).forEach(link -> {
                RPGSkill child = link.getChild().getData();
                RPGSkill parent = link.getParent().getData();

                // If the link is bidirectional we have to check if the other node is the "parent"
                if (link.getType() == Graph.LinkType.BIDIRECTIONAL) {
                    if (!skills.contains(parent) && (!hasUnique || !uniqueSkills.contains(parent))) {
                        linked.add(parent);
                    }
                }
                if (!skills.contains(child) && (!hasUnique || !uniqueSkills.contains(child))) {
                    linked.add(child);
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

    public Optional<Double> getCostForSkill(PlayerCharacter pc, RPGSkill skill, List<String> skillIds) {
        return getLinkBetween(skill, skillIds).map(link -> {
            Expression expression = expressionService.getExpression(link.getWeight());
            expression.setVariable("SKILLS", BigDecimal.valueOf(skillIds.size()));
            expression.setVariable("ATTRIBUTES", BigDecimal.valueOf(attributeService.getUpgradeableAttributeCount(pc)));
            return expression.eval().doubleValue();
        });
    }
}
