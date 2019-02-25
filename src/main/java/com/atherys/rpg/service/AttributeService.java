package com.atherys.rpg.service;

import com.atherys.rpg.AtherysRPGConfig;
import com.atherys.rpg.api.stat.Attribute;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.HashSet;
import java.util.Set;

@Singleton
public class AttributeService {

    @Inject
    private AtherysRPGConfig config;

    public AttributeService() {}

    public Set<Attribute> getDefaultAttributes() {
        return new HashSet<>(config.DEFAULT_ATTRIBUTES);
    }

}
