package com.atherys.rpg.config.skill;

import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;

@ConfigSerializable
public class SkillNodeLinkConfig {

    public enum SkillNodeLinkConfigType {
        UNIDIRECTIONAL,
        BIDIRECTIONAL
    }

    @Setting("parent-node")
    public String PARENT_SKILL_NODE_ID = "some-skill-node-parent-id";

    @Setting("child-node")
    public String CHILD_SKILL_NODE_ID = "some-skill-node-child-id";

    @Setting("cost")
    public double COST = 0.0;

    @Setting("type")
    public SkillNodeLinkConfigType TYPE = SkillNodeLinkConfigType.UNIDIRECTIONAL;
}
