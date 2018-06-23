package com.atherys.rpg.view;

import com.atherys.core.views.View;
import com.atherys.rpg.api.character.tree.TalentTree;
import org.spongepowered.api.entity.living.player.Player;

public class TalentTreeView implements View<TalentTree> {

    private TalentTree tree;

    public TalentTreeView(TalentTree tree) {
        this.tree = tree;
    }

    @Override
    public void show(Player player) {

    }
}
