package com.atherys.rpg.mutators;

import com.atherys.rpg.api.character.Mutator;
import com.atherys.rpg.api.character.RPGCharacter;
import com.atherys.rpg.api.resource.Resource;
import com.google.gson.annotations.Expose;

public class ResourceMutator implements Mutator {

    @Expose
    private Resource resource;

    public ResourceMutator(Resource resource) {
        this.resource = resource;
    }

    @Override
    public void mutate(RPGCharacter character) {
        character.setResource(resource);
    }
}
