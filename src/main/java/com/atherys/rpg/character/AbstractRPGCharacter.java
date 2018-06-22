package com.atherys.rpg.character;

import com.atherys.rpg.api.attribute.Attribute;
import com.atherys.rpg.api.character.RPGCharacter;
import com.atherys.rpg.api.character.tree.TalentTree;
import com.atherys.rpg.api.effect.Applyable;
import com.atherys.rpg.api.resource.Resource;
import com.atherys.rpg.api.skill.CastResult;
import com.atherys.rpg.api.skill.Castable;
import com.atherys.rpg.api.skill.CastableProperties;
import com.atherys.rpg.skill.SkillService;

import javax.annotation.Nonnull;
import java.util.*;

public abstract class AbstractRPGCharacter implements RPGCharacter {

    protected UUID entityUUID;

    protected Set<Applyable> effects = new HashSet<>();
    protected Set<Attribute> attribs = new HashSet<>();

    protected Map<Castable,CastableProperties> skills = new HashMap<>();

    protected Resource resource;

    protected Map<TalentTree, Set<TalentTree.Node>> selectedNodes = new HashMap<>();

    protected Map<Castable,Long> lastUsed = new HashMap<>();

    protected AbstractRPGCharacter(UUID uuid) {
        this.entityUUID = uuid;
    }

    @Override
    @Nonnull
    public UUID getUniqueId() {
        return entityUUID;
    }

    @Override
    public CastResult cast(Castable castable, long timestamp, String... args) {
        Long cooldown = getProperty(castable, CastableProperties.COOLDOWN, Long.class);
        Double cost = getProperty(castable, CastableProperties.COST, Double.class);

        if ( lastUsed.containsKey(castable) && timestamp - lastUsed.get(castable) <= cooldown ) {
            return CastResult.onCooldown(timestamp, castable, lastUsed.get(castable) + cooldown);
        }

        if ( resource.getCurrent() < cost ) return CastResult.insufficientResources(castable, resource);

        return SkillService.getInstance().cast(castable, this, timestamp, args);
    }

    @Override
    public Set<Attribute> getAttributes() {
        return attribs;
    }

    @Override
    public Set<Applyable> getEffects() {
        return effects;
    }

    @Override
    public Map<Castable,CastableProperties> getCastables() {
        return skills;
    }

    @Override
    public Resource getResource() {
        return resource;
    }

    @Override
    public void setResource(Resource resource) {
        this.resource = resource;
    }

    @Override
    public Set<TalentTree.Node> getSelectedNodesFromTree(TalentTree tree) {
        return selectedNodes.getOrDefault(tree, new HashSet<>());
    }

    @Override
    public void setSelectedNodesFromTree(TalentTree tree, Set<TalentTree.Node> nodes) {
        mutateWithNodesAtDepth(nodes, 0);
        selectedNodes.put(tree, nodes);
    }

    /**
     * Applies all the TalentTree nodes at the specified depth,
     * and then recursively calls itself for higher depths, until no more nodes are found
     *
     * @param nodes The nodes to be applied
     * @param depth The depth to start at
     */
    private void mutateWithNodesAtDepth(Set<TalentTree.Node> nodes, int depth) {
        boolean found = false;

        for (TalentTree.Node node : nodes) {
            if ( node.getDepth() == depth ) {
                node.mutate(this);
                if (!found) found = true;
            }
        }

        if ( found ) mutateWithNodesAtDepth(nodes, depth + 1);
    }
}
