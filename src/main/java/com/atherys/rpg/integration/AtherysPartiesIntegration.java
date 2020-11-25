package com.atherys.rpg.integration;

import com.atherys.party.AtherysParties;
import com.atherys.party.entity.Party;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public final class AtherysPartiesIntegration {
    public static Set<Player> fetchPlayerPartyMembersWithRadius(Player killer, double radius) {
        Optional<Party> partyOptional = AtherysParties.getInstance().getPartyFacade().getPlayerParty(killer);
        return partyOptional.map(party -> party.getMembers().stream()
                    .map(uuid -> Sponge.getServer().getPlayer(uuid))
                    .filter(player -> {
                        boolean inRange = player.isPresent() && killer.getPosition().distanceSquared(player.get().getPosition()) < Math.pow(radius, 2);
                        return inRange || player.isPresent() && player.get().getUniqueId() == killer.getUniqueId();
                    })
                    .map(Optional::get)
                    .collect(Collectors.toSet())
        ).orElse(Collections.singleton(killer));
    }
}
