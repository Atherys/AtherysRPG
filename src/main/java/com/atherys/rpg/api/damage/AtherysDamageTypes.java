package com.atherys.rpg.api.damage;

import org.spongepowered.api.event.cause.entity.damage.DamageTypes;

/**
 * The Cataloguing class for {@link AtherysDamageType}
 */
public final class AtherysDamageTypes {

    // Melee
    public static final AtherysDamageType BLUNT = new AtherysDamageType("atherys:blunt", "Blunt", DamageTypes.ATTACK);
    public static final AtherysDamageType STAB = new AtherysDamageType("atherys:stab", "Stabbing", DamageTypes.ATTACK);
    public static final AtherysDamageType SLASH = new AtherysDamageType("atherys:slash", "Slashing", DamageTypes.ATTACK);
    public static final AtherysDamageType UNARMED = new AtherysDamageType("atherys:unarmed", "Unarmed", DamageTypes.ATTACK);

    // Ranged
    public static final AtherysDamageType PIERCE = new AtherysDamageType("atherys:pierce", "Piercing", DamageTypes.PROJECTILE);
    public static final AtherysDamageType BALLISTIC = new AtherysDamageType("atherys:ballistic", "Ballistic", DamageTypes.PROJECTILE);

    // Magic
    public static final AtherysDamageType FIRE = new AtherysDamageType("atherys:fire", "Fire", DamageTypes.MAGIC);
    public static final AtherysDamageType ICE = new AtherysDamageType("atherys:ice", "Ice", DamageTypes.MAGIC);
    public static final AtherysDamageType ARCANE = new AtherysDamageType("atherys:arcane", "Arcane", DamageTypes.MAGIC);
    public static final AtherysDamageType SHOCK = new AtherysDamageType("atherys:shock", "Shock", DamageTypes.MAGIC);
    public static final AtherysDamageType NATURE = new AtherysDamageType("atherys:nature", "Nature", DamageTypes.MAGIC);
    public static final AtherysDamageType MENTAL = new AtherysDamageType("atherys:mental", "Mental", DamageTypes.MAGIC);
    public static final AtherysDamageType RADIANT = new AtherysDamageType("atherys:radiant", "Radiant", DamageTypes.MAGIC);
    public static final AtherysDamageType NECROTIC = new AtherysDamageType("atherys:necrotic", "Necrotic", DamageTypes.MAGIC);
    public static final AtherysDamageType BLOOD = new AtherysDamageType("atherys:blood", "Blood", DamageTypes.MAGIC);

    private AtherysDamageTypes() {
    }

}
