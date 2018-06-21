package com.atherys.rpg.character;

import com.atherys.rpg.api.skill.CastResult;
import com.atherys.rpg.api.skill.Castable;
import com.atherys.rpg.api.skill.MouseButtonCombo;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.entity.living.player.Player;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.UUID;

public class PlayerCharacter extends AbstractRPGCharacter {

    private Player player;

    protected PlayerCharacter(UUID uuid) {
        super(uuid);
    }

    /**
     * Retrieves the cached player object from this PlayerCharacter
     *
     * @return the cached player object, or null if it could not be found based on the uuid.
     */
    @Nullable
    public Player getCachedPlayer() {
        if ( player != null && !player.isRemoved() && player.isLoaded() && player.isOnline() ) return player;
        else return getPlayer();
    }


    public void setCachedPlayer(Player player) {
        this.player = player;
    }

    private Player getPlayer() {
        return Sponge.getServer().getOnlinePlayers().stream().filter(player -> player.getUniqueId().equals(this.getUniqueId())).findAny().orElse(null);
    }

    @Override
    public CastResult cast(Castable castable, String... args) {
        return super.cast(castable, args);
    }

    Optional<? extends Castable> getCastableByCombo(MouseButtonCombo combo) {
        return null;
    }

    Optional<MouseButtonCombo> getCurrentCombo() {
        return null;
    }

    @Override
    public Optional<? extends Living> asLiving() {
        return Optional.ofNullable(getCachedPlayer());
    }
}
